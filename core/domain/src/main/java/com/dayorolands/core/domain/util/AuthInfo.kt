package com.dayorolands.core.domain.util

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)
