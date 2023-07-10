package com.example.catalog.presentation.createproduct

import com.example.catalog.CatalogRouter
import com.example.catalog.R
import com.example.catalog.domain.GetCreateProductUseCase
import com.example.catalog.domain.entities.CreateProduct
import com.example.catalog.domain.entities.CreateProductField
import com.example.catalog.domain.exceptions.EmptyFieldException
import com.example.catalog.domain.exceptions.ProducttAlreadyExistsException
import com.example.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val createProductUseCase: GetCreateProductUseCase,
    private val catalogRouter: CatalogRouter
): BaseViewModel() {

    private val isCreateProductInProgressFlow = MutableStateFlow(false)
    private val fieldErrorMessageFlow = MutableStateFlow<Pair<CreateProductField, String>?>(null)

    private val focusFieldLiveEventValue = liveEvent<CreateProductField>()

    val stateLiveValue = combine(
        isCreateProductInProgressFlow,
        fieldErrorMessageFlow,
        ::State
    ).toLiveValue()

    fun createProduct(createProduct: CreateProduct) = debounce{
        viewModelScope.launch {
            try {
                showProgress()
                createProductUseCase.createProduct(createProduct)
                commonUi.toast(resources.getString(R.string.create_product_success))
                catalogRouter.goBack()
            }catch (e: EmptyFieldException){
                handleEmptyFieldException(e)
            }catch (e: ProducttAlreadyExistsException){
                handleProductAlreadyExistException()
            }finally {
                hideProgress()
            }
        }
    }

    fun clearError(field: CreateProductField){
        val currentErrorField = fieldErrorMessageFlow.value?.first
        if (currentErrorField == field) {
            fieldErrorMessageFlow.value = null
        }
    }

    private fun handleEmptyFieldException(emptyFieldException: EmptyFieldException) {
        focusField(emptyFieldException.field)
        setFieldError(emptyFieldException.field, resources.getString(R.string.field_is_empty))
    }

    private fun handleProductAlreadyExistException(){
        focusField(CreateProductField.TITLE)
        setFieldError(CreateProductField.TITLE, resources.getString(R.string.product_already_exists))
    }

    private fun showProgress(){
        isCreateProductInProgressFlow.value = true
    }

    private fun hideProgress(){
        isCreateProductInProgressFlow.value = false
    }

    private fun focusField(field: CreateProductField){
        focusFieldLiveEventValue.publish(field)
    }

    private fun setFieldError(field: CreateProductField, errorMessage: String){
        fieldErrorMessageFlow.value = field to errorMessage
    }

    data class State(
        val createProductInProgress: Boolean,
        val fieldErrorMessage: Pair<CreateProductField, String>?
    ){
        val showProgressBar: Boolean get() = createProductInProgress
    }

}