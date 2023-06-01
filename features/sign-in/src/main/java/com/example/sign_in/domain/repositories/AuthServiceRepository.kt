package com.example.sign_in.domain.repositories

interface AuthServiceRepository {

    suspend fun signIn(login: String, password: String): String
}