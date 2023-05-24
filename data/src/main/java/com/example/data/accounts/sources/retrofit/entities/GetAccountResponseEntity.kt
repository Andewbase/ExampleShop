package com.example.data.accounts.sources.retrofit.entities

import com.example.data.accounts.entities.AccountDataEntity


data class GetAccountResponseEntity(
    val login: String,
    val email: String?,
    val username: String
){
    fun toAccountDataEntity(): AccountDataEntity = AccountDataEntity(
        login = login,
        username = username,
        email = email ?: ""
    )

}
