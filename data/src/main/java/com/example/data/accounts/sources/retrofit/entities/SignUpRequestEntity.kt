package com.example.data.accounts.sources.retrofit.entities

data class SignUpRequestEntity(
        val login: String,
        val password: String,
        val email: String,
        val username: String
)