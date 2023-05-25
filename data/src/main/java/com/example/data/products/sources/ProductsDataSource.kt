package com.example.data.products.sources

import com.example.data.products.entities.CreateProduct
import com.example.data.products.entities.Product

interface ProductsDataSource {

    suspend fun createProduct(createProduct: CreateProduct)

    suspend fun searchProducts(searchQuery: String): List<Product>

    suspend fun getAllProducts(): List<Product>

}