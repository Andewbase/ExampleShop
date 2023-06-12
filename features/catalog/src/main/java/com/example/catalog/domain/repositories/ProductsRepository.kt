package com.example.catalog.domain.repositories

import com.example.catalog.domain.entities.CreateProduct
import com.example.catalog.domain.entities.Product
import com.example.core.Container
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    suspend fun  createProduct(createProduct: CreateProduct)

    fun isAdmin(): Boolean

    fun searchProduct(product: String): Flow<Container<List<Product>>>

    suspend fun getProductId(id: String): Flow<Container<Product>>
}