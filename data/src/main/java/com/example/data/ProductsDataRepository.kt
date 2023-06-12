package com.example.data

import com.example.core.Container
import com.example.data.products.entities.CreateProduct
import com.example.data.products.entities.ProductDataEntity
import kotlinx.coroutines.flow.Flow

interface ProductsDataRepository {

    suspend fun  createProduct(createProduct: CreateProduct)

    suspend fun getProductById(id: String): ProductDataEntity

    fun getCurrentIsAdmin(): Boolean

    fun searchProduct(product: String): Flow<Container<List<ProductDataEntity>>>

}