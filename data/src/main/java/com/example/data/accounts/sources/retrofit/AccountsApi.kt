package com.example.data.accounts.sources.retrofit

import com.example.data.accounts.sources.retrofit.entities.GetAccountResponseEntity
import com.example.data.accounts.sources.retrofit.entities.SignInRequestEntity
import com.example.data.accounts.sources.retrofit.entities.SignInResponseEntity
import com.example.data.accounts.sources.retrofit.entities.SignUpRequestEntity
import com.example.data.accounts.sources.retrofit.entities.UpdateUsernameRequestEntity
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
    suspend fun setUserName(@Body updateUsernameRequestEntity: UpdateUsernameRequestEntity): GetAccountResponseEntity
}