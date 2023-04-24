package com.example.exampleshop.sources.accounts

import com.example.exampleshop.app.model.accounts.AccountsSource
import com.example.exampleshop.app.model.accounts.entities.SignUpData
import com.example.exampleshop.sources.accounts.entities.SignInRequestEntity
import com.example.exampleshop.sources.accounts.entities.SignUpRequestEntity
import com.example.exampleshop.sources.base.BaseRetrofitSource
import com.example.exampleshop.sources.base.RetrofitConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitAccountsSource @Inject constructor(
    config: RetrofitConfig
): BaseRetrofitSource(config), AccountsSource {

    private val accountsApi = retrofit.create(AccountsApi::class.java)

    override suspend fun signIn(login: String, password: String): String = wrapRetrofitExceptions {
        val signInRequestEntity = SignInRequestEntity(login, password)
        accountsApi.signIn(signInRequestEntity).token
    }

    override suspend fun signUp(signUpData: SignUpData) {
        val signUpRequestEntity = SignUpRequestEntity(signUpData.login, signUpData.password, signUpData.email)
        accountsApi.signUp(signUpRequestEntity)
    }

}