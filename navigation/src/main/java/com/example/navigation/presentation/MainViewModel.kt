package com.example.navigation.presentation

import com.example.core.Container
import com.example.navigation.domain.GetCurrentUsernameUseCase
import com.example.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentUsernameUseCase: GetCurrentUsernameUseCase
): BaseViewModel() {

    val usernameLiveValue = liveValue<String?>()

    private fun observeUsername() {
        viewModelScope.launch {
            getCurrentUsernameUseCase.getUsername().collectLatest { container ->
                if (container is Container.Success) {
                    usernameLiveValue.value = container.value
                } else {
                    usernameLiveValue.value = null
                }
            }
        }
    }

}