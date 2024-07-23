package com.dayorolands.auth.data

import com.dayorolands.core.data.networking.post
import com.dayorolands.core.domain.SessionStorage
import com.dayorolands.core.domain.util.AuthInfo
import com.dayorolands.core.domain.util.DataError
import com.dayorolands.core.domain.util.EmptyResult
import com.dayorolands.core.domain.util.Result
import com.dayorolands.core.domain.util.asEmptyDataResult
import com.runique.auth.domain.AuthRepository
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
): AuthRepository{
    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )

        if(result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }

        return result.asEmptyDataResult()
    }

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }
}