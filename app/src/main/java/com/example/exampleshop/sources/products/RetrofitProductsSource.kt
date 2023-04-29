package com.example.exampleshop.sources.products

import com.example.exampleshop.app.model.products.ProductsSource
import com.example.exampleshop.app.model.products.entities.CreateProduct
import com.example.exampleshop.app.model.products.entities.Product
import com.example.exampleshop.app.model.products.entities.Products
import com.example.exampleshop.sources.base.BaseRetrofitSource
import com.example.exampleshop.sources.base.RetrofitConfig
import com.example.exampleshop.sources.products.entities.CreateProductRequestEntity
import com.example.exampleshop.sources.products.entities.FetchProductRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitProductsSource @Inject constructor(
    config: RetrofitConfig
): BaseRetrofitSource(config), ProductsSource {

    private val productsApi = retrofit.create(ProductsApi::class.java)

    override suspend fun createProduct(createProduct: CreateProduct) {
        val createProductsRequestEntity = CreateProductRequestEntity(createProduct.title, createProduct.description, createProduct.price.toDouble())
        productsApi.createProducts(createProductsRequestEntity)
    }

    override suspend fun searchProducts(searchQuery: String): Products {
        val fetchProductRequest = FetchProductRequest(searchQuery)

       return productsApi.searchProducts(fetchProductRequest).toProducts()
    }

    override suspend fun getAllProducts(): List<Product> = wrapRetrofitExceptions {
       productsApi.getAllProducts().map { productEntity ->
           productEntity.toProduct()
       }
    }

    override suspend fun getProduct(searchQuery: String): List<Product> = wrapRetrofitExceptions {
        val fetchProductRequest = FetchProductRequest(searchQuery = searchQuery)
        productsApi.getProduct(fetchProductRequest).map {
            it.toProduct()
        }
    }


}