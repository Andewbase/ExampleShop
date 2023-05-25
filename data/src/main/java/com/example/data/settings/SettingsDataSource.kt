package com.example.data.settings

import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {

    fun setToken(token: String?)

    fun getToken(): String?

    fun listenToken(): Flow<String?>
}