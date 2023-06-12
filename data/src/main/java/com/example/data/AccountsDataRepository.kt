package com.example.data

import com.example.core.Container
import com.example.data.accounts.entities.AccountDataEntity
import com.example.data.accounts.entities.SignUpDataEntity
import kotlinx.coroutines.flow.Flow

interface AccountsDataRepository {

    fun getAccount(): Flow<Container<AccountDataEntity>>

    suspend fun setAccountUsername(username: String)

    suspend fun signIn(email: String, password: String): String

    suspend fun signUp(signUpData: SignUpDataEntity)

    fun isAdmin(): Boolean
    fun reload()
}