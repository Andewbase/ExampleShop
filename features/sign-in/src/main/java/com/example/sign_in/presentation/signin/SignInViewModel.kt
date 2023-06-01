package com.example.sign_in.presentation.signin

import com.example.core.AuthException
import com.example.core.Container
import com.example.presentation.BaseViewModel
import com.example.sign_in.R
import com.example.sign_in.domain.IsSignedInUseCase
import com.example.sign_in.domain.SignInUseCase
import com.example.sign_in.domain.exceptions.EmptyLoginException
import com.example.sign_in.domain.exceptions.EmptyPasswordException
import com.example.sign_in.presentation.SignInRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val isSignedInUseCase: IsSignedInUseCase,
    private val signInUseCase: SignInUseCase,
    private val router: SignInRouter
) : BaseViewModel() {

    private val loadScreenStateFlow = MutableStateFlow<Container<Unit>>(Container.Pending)
    private val progressStateFlow = MutableStateFlow(false)
    private val loginErrorStateFlow = MutableStateFlow<String?>(null)
    private val passwordErrorStateFlow = MutableStateFlow<String?>(null)

    val stateLiveValue = combine(
        loadScreenStateFlow,
        progressStateFlow,
        loginErrorStateFlow,
        passwordErrorStateFlow,
        ::merge
    ).toLiveValue(Container.Pending)

    init {
        load()
    }

    fun load() = debounce {
        viewModelScope.launch {
            loadScreenStateFlow.value = Container.Pending
            try {
                if (isSignedInUseCase.isSignedIn()) {
                    router.launchMain()
                } else {
                    loadScreenStateFlow.value = Container.Success(Unit)
                }
            } catch (e: Exception) {
                loadScreenStateFlow.value = Container.Error(e)
            }
        }
    }

    fun signIn(login: String, password: String) = debounce {
        viewModelScope.launch {
            try {
                progressStateFlow.value = true
                signInUseCase.signIn(login, password)
                router.launchMain()
            } catch (e: EmptyLoginException) {
                loginErrorStateFlow.value = resources.getString(R.string.signin_empty_login)
            } catch (e: EmptyPasswordException) {
                passwordErrorStateFlow.value = resources.getString(R.string.signin_empty_password)
            } catch (e: AuthException) {
                showErrorDialog(resources.getString(R.string.signin_invalid_login_or_password))
            } finally {
                progressStateFlow.value = false
            }
        }
    }

    fun launchSignUp(login: String) = debounce {
        router.launchSignUp(login)
    }

    fun clearLoginError() {
        loginErrorStateFlow.value = null
    }

    fun clearPasswordError() {
        passwordErrorStateFlow.value = null
    }

    private fun merge(
        loadContainer: Container<Unit>,
        inProgress: Boolean,
        emailError: String?,
        passwordError: String?
    ): Container<State> {
        return loadContainer.map { State(inProgress, emailError, passwordError) }
    }

    class State(
        private val inProgress: Boolean,
        val loginError: String?,
        val passwordError: String?,
    ) {
        val enableButtons: Boolean get() = !inProgress
        val showProgressBar: Boolean get() = inProgress
    }

}
