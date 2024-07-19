package com.runique.auth.domain

import com.dayorolands.core.domain.util.DataError
import com.dayorolands.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}