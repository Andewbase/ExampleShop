package com.example.exampleshop.sources.products.entities

data class CreateProductRequestEntity(
    val title: String,
    val description: String,
    val price: Double
)
