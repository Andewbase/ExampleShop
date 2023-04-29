package com.example.exampleshop.app.model.accounts.entities

import com.example.exampleshop.app.model.EmptySignUpFieldException
import com.example.exampleshop.app.model.PasswordMismatchException
import com.example.exampleshop.app.model.field.SignUpField


data class SignUpData(
    val login: String,
    val userName: String,
    val password: String,
    val email: String,
    val repeatPassword: String
) {

    fun validate() {
        if (login.isBlank()) throw EmptySignUpFieldException(SignUpField.LOGIN)
        if (userName.isBlank()) throw EmptySignUpFieldException(SignUpField.USERNAME)
        if (password.isBlank()) throw EmptySignUpFieldException(SignUpField.PASSWORD)
        if (email.isBlank()) throw EmptySignUpFieldException(SignUpField.EMAIL)
        if (password != repeatPassword) throw PasswordMismatchException()
    }
}
