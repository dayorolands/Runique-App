package com.dayorolands.auth.data

import com.dayorolands.core.data.networking.post
import com.dayorolands.core.domain.util.DataError
import com.dayorolands.core.domain.util.EmptyResult
import com.runique.auth.domain.AuthRepository
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient
): AuthRepository{
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