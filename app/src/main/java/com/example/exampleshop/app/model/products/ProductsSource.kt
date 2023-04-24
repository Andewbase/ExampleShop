package com.example.exampleshop.app.model.products

import com.example.exampleshop.app.model.products.entities.CreateProdut
import com.example.exampleshop.app.model.products.entities.Product
import com.example.exampleshop.app.model.products.entities.Products

interface ProductsSource {

    suspend fun createProdut(createProdut: CreateProdut)

    suspend fun searchProducts(searchQuery: String): Products

    suspend fun getAllProducts(): List<Product>

    suspend fun getProduct(searchQuery: String): List<Product>
}