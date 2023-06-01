package com.example.sign_up.domain

import com.example.sign_up.domain.entities.SignUpData
import com.example.sign_up.domain.entities.SignUpField
import com.example.sign_up.domain.exceptions.EmptyFieldException
import com.example.sign_up.domain.exceptions.PasswordMismatchException
import com.example.sign_up.domain.repositories.SignUpRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {

    /**
     * Create a new account.
     * @throws EmptyFieldException
     * @throws AccountAlreadyExistsException
     * @throws PasswordMismatchException
     */
    suspend fun signUp(signUpData: SignUpData) {
        if (signUpData.login.isBlank()) throw  EmptyFieldException(SignUpField.LOGIN)
        if (signUpData.email.isBlank()) throw EmptyFieldException(SignUpField.EMAIL)
        if (signUpData.username.isBlank()) throw EmptyFieldException(SignUpField.USERNAME)
        if (signUpData.password.isBlank()) throw EmptyFieldException(SignUpField.PASSWORD)
        if (signUpData.repeatPassword.isBlank()) throw EmptyFieldException(SignUpField.REPEAT_PASSWORD)
        if (signUpData.password != signUpData.repeatPassword) throw PasswordMismatchException()

        signUpRepository.signUp(signUpData)
    }

}