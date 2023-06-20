package com.example.catalog.presentation.details

import com.example.catalog.domain.GetProductDetailsUseCase
import com.example.catalog.presentation.details.ProductDetailsFragment.Screen
import com.example.presentation.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ProductDetailsViewModel @AssistedInject constructor(
    @Assisted private val screen: Screen,
    private val getProductDetailsUseCase: GetProductDetailsUseCase
): BaseViewModel() {

    val productFlow = getProductDetailsUseCase.getProduct(screen.productId).toLiveValue()



    @AssistedFactory
    interface Factory {
        fun create(screen: Screen): ProductDetailsViewModel
    }

}