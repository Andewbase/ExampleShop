package com.example.exampleshop.app.model.accounts.entities

import com.example.exampleshop.app.model.EmptySignInException
import com.example.exampleshop.app.model.field.SignInField

data class SignInData(
    val login: String,
    val password: String
){
    fun validate(){
        if (login.isBlank()) throw EmptySignInException(SignInField.LOGIN)
        if (password.isBlank()) throw EmptySignInException(SignInField.PASSWORD)
    }
}