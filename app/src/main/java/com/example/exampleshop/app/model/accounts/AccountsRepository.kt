package com.example.exampleshop.app.model.accounts

import com.example.exampleshop.app.model.AccountAlreadyExistsException
import com.example.exampleshop.app.model.AuthException
import com.example.exampleshop.app.model.BackendException
import com.example.exampleshop.app.model.EmptyFieldException
import com.example.exampleshop.app.model.Field
import com.example.exampleshop.app.model.InvalidCredentialsException
import com.example.exampleshop.app.model.Result
import com.example.exampleshop.app.model.accounts.entities.Account
import com.example.exampleshop.app.model.accounts.entities.SignUpData
import com.example.exampleshop.app.model.settings.AppSettings
import com.example.exampleshop.app.model.wrapBackendExceptions
import com.example.exampleshop.app.utils.async.LazyFlowSubject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountsRepository @Inject constructor(
    private val accountsSource: AccountsSource,
    private val appSettings: AppSettings
) {

    private val accountLazyFlowSubject = LazyFlowSubject<Unit, Account> {
        doGetAccount()
    }

    fun isSignedIn(): Boolean {
        // user is signed-in if auth token exists
        return appSettings.getCurrentToken() != null
    }

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

    fun getAccount(): Flow<Result<Account>> {
        return accountLazyFlowSubject.listen(Unit)
    }

    fun logout() {
        appSettings.setCurrentToken(null)
        accountLazyFlowSubject.updateAllValues(null)
    }

    private suspend fun doGetAccount(): Account = wrapBackendExceptions {
        try {
            accountsSource.getAccount()
        } catch (e: BackendException) {
            // account has been deleted = session expired = AuthException
            if (e.code == 404) throw AuthException(e)
            else throw e
        }
    }


}