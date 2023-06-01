package com.example.sign_up.domain.entities

data class SignUpData(
    val login: String,
    val username: String,
    val email: String,
    val password: String,
    val repeatPassword: String
)
