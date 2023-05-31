package com.example.data.products.di

import com.example.data.products.sources.ProductsDataSource
import com.example.data.products.sources.RetrofitProductsSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProductSourcesModule {

    @Binds
    @Singleton
    fun bindProductSource(
        productsDataSource: RetrofitProductsSource
    ): ProductsDataSource

}