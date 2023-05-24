package com.example.data.accounts.sources

import com.example.data.accounts.entities.AccountDataEntity
import com.example.data.accounts.entities.SignUpDataEntity
import com.example.data.accounts.sources.retrofit.AccountsApi
import com.example.data.accounts.sources.retrofit.entities.SignInRequestEntity
import com.example.data.accounts.sources.retrofit.entities.SignUpRequestEntity
import com.example.data.accounts.sources.retrofit.entities.UpdateUsernameRequestEntity
import com.example.data.retrofit.BaseRetrofitSource
import com.example.data.retrofit.RetrofitConfig

class RetrofitAccountsSource(
    config: RetrofitConfig
): BaseRetrofitSource(config), AccountsDataSource {

    private val accountsApi = retrofit.create(AccountsApi::class.java)

    override suspend fun signIn(login: String, password: String): String = wrapRetrofitExceptions {
        val signInRequestEntity = SignInRequestEntity(login, password)
        accountsApi.signIn(signInRequestEntity).token
    }

    override suspend fun signUp(signUpData: SignUpDataEntity) {
        val signUpRequestEntity = SignUpRequestEntity(signUpData.login, signUpData.password, signUpData.email, signUpData.username)
        accountsApi.signUp(signUpRequestEntity)
    }

    override suspend fun getAccount(): AccountDataEntity = wrapRetrofitExceptions {
        accountsApi.getAccount().toAccountDataEntity()
    }

    override suspend fun setAccountUsername(username: String): String = wrapRetrofitExceptions {
        val updateUsernameRequestEntity = UpdateUsernameRequestEntity(username)
        accountsApi.setUserName(updateUsernameRequestEntity)
    }
}