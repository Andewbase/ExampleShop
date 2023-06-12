package com.example.exampleshop.glue.signup.repositories

import com.example.data.AccountsDataRepository
import com.example.data.accounts.entities.SignUpDataEntity
import com.example.data.accounts.exceptions.AccountAlreadyExistsDataException
import com.example.sign_up.domain.entities.SignUpData
import com.example.sign_up.domain.exceptions.AccountAlreadyExistsException
import com.example.sign_up.domain.repositories.SignUpRepository
import javax.inject.Inject

class AdapterSignUpRepository @Inject constructor(
    private val accountsRepository: AccountsDataRepository
): SignUpRepository {

    override suspend fun signUp(signUpData: SignUpData) {
        try {
            accountsRepository.signUp(
                SignUpDataEntity(
                    login = signUpData.login,
                    password = signUpData.password,
                    email = signUpData.email,
                    username = signUpData.username
                )
            )
        } catch (e: AccountAlreadyExistsDataException){
            throw AccountAlreadyExistsException(e)
        }
    }

}