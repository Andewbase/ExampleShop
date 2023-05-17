package com.example.exampleshop.app.model.products

import com.example.exampleshop.app.model.products.entities.CreateProduct
import com.example.exampleshop.app.model.products.entities.Product

interface ProductsSource {

    suspend fun createProduct(createProduct: CreateProduct)

    suspend fun searchProducts(searchQuery: String): List<Product>

    suspend fun getAllProducts(): List<Product>

    suspend fun getProduct(searchQuery: String): List<Product>
}