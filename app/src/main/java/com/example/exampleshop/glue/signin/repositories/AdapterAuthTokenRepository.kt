package com.example.exampleshop.glue.signin.repositories

import com.example.data.settings.SettingsDataSource
import com.example.sign_in.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class AdapterAuthTokenRepository @Inject constructor(
    private val settingsDataSource: SettingsDataSource
): AuthTokenRepository {

    override suspend fun setToken(token: String?) {
        settingsDataSource.setToken(token)
    }

    override suspend fun getToken(): String? {
       return settingsDataSource.getToken()
    }
}