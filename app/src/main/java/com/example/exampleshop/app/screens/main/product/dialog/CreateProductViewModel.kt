package com.example.exampleshop.app.screens.main.product.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exampleshop.app.model.products.ProductsRepository
import com.example.exampleshop.app.model.products.entities.CreateProdut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val repository: ProductsRepository
): ViewModel() {

    fun createProduct(createProdut: CreateProdut){
        viewModelScope.launch {
            repository.createProdut(createProdut)
        }
    }

}