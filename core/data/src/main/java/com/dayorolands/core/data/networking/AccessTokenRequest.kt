package com.dayorolands.core.data.networking

data class AccessTokenRequest(
    val refreshToken: String,
    val userId: String
)
