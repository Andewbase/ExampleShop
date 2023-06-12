package com.example.exampleshop.glue.profile.di

import com.example.exampleshop.glue.profile.repositories.AdapterAuthTokenRepository
import com.example.exampleshop.glue.profile.repositories.AdapterProfileRepository
import com.example.profile.domain.repositories.AuthTokenRepository
import com.example.profile.domain.repositories.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProfileRepositoriesModule {

    @Binds
    fun bindAuthTokenRepository(
        authTokenRepository: AdapterAuthTokenRepository
    ): AuthTokenRepository

    @Binds
    fun bindProfileRepository(
        profileRepository: AdapterProfileRepository
    ): ProfileRepository
}