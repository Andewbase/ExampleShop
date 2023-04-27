package com.example.exampleshop.app.model.products

import com.example.exampleshop.app.model.AccountAlreadyExistsException
import com.example.exampleshop.app.model.BackendException
import com.example.exampleshop.app.model.Result
import com.example.exampleshop.app.model.products.entities.CreateProdut
import com.example.exampleshop.app.model.products.entities.Product
import com.example.exampleshop.app.model.products.entities.Products
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

    suspend fun createProdut(createProdut: CreateProdut){
        //TODO: validate

        try {
            productsSource.createProdut(createProdut)
        } catch (e: BackendException) {
            // user with such email already exists
            if (e.code == 409) throw AccountAlreadyExistsException(e)
            else throw e
        }
    }

    fun getAllProducts(): Flow<Result<List<Product>>> {
       return productsLazyFlowSubject.listen(Unit)
    }

    fun getProduct(product: String): Flow<Result<List<Product>>>{
        return productLazyFlowSubject.listen(product)
    }

}