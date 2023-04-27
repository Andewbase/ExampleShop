package com.example.exampleshop.app.model.products.entities

import com.example.exampleshop.app.screens.main.tabs.product.adapter.ProductItem

data class Product(
    val productId: String,
    val title: String,
    val description: String,
    val price: Double
){
   fun toProductItem(): ProductItem = ProductItem(
       productId = productId,
       title = title,
       price = price.toString()
   )
}