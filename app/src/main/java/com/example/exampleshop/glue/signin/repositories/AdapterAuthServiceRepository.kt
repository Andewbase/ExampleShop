package com.example.exampleshop.glue.signin.repositories

import com.example.data.AccountsDataRepository
import com.example.sign_in.domain.repositories.AuthServiceRepository
import javax.inject.Inject

class AdapterAuthServiceRepository @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository
): AuthServiceRepository {

    override suspend fun signIn(login: String, password: String): String {
        return accountsDataRepository.signIn(login, password)
    }
}