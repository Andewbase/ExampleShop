package com.example.profile.domain.repositories

import com.example.core.Container
import com.example.profile.domain.entities.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<Container<Profile>>

    /**
     * Reload profile info flow returned by [getProfile]
     */
    fun reload()

    /**
     * Update username of the current logged-in user.
     */
    suspend fun editUsername(newUsername: String)
}