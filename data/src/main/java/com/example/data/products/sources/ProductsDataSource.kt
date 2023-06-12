package com.example.data.products.sources

import com.example.data.products.entities.CreateProduct
import com.example.data.products.entities.ProductDataEntity

interface ProductsDataSource {

    suspend fun createProduct(createProduct: CreateProduct)

    suspend fun getProductById(id: String): ProductDataEntity

    suspend fun searchProducts(searchQuery: String): List<ProductDataEntity>

    suspend fun getAllProducts(): List<ProductDataEntity>

}