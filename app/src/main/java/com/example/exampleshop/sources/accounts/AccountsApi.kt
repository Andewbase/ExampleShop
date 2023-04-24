package com.example.exampleshop.sources.accounts

import com.example.exampleshop.sources.accounts.entities.SignInRequestEntity
import com.example.exampleshop.sources.accounts.entities.SignInResponseEntity
import com.example.exampleshop.sources.accounts.entities.SignUpRequestEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountsApi {

    @POST("login")
    suspend fun signIn(@Body body: SignInRequestEntity): SignInResponseEntity

    @POST("register")
    suspend fun signUp(@Body body: SignUpRequestEntity)

}