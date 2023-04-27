package com.example.exampleshop.app.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exampleshop.app.model.accounts.AccountsRepository
import com.example.exampleshop.app.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: AccountsRepository
): ViewModel() {

    private val _login = MutableLiveData<String>()
    val login = _login.share()

    init {
        viewModelScope.launch {
            // listening for the current account and send the username to be displayed in the toolbar
            repository.getAccount().collect { result ->
                _login.value = result.getValueOrNull()?.login?.let { "login: $it" } ?: ""
            }
        }
    }

}