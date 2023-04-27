package com.example.exampleshop.sources.accounts.entities

import com.example.exampleshop.app.model.accounts.entities.Account

data class GetAccountResponseEntity(
    val login: String,
    val email: String?,
    val username: String
){
    fun toAccount(): Account = Account(
        login = login,
        email = email ?: "",
        username = username
    )

}
