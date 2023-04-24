package com.example.exampleshop.app.screens.main.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exampleshop.app.ConstToken.ADMIN_TOKEN
import com.example.exampleshop.app.model.Result
import com.example.exampleshop.app.model.products.ProductsRepository
import com.example.exampleshop.app.model.products.entities.Product
import com.example.exampleshop.app.model.settings.AppSettings
import com.example.exampleshop.app.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val appSettings: AppSettings
): ViewModel() {

    private var _products: MutableLiveData<Result<List<Product>>> = MutableLiveData()
    val products = _products.share()

    private var _settings: MutableLiveData<Boolean> = MutableLiveData()
    val settings = _settings.share()



    init {
        getProducts()
    }

    private fun getProducts(){
        viewModelScope.launch {
            productsRepository.getAllProducts().collect{
                _products.value = it
            }
        }
    }

    private fun checkUser(){
        viewModelScope.launch {
           val user = appSettings.getCurrentToken()
            _settings.value = user == ADMIN_TOKEN
        }
    }

}