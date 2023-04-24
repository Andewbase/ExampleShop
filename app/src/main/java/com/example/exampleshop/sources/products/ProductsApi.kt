package com.example.exampleshop.sources.products

import com.example.exampleshop.sources.products.entities.CreateProdutRequestEntity
import com.example.exampleshop.sources.products.entities.FetchProductRequest
import com.example.exampleshop.sources.products.entities.ProductEntity
import com.example.exampleshop.sources.products.entities.ProductsEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductsApi {

    @POST("products/create")
    suspend fun createProducts(@Body body: CreateProdutRequestEntity)

    @POST("products/search")
    suspend fun searchProducts(@Body body: FetchProductRequest): ProductsEntity

    @GET("products")
    suspend fun getAllProducts(): List<ProductEntity>

    @POST("products/product")
    suspend fun getProduct(@Body body: FetchProductRequest): List<ProductEntity>
}