package com.example.exampleshop.app.screens.main.tabs.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.exampleshop.app.model.Result
import com.example.exampleshop.app.model.accounts.AccountsRepository
import com.example.exampleshop.app.model.accounts.entities.Account
import com.example.exampleshop.app.screens.base.BaseViewModel
import com.example.exampleshop.app.utils.logger.Logger
import com.example.exampleshop.app.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    accountsRepository: AccountsRepository,
    logger: Logger
): BaseViewModel(accountsRepository, logger) {

    private val _account = MutableLiveData<Result<Account>>()
    val account = _account.share()

    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect {
                _account.value = it
            }
        }
    }

}