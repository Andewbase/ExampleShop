package com.example.catalog

interface CatalogRouter {

    fun launchDetails(productId: String)

    fun goBack()
    fun launchCreateProduct()

}