package com.example.data.products.di

import com.example.data.ProductsDataRepository
import com.example.data.products.RealProductsDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProductRepositoriesModule {

    @Binds
    @Singleton
    fun bindProductsRepository(
        productsRepository: RealProductsDataRepository
    ): ProductsDataRepository

}