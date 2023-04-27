package com.example.exampleshop.sources.accounts.entities

data class SignUpRequestEntity(
        val login: String,
        val password: String,
        val email: String,
        val username: String
)