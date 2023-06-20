package com.example.exampleshop.glue.catalog.repositories

import com.example.catalog.domain.entities.CreateProduct
import com.example.catalog.domain.entities.Product
import com.example.catalog.domain.repositories.ProductsRepository
import com.example.core.Container
import com.example.data.ProductsDataRepository
import com.example.exampleshop.glue.catalog.mappers.ProductMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterProductsRepository @Inject constructor(
    private val productsDataRepository: ProductsDataRepository,
    private val productMapper: ProductMapper
): ProductsRepository {

    override suspend fun createProduct(createProduct: CreateProduct) {
        TODO("Not yet implemented")
    }

    override fun isAdmin(): Boolean {
        TODO("Not yet implemented")
    }

    override fun searchProduct(product: String): Flow<Container<List<Product>>> {
        return productsDataRepository.searchProduct(product).map { container ->
            container.map { list ->
                list.map {
                    productMapper.toProduct(it)
                }
            }
        }
    }

    override suspend fun getProductId(id: String): Product {
        val productDataEntity = productsDataRepository.getProductById(id)
        return productMapper.toProduct(productDataEntity)
    }
}