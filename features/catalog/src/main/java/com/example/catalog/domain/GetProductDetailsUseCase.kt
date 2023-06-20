package com.example.catalog.domain

import com.example.catalog.domain.entities.Product
import com.example.catalog.domain.repositories.ProductsRepository
import com.example.core.Container
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {

    fun getProduct(id: String): Flow<Container<Product>>{
        return productsRepository.getProductId(id)
    }

}