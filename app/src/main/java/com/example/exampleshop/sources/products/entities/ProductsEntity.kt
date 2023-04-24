package com.example.exampleshop.sources.products.entities

import com.example.exampleshop.app.model.products.entities.Products

data class ProductsEntity(
   val products: List<ProductEntity>
){
   fun toProducts(): Products {
      return Products(
         products = products.map {
            it.toProduct()
         }
      )
   }
}