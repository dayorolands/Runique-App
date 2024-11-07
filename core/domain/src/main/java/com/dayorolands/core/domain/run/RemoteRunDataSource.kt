package com.dayorolands.core.domain.run

import com.dayorolands.core.domain.util.DataError
import com.dayorolands.core.domain.util.EmptyResult
import com.dayorolands.core.domain.util.Result

interface RemoteRunDataSource {
    suspend fun getRuns(): Result<List<Run>, DataError.Network>
    suspend fun postRun(run: Run, mapPicture: ByteArray): Result<Run, DataError.Network>
    suspend fun deleteRun(id: String): EmptyResult<DataError.Network>
}