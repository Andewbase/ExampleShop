package com.example.data.products.sources.retrofit

import com.example.data.products.sources.retrofit.entities.CreateProductRequestEntity
import com.example.data.products.sources.retrofit.entities.FetchProductRequest
import com.example.data.products.sources.retrofit.entities.ProductEntity
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
}