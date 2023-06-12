package com.example.exampleshop.glue.profile.repositories

import com.example.data.settings.SettingsDataSource
import com.example.profile.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class AdapterAuthTokenRepository @Inject constructor(
   private val settingsDataSource: SettingsDataSource
): AuthTokenRepository {

    override suspend fun clearToken() {
        settingsDataSource.setToken(null)
    }

}