package com.example.exampleshop.app.model.accounts

import com.example.exampleshop.app.model.AccountAlreadyExistsException
import com.example.exampleshop.app.model.BackendException
import com.example.exampleshop.app.model.EmptyFieldException
import com.example.exampleshop.app.model.Field
import com.example.exampleshop.app.model.InvalidCredentialsException
import com.example.exampleshop.app.model.accounts.entities.SignUpData
import com.example.exampleshop.app.model.settings.AppSettings
import javax.inject.Inject

class AccountsRepository @Inject constructor(
    private val accountsSource: AccountsSource,
    private val appSettings: AppSettings
) {

    suspend fun signIn(login: String, password: String){
        if (login.isBlank()) throw EmptyFieldException(Field.Login)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)

        val token = try {
            accountsSource.signIn(login, password)
        }catch (e: Exception){
            if (e is BackendException && e.code == 401) {
                // map 401 error for sign-in to InvalidCredentialsException
                throw InvalidCredentialsException(e)
            } else {
                throw e
            }
        }

        appSettings.setCurrentToken(token)

        accountsSource.signIn(login, password)
    }

    suspend fun signUp(signUpData: SignUpData) {
        signUpData.validate()
        try {
            accountsSource.signUp(signUpData)
        } catch (e: BackendException) {
            // user with such email already exists
            if (e.code == 409) throw AccountAlreadyExistsException(e)
            else throw e
        }
    }


}