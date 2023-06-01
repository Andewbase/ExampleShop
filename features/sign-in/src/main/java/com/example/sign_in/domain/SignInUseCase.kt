package com.example.sign_in.domain

import com.example.sign_in.domain.exceptions.EmptyLoginException
import com.example.sign_in.domain.exceptions.EmptyPasswordException
import com.example.sign_in.domain.repositories.AuthServiceRepository
import com.example.sign_in.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authTokenRepository: AuthTokenRepository,
    private val authServiceRepository: AuthServiceRepository,
) {

    /**
     * Sign-in to the app by login and password and save auth token.
     * @throws EmptyLoginException if email is blank
     * @throws EmptyPasswordException if password is blank
     */
    suspend fun signIn(login: String, password: String) {
        if (login.isBlank()) throw EmptyLoginException()
        if (password.isBlank()) throw EmptyPasswordException()

        val token = authServiceRepository.signIn(login, password)

        authTokenRepository.setToken(token)
    }

}