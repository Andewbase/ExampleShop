package com.example.exampleshop.di

import com.example.exampleshop.app.model.accounts.AccountsSource
import com.example.exampleshop.app.model.products.ProductsSource
import com.example.exampleshop.sources.accounts.RetrofitAccountsSource
import com.example.exampleshop.sources.products.RetrofitProductsSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourcesModule {

    @Binds
    abstract fun bindAccountsSource(
        retrofitAccountsSource: RetrofitAccountsSource
    ): AccountsSource

    @Binds
    abstract fun bindProductsSource(
        retrofitProductsSource: RetrofitProductsSource
    ): ProductsSource
}