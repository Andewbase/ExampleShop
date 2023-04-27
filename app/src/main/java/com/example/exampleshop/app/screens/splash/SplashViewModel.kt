package com.example.exampleshop.app.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exampleshop.app.model.accounts.AccountsRepository
import com.example.exampleshop.app.model.products.ProductsRepository
import com.example.exampleshop.app.utils.MutableLiveEvent
import com.example.exampleshop.app.utils.publishEvent
import com.example.exampleshop.app.utils.share
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: AccountsRepository
): ViewModel() {

    private val _launchMainScreenEvent = MutableLiveEvent<Boolean>()
    val launchMainScreenEvent = _launchMainScreenEvent.share()

    init {
        viewModelScope.launch {
            _launchMainScreenEvent.publishEvent(repository.isSignedIn())
        }
    }

}