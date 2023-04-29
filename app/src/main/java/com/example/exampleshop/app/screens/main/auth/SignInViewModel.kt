package com.example.exampleshop.app.screens.main.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.exampleshop.R
import com.example.exampleshop.app.model.EmptySignInException
import com.example.exampleshop.app.model.InvalidCredentialsException
import com.example.exampleshop.app.model.accounts.AccountsRepository
import com.example.exampleshop.app.model.accounts.entities.SignInData
import com.example.exampleshop.app.model.field.SignInField
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

    private val _navigateToTabsEvent = MutableUnitLiveEvent()
    val navigateToTabsEvent = _navigateToTabsEvent.share()

    fun signIn(signInData: SignInData) {
        viewModelScope.launch {
            showProgress()
            try {
                accountsRepository.signIn(signInData)
                launchTabsScreen()
            } catch (e: EmptySignInException) {
                processEmptyFieldException(e)
            } catch (e: InvalidCredentialsException) {
                processInvalidCredentialsException()
            }
            finally {
                hideProgress()
            }
        }
    }

    private fun processEmptyFieldException(e: EmptySignInException) {
        _state.value = _state.requireValue().copy(
            emptyLoginError = e.signInField == SignInField.LOGIN,
            emptyPasswordError = e.signInField == SignInField.PASSWORD
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

    private fun launchTabsScreen() = _navigateToTabsEvent.publishEvent()

    data class State(
        val emptyLoginError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
        val enableViews: Boolean get() = !signInInProgress
    }

}