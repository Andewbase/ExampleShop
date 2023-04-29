package com.example.exampleshop.app.model.products

import com.example.exampleshop.app.model.Result
import com.example.exampleshop.app.model.products.entities.CreateProduct
import com.example.exampleshop.app.model.products.entities.Product
import com.example.exampleshop.app.model.wrapBackendExceptions
import com.example.exampleshop.app.utils.async.LazyFlowSubject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsSource: ProductsSource
) {

    private val productsLazyFlowSubject = LazyFlowSubject<Unit, List<Product>>{
        wrapBackendExceptions { productsSource.getAllProducts() }
    }

    private val productLazyFlowSubject = LazyFlowSubject<String, List<Product>>{ nameProduct ->
        wrapBackendExceptions { productsSource.getProduct(nameProduct) }
    }

    suspend fun createProduct(createProduct: CreateProduct){
        createProduct.validate()
        productsSource.createProduct(createProduct)
    }

    fun getAllProducts(): Flow<Result<List<Product>>> {
       return productsLazyFlowSubject.listen(Unit)
    }

    fun getProduct(product: String): Flow<Result<List<Product>>>{
        return productLazyFlowSubject.listen(product)
    }

}