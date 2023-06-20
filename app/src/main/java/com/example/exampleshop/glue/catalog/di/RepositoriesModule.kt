package com.example.exampleshop.glue.catalog.di

import com.example.catalog.domain.repositories.ProductsRepository
import com.example.exampleshop.glue.catalog.repositories.AdapterProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun provideProductsRepository(
        repository: AdapterProductsRepository
    ): ProductsRepository

}