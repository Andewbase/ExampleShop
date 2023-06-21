package com.example.exampleshop.glue.catalog.mappers

import com.example.catalog.domain.entities.Product
import com.example.data.products.entities.ProductDataEntity
import javax.inject.Inject

class ProductMapper @Inject constructor(){

    fun toProduct(dataEntity: ProductDataEntity): Product{
        return Product(
            productId = dataEntity.productId,
            title = dataEntity.title,
            description = dataEntity.description,
            price = dataEntity.price
        )
    }
}