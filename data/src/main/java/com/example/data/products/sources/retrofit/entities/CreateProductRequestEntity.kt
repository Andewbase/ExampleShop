package com.example.data.products.sources.retrofit.entities

data class CreateProductRequestEntity(
    val title: String,
    val description: String,
    val quantity: Int,
    val price: Double
)
