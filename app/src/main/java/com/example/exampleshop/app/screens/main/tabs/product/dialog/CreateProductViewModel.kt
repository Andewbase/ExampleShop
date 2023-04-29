package com.example.exampleshop.app.screens.main.tabs.product.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exampleshop.app.model.EmptyProductException
import com.example.exampleshop.app.model.field.ProductField
import com.example.exampleshop.app.model.products.ProductsRepository
import com.example.exampleshop.app.model.products.entities.CreateProduct
import com.example.exampleshop.app.utils.MutableUnitLiveEvent
import com.example.exampleshop.app.utils.publishEvent
import com.example.exampleshop.app.utils.requireValue
import com.example.exampleshop.app.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val repository: ProductsRepository
): ViewModel() {

    private val _goBackEvent = MutableUnitLiveEvent()
    val goBackEvent = _goBackEvent.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    fun createProduct(createProduct: CreateProduct){
        viewModelScope.launch {
            showProgress()
            try {
                repository.createProduct(createProduct)
                goBack()
            }catch (e: EmptyProductException){
                processEmptyFieldException(e)
            }
            finally {
                hideProgress()
            }
        }
    }

    private fun processEmptyFieldException(e: EmptyProductException){
        _state.value = _state.requireValue().copy(
            emptyTitleError = e.productField == ProductField.TITLE,
            emptyDescriptionError = e.productField == ProductField.DESCRIPTION,
            emptyPriceError = e.productField == ProductField.PRICE
        )
    }

    private fun showProgress() {
        _state.value = State(productProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(productProgress = false)
    }

    private fun goBack() = _goBackEvent.publishEvent()

    data class State(
        val emptyTitleError: Boolean = false,
        val emptyDescriptionError: Boolean = false,
        val emptyPriceError: Boolean = false,
        val productProgress: Boolean = false
    ){
        val showProgress: Boolean get() = productProgress
        val enableViews: Boolean get() = !productProgress
    }

}