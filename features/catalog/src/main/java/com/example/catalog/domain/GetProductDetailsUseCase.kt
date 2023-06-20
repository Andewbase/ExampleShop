package com.example.catalog.domain

import com.example.catalog.domain.entities.Product
import com.example.catalog.domain.repositories.ProductsRepository
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {

    suspend fun getProduct(id: String): Product{
        return productsRepository.getProductId(id)
    }

}