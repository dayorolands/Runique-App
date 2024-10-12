package com.dayorolands.core.database

import android.database.sqlite.SQLiteFullException
import com.dayorolands.core.database.dao.RunDao
import com.dayorolands.core.database.mappers.toRun
import com.dayorolands.core.database.mappers.toRunEntity
import com.dayorolands.core.domain.run.LocalRunDataSource
import com.dayorolands.core.domain.run.Run
import com.dayorolands.core.domain.run.RunId
import com.dayorolands.core.domain.util.DataError
import com.dayorolands.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalRunDataSource(
    private val runDao: RunDao
): LocalRunDataSource {
    override fun getRuns(): Flow<List<Run>> {
        return runDao.getRuns()
            .map { runEntities ->
                runEntities.map {
                    it.toRun()
                }
            }
    }

    override suspend fun upsertRun(run: Run): Result<RunId, DataError.Local> {
        return try {
            val entity = run.toRunEntity()
            runDao.upsertRun(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertRuns(run: List<Run>): Result<List<RunId>, DataError.Local> {
        return try {
            val entities = run.map { it.toRunEntity() }
            runDao.upsertRuns(entities)
            Result.Success(entities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteRun(id: String) {
        runDao.deleteRun(id = id)
    }

    override suspend fun deleteAllRuns() {
        runDao.deleteAllRuns()
    }
}