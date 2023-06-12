package com.example.data.products

import com.example.core.Container
import com.example.core.entities.OnChange
import com.example.core.flow.LazyFlowSubjectFactory
import com.example.data.AccountsDataRepository
import com.example.data.ProductsDataRepository
import com.example.data.products.entities.CreateProduct
import com.example.data.products.entities.ProductDataEntity
import com.example.data.products.sources.ProductsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class RealProductsDataRepository @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository,
    private val productsDataSource: ProductsDataSource,
    private val lazyFlowSubjectFactory: LazyFlowSubjectFactory
): ProductsDataRepository {

    private val updateNotifierFlow = MutableStateFlow(OnChange(Unit))

    override suspend fun createProduct(createProduct: CreateProduct) {
        productsDataSource.createProduct(createProduct)
    }

    override suspend fun getProductById(id: String): ProductDataEntity {
        return productsDataSource.getProductById(id)
    }

    override fun getCurrentIsAdmin(): Boolean {
       return accountsDataRepository.isAdmin()
    }

    override fun searchProduct(product: String): Flow<Container<List<ProductDataEntity>>> {
        return updateNotifierFlow.flatMapLatest {
            lazyFlowSubjectFactory.create{
                productsDataSource.searchProducts(product)
            }.listen()
        }
    }
}