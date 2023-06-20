package com.example.exampleshop.glue.navigation.di

import com.example.exampleshop.glue.navigation.DefaultDestinationsProvider
import com.example.navigation.DestinationsProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface StartDestinationProviderModule {

    @Binds
    fun bindStartDestinationProvider(
        startDestinationProvider: DefaultDestinationsProvider
    ): DestinationsProvider

}