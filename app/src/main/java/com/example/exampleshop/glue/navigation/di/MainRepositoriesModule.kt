package com.example.exampleshop.glue.navigation.di

import com.example.exampleshop.glue.navigation.repositories.AdapterGetCurrentUsernameRepository
import com.example.navigation.domain.repositories.GetCurrentUsernameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MainRepositoriesModule {

    @Binds
    fun bindGetCurrentUsernameRepository(
        getCurrentUsernameRepository: AdapterGetCurrentUsernameRepository
    ): GetCurrentUsernameRepository

}