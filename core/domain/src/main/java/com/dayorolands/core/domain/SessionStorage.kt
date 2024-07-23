package com.dayorolands.core.domain

import com.dayorolands.core.domain.util.AuthInfo

interface SessionStorage {
    suspend fun get(): AuthInfo?

    suspend fun set(authInfo: AuthInfo?)
}