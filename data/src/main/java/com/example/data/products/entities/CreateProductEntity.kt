package com.example.data.products.entities



data class CreateProductEntity(
    val title: String,
    val description: String,
    val quantity: Int,
    val price: String
)
