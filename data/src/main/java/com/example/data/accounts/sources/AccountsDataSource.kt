package com.example.data.accounts.sources

import com.example.data.accounts.entities.AccountDataEntity
import com.example.data.accounts.entities.SignUpDataEntity

interface AccountsDataSource {

    suspend fun signIn(login: String, password: String): String

    suspend fun signUp(signUpData: SignUpDataEntity)

    suspend fun getAccount(): AccountDataEntity

    suspend fun setAccountUsername(username: String): AccountDataEntity
}