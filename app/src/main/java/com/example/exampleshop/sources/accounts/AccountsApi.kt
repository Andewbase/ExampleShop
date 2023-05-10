package com.example.exampleshop.sources.accounts

import com.example.exampleshop.sources.accounts.entities.GetAccountResponseEntity
import com.example.exampleshop.sources.accounts.entities.SignInRequestEntity
import com.example.exampleshop.sources.accounts.entities.SignInResponseEntity
import com.example.exampleshop.sources.accounts.entities.SignUpRequestEntity
import com.example.exampleshop.sources.accounts.entities.UpdateUsernameRequestEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AccountsApi {

    @POST("login")
    suspend fun signIn(@Body body: SignInRequestEntity): SignInResponseEntity

    @POST("register")
    suspend fun signUp(@Body body: SignUpRequestEntity)

    @GET("me")
    suspend fun getAccount(): GetAccountResponseEntity

    @PUT("me")
    suspend fun setUserName(@Body updateUsernameRequestEntity: UpdateUsernameRequestEntity)
}