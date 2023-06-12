package com.example.exampleshop.glue.catalog

import com.example.catalog.CatalogRouter
import com.example.catalog.presentation.details.ProductDetailsFragment
import com.example.exampleshop.R
import com.example.navigation.GlobalNavComponentRouter
import javax.inject.Inject

class AdapterCatalogRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
): CatalogRouter {

    override fun launchDetails(productId: String) {
        globalNavComponentRouter.launch(
            R.id.productDetailsFragment,
            ProductDetailsFragment.Screen(productId)
        )
    }

}