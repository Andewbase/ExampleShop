package com.example.exampleshop.sources.products

import com.example.exampleshop.sources.products.entities.CreateProductRequestEntity
import com.example.exampleshop.sources.products.entities.FetchProductRequest
import com.example.exampleshop.sources.products.entities.ProductEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductsApi {

    @POST("products/create")
    suspend fun createProducts(@Body body: CreateProductRequestEntity)

    @POST("products/search")
    suspend fun searchProducts(@Body body: FetchProductRequest): List<ProductEntity>

    @GET("products")
    suspend fun getAllProducts(): List<ProductEntity>

    @POST("products/product")
    suspend fun getProduct(@Body body: FetchProductRequest): List<ProductEntity>
}