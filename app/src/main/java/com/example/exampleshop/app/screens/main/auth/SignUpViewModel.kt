package com.example.exampleshop.app.screens.main.auth

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.exampleshop.R
import com.example.exampleshop.app.model.AccountAlreadyExistsException
import com.example.exampleshop.app.model.EmptySignUpFieldException
import com.example.exampleshop.app.model.PasswordMismatchException
import com.example.exampleshop.app.model.accounts.AccountsRepository
import com.example.exampleshop.app.model.accounts.entities.SignUpData
import com.example.exampleshop.app.model.field.SignUpField
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
class SignUpViewModel @Inject constructor(
     accountsRepository: AccountsRepository,
     logger: Logger
): BaseViewModel(accountsRepository, logger) {

    private val _goBackEvent = MutableUnitLiveEvent()
    val goBackEvent = _goBackEvent.share()

    private val _showToastEvent = MutableLiveEvent<Int>()
    val showToastEvent = _showToastEvent.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    fun signUp(signUpData: SignUpData)  {
        viewModelScope.launch {
            showProgress()
            try {
                accountsRepository.signUp(signUpData)
                showSuccessSignUpMessage()
                goBack()
            } catch (e: EmptySignUpFieldException) {
                processEmptyFieldException(e)
            }catch (e: PasswordMismatchException) {
                processPasswordMismatchException()
            } catch (e: AccountAlreadyExistsException) {
                processAccountAlreadyExistsException()
            } finally {
                hideProgress()
            }
        }
    }

    private fun processEmptyFieldException(e: EmptySignUpFieldException) {
        _state.value = when (e.signUpField) {
            SignUpField.EMAIL -> _state.requireValue()
                .copy(emailErrorMessageRes = R.string.field_is_empty)
            SignUpField.LOGIN -> _state.requireValue()
                .copy(loginErrorMessageRes = R.string.field_is_empty)
            SignUpField.USERNAME -> _state.requireValue()
                .copy(usernameErrorMessageRes = R.string.field_is_empty)
            SignUpField.PASSWORD -> _state.requireValue()
                .copy(passwordErrorMessageRes = R.string.field_is_empty)
        }
    }

    private fun processPasswordMismatchException() {
        _state.value = _state.requireValue()
            .copy(repeatPasswordErrorMessageRes = R.string.password_mismatch)
    }

    private fun processAccountAlreadyExistsException() {
        _state.value = _state.requireValue()
            .copy(emailErrorMessageRes = R.string.account_already_exists)
    }

    private fun showProgress() {
        _state.value = State(signUpInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(signUpInProgress = false)
    }

    private fun showSuccessSignUpMessage() = _showToastEvent.publishEvent(R.string.sign_up_success)

    private fun goBack() = _goBackEvent.publishEvent()

    data class State(
        @StringRes val loginErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val usernameErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val emailErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val passwordErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val repeatPasswordErrorMessageRes: Int = NO_ERROR_MESSAGE,
        val signUpInProgress: Boolean = false,
    ) {
        val showProgress: Boolean get() = signUpInProgress
        val enableViews: Boolean get() = !signUpInProgress
    }

    companion object {
        const val NO_ERROR_MESSAGE = 0
    }
}