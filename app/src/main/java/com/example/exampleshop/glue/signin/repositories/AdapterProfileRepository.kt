package com.example.exampleshop.glue.signin.repositories


import com.example.core.AuthException
import com.example.core.unwrapFirst
import com.example.data.AccountsDataRepository
import com.example.sign_in.domain.repositories.ProfileRepository
import javax.inject.Inject

class AdapterProfileRepository @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository
): ProfileRepository {

    override suspend fun canLoadProfile(): Boolean {
       return try{
        accountsDataRepository.getAccount().unwrapFirst()
           true
       }catch (ignored: AuthException){
            false
       }
    }


}