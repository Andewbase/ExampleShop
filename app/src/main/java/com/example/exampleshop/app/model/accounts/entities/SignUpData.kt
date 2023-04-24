package com.example.exampleshop.app.model.accounts.entities

import com.example.exampleshop.app.model.EmptyFieldException
import com.example.exampleshop.app.model.Field
import com.example.exampleshop.app.model.PasswordMismatchException


data class SignUpData(
    val login: String,
    val password: String,
    val email: String,
    val repeatPassword: String
) {

    fun validate() {
        if (login.isBlank()) throw EmptyFieldException(Field.Login)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (password != repeatPassword) throw PasswordMismatchException()
    }
}
