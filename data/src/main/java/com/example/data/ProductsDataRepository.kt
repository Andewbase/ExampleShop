package com.example.data

import com.example.core.Container
import com.example.data.products.entities.CreateProduct
import com.example.data.products.entities.Product
import kotlinx.coroutines.flow.Flow

interface ProductsDataRepository {

    suspend fun  createProduct(createProduct: CreateProduct)

    fun searchProduct(product: String): Flow<Container<List<Product>>>

}