package com.example.exampleshop.glue.signin.di

import com.example.exampleshop.glue.signin.repositories.AdapterAuthServiceRepository
import com.example.exampleshop.glue.signin.repositories.AdapterAuthTokenRepository
import com.example.exampleshop.glue.signin.repositories.AdapterProfileRepository
import com.example.sign_in.domain.repositories.AuthServiceRepository
import com.example.sign_in.domain.repositories.AuthTokenRepository
import com.example.sign_in.domain.repositories.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SignInRepositoriesModule {

    @Binds
    fun bindAuthRepository(authServiceRepository: AdapterAuthServiceRepository): AuthServiceRepository

    @Binds
    fun bindAuthTokenRepository(authTokenRepository: AdapterAuthTokenRepository): AuthTokenRepository

    @Binds
    fun bindProfileRepository(profileRepository: AdapterProfileRepository): ProfileRepository
}