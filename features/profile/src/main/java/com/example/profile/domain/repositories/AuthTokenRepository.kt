package com.example.profile.domain.repositories

interface AuthTokenRepository {

    suspend fun clearToken()
}