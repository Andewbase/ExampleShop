package com.example.exampleshop.di

import com.example.exampleshop.app.utils.logger.LogCatLogger
import com.example.exampleshop.app.utils.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class StuffsModule {

    @Provides
    fun provideLogger(): Logger {
        return LogCatLogger
    }

}