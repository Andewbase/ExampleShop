package com.example.catalog.domain.entities

data class Product(
    val productId: String,
    val title: String,
    val description: String,
    val price: String,
    val quantity: String
)