package com.example.data

import com.example.core.Container
import com.example.data.products.entities.CreateProductEntity
import com.example.data.products.entities.ProductDataEntity
import kotlinx.coroutines.flow.Flow

interface ProductsDataRepository {

    suspend fun  createProduct(createProduct: CreateProductEntity)

    fun getProductById(id: String): Flow<Container<ProductDataEntity>>

    fun getCurrentIsAdmin(): Boolean

    fun searchProduct(product: String): Flow<Container<List<ProductDataEntity>>>

}