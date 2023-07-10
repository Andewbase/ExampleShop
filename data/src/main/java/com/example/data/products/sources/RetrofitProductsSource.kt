package com.example.data.products.sources

import com.example.data.products.entities.CreateProductEntity
import com.example.data.products.entities.ProductDataEntity
import com.example.data.products.sources.retrofit.ProductsApi
import com.example.data.products.sources.retrofit.entities.CreateProductRequestEntity
import com.example.data.products.sources.retrofit.entities.FetchProductRequest
import com.example.data.retrofit.BaseRetrofitSource
import com.example.data.retrofit.RetrofitConfig
import javax.inject.Inject


class RetrofitProductsSource @Inject constructor(
    config: RetrofitConfig
): BaseRetrofitSource(config), ProductsDataSource{

    private val productsApi = retrofit.create(ProductsApi::class.java)

    override suspend fun createProduct(createProduct: CreateProductEntity) {
        val createProductsRequestEntity = CreateProductRequestEntity(
            createProduct.title,
            createProduct.description,
            createProduct.quantity,
            createProduct.price.toDouble()
        )
        productsApi.createProducts(createProductsRequestEntity)
    }

    override suspend fun getProductById(id: String): ProductDataEntity {
        val idProduct = productsApi.getProductById(id)
        return idProduct.toProduct()
    }

    override suspend fun searchProducts(searchQuery: String): List<ProductDataEntity> = wrapRetrofitExceptions {
        val fetchProductRequest = FetchProductRequest(searchQuery = searchQuery)
        productsApi.searchProducts(fetchProductRequest).map {
            it.toProduct()
        }
    }

    override suspend fun getAllProducts(): List<ProductDataEntity> = wrapRetrofitExceptions {
        productsApi.getAllProducts().map { productEntity ->
            productEntity.toProduct()
        }
    }

}