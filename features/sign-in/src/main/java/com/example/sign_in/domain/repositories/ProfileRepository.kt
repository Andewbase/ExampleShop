package com.example.sign_in.domain.repositories

interface ProfileRepository {

    suspend fun canLoadProfile(): Boolean

}