package com.example.exampleshop.app.screens.main.tabs.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.exampleshop.R
import com.example.exampleshop.app.model.EmptySetUserNameExceptions
import com.example.exampleshop.app.model.Success
import com.example.exampleshop.app.model.accounts.AccountsRepository
import com.example.exampleshop.app.screens.base.BaseViewModel
import com.example.exampleshop.app.utils.MutableLiveEvent
import com.example.exampleshop.app.utils.MutableUnitLiveEvent
import com.example.exampleshop.app.utils.logger.Logger
import com.example.exampleshop.app.utils.publishEvent
import com.example.exampleshop.app.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    accountsRepository: AccountsRepository,
    logger: Logger
): BaseViewModel(accountsRepository, logger) {

    private val _initialUsernameEvent = MutableLiveEvent<String>()
    val initialUsernameEvent = _initialUsernameEvent.share()

    private val _saveInProgress = MutableLiveData(false)
    val saveInProgress = _saveInProgress.share()

    private val _goBackEvent = MutableUnitLiveEvent()
    val goBackEvent = _goBackEvent.share()

    private val _showErrorEvent = MutableLiveEvent<Int>()
    val showErrorEvent = _showErrorEvent.share()

    init {
        viewModelScope.launch {
            val res = accountsRepository.getAccount()
                .filter { it.isFinished() }
                .first()
            if (res is Success) _initialUsernameEvent.publishEvent(res.value.username)
        }
    }

    fun saveUsername(newUsername: String) = viewModelScope.safeLaunch {
        showProgress()
        try {
            accountsRepository.updateAccountUsername(newUsername)
            goBack()
        } catch (e: EmptySetUserNameExceptions) {
            showEmptyFieldErrorMessage()
        } finally {
            hideProgress()
        }
    }

    private fun goBack() = _goBackEvent.publishEvent()

    private fun showProgress() {
        _saveInProgress.value = true
    }

    private fun hideProgress() {
        _saveInProgress.value = false
    }

    private fun showEmptyFieldErrorMessage() = _showErrorEvent.publishEvent(R.string.field_is_empty)

}