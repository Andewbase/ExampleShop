package com.example.data.products.sources.retrofit.entities

import com.example.data.products.entities.ProductDataEntity

data class ProductEntity(
    val productId: String,
    val title: String,
    val description: String,
    val quantity: Int,
    val price: Double
){
   fun toProduct(): ProductDataEntity = ProductDataEntity(
       productId = productId,
       title = title,
       description = description,
       quantity = quantity,
       price = price.toString()
   )
}