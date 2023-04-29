package com.example.exampleshop.app.model.products.entities

import com.example.exampleshop.app.model.EmptyProductException
import com.example.exampleshop.app.model.field.ProductField

data class CreateProduct(
    val title: String,
    val description: String,
    val price: String
){
    fun validate(){
        if (title.isBlank()) throw EmptyProductException(ProductField.TITLE)
        if (description.isBlank()) throw EmptyProductException(ProductField.DESCRIPTION)
        if (price.isBlank()) throw EmptyProductException(ProductField.PRICE)
    }
}
