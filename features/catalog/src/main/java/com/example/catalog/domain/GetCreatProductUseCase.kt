package com.example.catalog.domain

import com.example.catalog.domain.entities.CreateProduct
import com.example.catalog.domain.entities.CreateProductField
import com.example.catalog.domain.exceptions.EmptyFieldException
import com.example.catalog.domain.repositories.ProductsRepository
import javax.inject.Inject

class GetCreatProductUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {

    suspend fun createProduct(createProduct: CreateProduct){
        if (createProduct.title.isBlank()) throw EmptyFieldException(CreateProductField.TITLE)
        if (createProduct.description.isBlank()) throw EmptyFieldException(CreateProductField.DESCRIPTION)
        if (createProduct.price.isBlank()) throw EmptyFieldException(CreateProductField.PRICE)

        productsRepository.createProduct(createProduct)
    }



}