package com.example.exampleshop.app.screens.main.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.exampleshop.R
import com.example.exampleshop.app.model.EmptyFieldException
import com.example.exampleshop.app.model.Field
import com.example.exampleshop.app.model.InvalidCredentialsException
import com.example.exampleshop.app.model.accounts.AccountsRepository
import com.example.exampleshop.app.screens.base.BaseViewModel
import com.example.exampleshop.app.utils.MutableLiveEvent
import com.example.exampleshop.app.utils.MutableUnitLiveEvent
import com.example.exampleshop.app.utils.logger.Logger
import com.example.exampleshop.app.utils.publishEvent
import com.example.exampleshop.app.utils.requireValue
import com.example.exampleshop.app.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
     accountsRepository: AccountsRepository,
     logger: Logger
): BaseViewModel(accountsRepository, logger) {

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _clearPasswordEvent = MutableUnitLiveEvent()
    val clearPasswordEvent = _clearPasswordEvent.share()

    private val _showAuthErrorToastEvent = MutableLiveEvent<Int>()
    val showAuthToastEvent = _showAuthErrorToastEvent.share()

    private val _navigateToProductsEvent = MutableUnitLiveEvent()
    val navigateToProductsEvent = _navigateToProductsEvent.share()

    fun signIn(login: String, password: String) {
        viewModelScope.launch {
            showProgress()
            try {
                accountsRepository.signIn(login, password)
                launchProductsScreen()
            } catch (e: EmptyFieldException) {
                processEmptyFieldException(e)
            } catch (e: InvalidCredentialsException) {
                processInvalidCredentialsException()
            }
            finally {
                hideProgress()
            }
        }
    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = _state.requireValue().copy(
            emptyLoginError = e.field == Field.Login,
            emptyPasswordError = e.field == Field.Password
        )
    }

    private fun processInvalidCredentialsException() {
        clearPasswordField()
        showAuthErrorToast()
    }

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(signInInProgress = false)
    }

    private fun showAuthErrorToast() = _showAuthErrorToastEvent.publishEvent(R.string.invalid_email_or_password)

    private fun clearPasswordField() = _clearPasswordEvent.publishEvent()

    private fun launchProductsScreen() = _navigateToProductsEvent.publishEvent()

    data class State(
        val emptyLoginError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
        val enableViews: Boolean get() = !signInInProgress
    }

}