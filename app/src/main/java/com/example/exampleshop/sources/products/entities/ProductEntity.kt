package com.example.exampleshop.sources.products.entities

import com.example.exampleshop.app.model.products.entities.Product

data class ProductEntity(
    val productId: String,
    val title: String,
    val description: String,
    val price: Double
){
   fun toProduct(): Product = Product(
       productId = productId,
       title = title,
       description = description,
       price = price
   )
}