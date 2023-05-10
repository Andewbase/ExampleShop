package com.example.exampleshop.app.model.accounts

import com.example.exampleshop.app.model.accounts.entities.Account
import com.example.exampleshop.app.model.accounts.entities.SignInData
import com.example.exampleshop.app.model.accounts.entities.SignUpData

interface AccountsSource {

    suspend fun signIn(signInData: SignInData): String

    suspend fun signUp(signUpData: SignUpData)

    suspend fun getAccount(): Account

    suspend fun setUserName(userName: String)
}