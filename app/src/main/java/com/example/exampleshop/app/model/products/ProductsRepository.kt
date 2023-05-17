package com.example.exampleshop.app.model.products

import com.example.exampleshop.app.model.Empty
import com.example.exampleshop.app.model.Result
import com.example.exampleshop.app.model.Success
import com.example.exampleshop.app.model.accounts.AccountsRepository
import com.example.exampleshop.app.model.accounts.entities.Account
import com.example.exampleshop.app.model.products.entities.CreateProduct
import com.example.exampleshop.app.model.products.entities.Product
import com.example.exampleshop.app.model.wrapBackendExceptions
import com.example.exampleshop.app.utils.async.LazyFlowSubject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepository @Inject constructor(
    private val accountsRepository: AccountsRepository,
    private val productsSource: ProductsSource
) {

    private var accountResult: Result<Account> = Empty()

    private val productsLazyFlowSubject = LazyFlowSubject<Unit, List<Product>>{
        wrapBackendExceptions { productsSource.getAllProducts() }
    }

    private val productLazyFlowSubject = LazyFlowSubject<String, List<Product>>{ nameProduct ->
        wrapBackendExceptions { productsSource.searchProducts(nameProduct) }
    }

    suspend fun createProduct(createProduct: CreateProduct){
        createProduct.validate()
        productsSource.createProduct(createProduct)
    }

    fun searchProduct(product: String): Flow<Result<List<Product>>> {
        return accountsRepository.getAccount().onEach {
            accountResult = it
        }.flatMapLatest { result ->
            if (result is Success){
                productLazyFlowSubject.listen(product)
            } else {
                flowOf(result.map())
            }
        }
    }

    fun getAllProducts(): Flow<Result<List<Product>>> {
       return productsLazyFlowSubject.listen(Unit)
    }
}