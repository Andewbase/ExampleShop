package com.example.catalog.presentation.catalog

import com.example.catalog.CatalogRouter
import com.example.catalog.domain.GetCatalogUseCase
import com.example.catalog.domain.entities.ProductItem
import com.example.core.Container
import com.example.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class CatalogViewModel @Inject constructor(
    private val getCatalogUseCase: GetCatalogUseCase,
    private val catalogRouter: CatalogRouter
): BaseViewModel() {

    val productLiveValue = getCatalogUseCase.getProductsItem("").toLiveValue(initialValue = Container.Pending)

    val adminLiveValue = liveValue<Boolean>()

    init {
        observeAdmin()
    }

    fun launchDetails(productItem: ProductItem) = debounce {
        catalogRouter.launchDetails(productItem.productId)
    }

    fun launchCreateProduct() = debounce {
        catalogRouter.launchCreateProduct()
    }

    private fun observeAdmin(){
        viewModelScope.launch {
            adminLiveValue.value = getCatalogUseCase.isAdmin()
        }
    }


}