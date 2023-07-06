package com.example.data.products.sources

import com.example.data.products.entities.CreateProductEntity
import com.example.data.products.entities.ProductDataEntity

interface ProductsDataSource {

    suspend fun createProduct(createProduct: CreateProductEntity)

    suspend fun getProductById(id: String): ProductDataEntity

    suspend fun searchProducts(searchQuery: String): List<ProductDataEntity>

    suspend fun getAllProducts(): List<ProductDataEntity>

}