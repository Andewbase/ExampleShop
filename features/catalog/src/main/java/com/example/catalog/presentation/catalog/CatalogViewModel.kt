package com.example.catalog.presentation.catalog

import com.example.catalog.CatalogRouter
import com.example.catalog.domain.GetCatalogUseCase
import com.example.catalog.domain.entities.ProductItem
import com.example.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCatalogUseCase: GetCatalogUseCase,
    private val catalogRouter: CatalogRouter
): BaseViewModel() {



    fun launchDetails(productItem: ProductItem) = debounce {
        catalogRouter.launchDetails(productItem.productId)
    }


}