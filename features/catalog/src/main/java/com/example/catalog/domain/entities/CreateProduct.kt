package com.example.catalog.domain.entities

data class CreateProduct(
    val title: String,
    val description: String,
    val quantity: String,
    val price: String
)
