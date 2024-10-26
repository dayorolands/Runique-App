package com.dayorolands.core.data.run

import com.dayorolands.core.database.dao.RunPendingSyncDao
import com.dayorolands.core.database.mappers.toRun
import com.dayorolands.core.domain.SessionStorage
import com.dayorolands.core.domain.run.LocalRunDataSource
import com.dayorolands.core.domain.run.RemoteRunDataSource
import com.dayorolands.core.domain.run.Run
import com.dayorolands.core.domain.run.RunRepository
import com.dayorolands.core.domain.util.DataError
import com.dayorolands.core.domain.util.EmptyResult
import com.dayorolands.core.domain.util.Result
import com.dayorolands.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfflineFirstRunRepository(
    private val localRunDataSource: LocalRunDataSource,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val applicationScope: CoroutineScope,
    private val runPendingSyncDao: RunPendingSyncDao,
    private val sessionStorage: SessionStorage
) : RunRepository {
    override fun getRuns(): Flow<List<Run>> {
        return localRunDataSource.getRuns()
    }

    override suspend fun fetchRuns(): EmptyResult<DataError> {
        return when(val result = remoteRunDataSource.getRuns()) {
            is Result.Error -> { result.asEmptyDataResult() }
            is Result.Success -> {
                applicationScope.async {
                    localRunDataSource.upsertRuns(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyResult<DataError> {
        val localResult = localRunDataSource.upsertRun(run)
        if(localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val runWithId = run.copy(id = localResult.data)
        val remoteResult = remoteRunDataSource.postRun(
            run = runWithId,
            mapPicture = mapPicture
        )

        return when(remoteResult) {
            is Result.Error -> Result.Success(Unit)
            is Result.Success -> {
                applicationScope.async {
                    localRunDataSource.upsertRun(remoteResult.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun deleteRun(runId: String) {
        localRunDataSource.deleteRun(id = runId)

        /* This is an edge case where the run is created in offline mode,
        * and then deleted in offline mode as well. In that case, we don't
        * need to sync anything to the backend. */
        val isPendingSync = runPendingSyncDao.getRunPendingSyncEntity(runId) != null
        if(isPendingSync) {
            runPendingSyncDao.deleteRunPendingSyncEntity(runId)
            return
        }
        val remoteResult = applicationScope.async {
            remoteRunDataSource.deleteRun(runId)
        }.await()
    }

    override suspend fun syncPendingRuns() {
        withContext(Dispatchers.IO) {
            val userId = sessionStorage.get()?.userId ?: return@withContext

            val createdRuns = async {
                runPendingSyncDao.getAllRunPendingSyncEntities(userId = userId)
            }
            val deletedRuns = async {
                runPendingSyncDao.getAllDeletedRunSyncEntities(userId = userId)
            }

            val createdJobs = createdRuns
                .await()
                .map {
                    launch {
                        val run = it.run.toRun()
                        when(remoteRunDataSource.postRun(run, it.mapPictureBytes)) {
                            is Result.Error -> Unit
                            is Result.Success -> {
                                /* The `applicationScope.launch` here helps ensure that if our coroutine is cancelled
                                * after the post run api call, the `deleteRunPendingSyncEntity` is still called. */
                                applicationScope.launch {
                                    runPendingSyncDao.deleteRunPendingSyncEntity(it.runId)
                                }.join()
                            }
                        }
                    }
                }

            val deleteJobs = deletedRuns
                .await()
                .map {
                    launch {
                        when(remoteRunDataSource.deleteRun(it.runId)) {
                            is Result.Error -> Unit
                            is Result.Success -> {
                                applicationScope.launch {
                                    runPendingSyncDao.deleteDeletedRunSyncEntity(it.runId)
                                }.join()
                            }
                        }
                    }
                }

            createdJobs.forEach { it.join() }
            deleteJobs.forEach { it.join() }
        }
    }
}