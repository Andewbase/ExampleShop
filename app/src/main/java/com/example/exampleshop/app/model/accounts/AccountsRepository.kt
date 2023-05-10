package com.example.exampleshop.app.model.accounts

import com.example.exampleshop.app.AdminConst.ADMIN_TOKEN
import com.example.exampleshop.app.model.AccountAlreadyExistsException
import com.example.exampleshop.app.model.AuthException
import com.example.exampleshop.app.model.BackendException
import com.example.exampleshop.app.model.EmptySetUserNameExceptions
import com.example.exampleshop.app.model.InvalidCredentialsException
import com.example.exampleshop.app.model.Result
import com.example.exampleshop.app.model.accounts.entities.Account
import com.example.exampleshop.app.model.accounts.entities.SignInData
import com.example.exampleshop.app.model.accounts.entities.SignUpData
import com.example.exampleshop.app.model.field.SetUserNameField
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

    fun isAdmin(): Boolean{
        return appSettings.getCurrentToken() == ADMIN_TOKEN
    }

    fun isSignedIn(): Boolean {
        // user is signed-in if auth token exists
        return appSettings.getCurrentToken() != null
    }

    suspend fun signIn(signInData: SignInData){
        signInData.validate()

        val token = try {
            accountsSource.signIn(signInData)
        }catch (e: Exception){
            if (e is BackendException && e.code == 401) {
                // map 401 error for sign-in to InvalidCredentialsException
                throw InvalidCredentialsException(e)
            } else {
                throw e
            }
        }

        appSettings.setCurrentToken(token)

        accountsSource.signIn(signInData)
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

    suspend fun updateAccountUsername(newUserName: String) = wrapBackendExceptions {
        if (newUserName.isBlank()) throw EmptySetUserNameExceptions(SetUserNameField.NEWUSERNAME)

        accountsSource.setUserName(newUserName)
        accountLazyFlowSubject.updateAllValues(accountsSource.getAccount())
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