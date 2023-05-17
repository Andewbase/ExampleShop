package com.example.exampleshop.app.screens.main.tabs.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.exampleshop.app.model.Result
import com.example.exampleshop.app.model.accounts.AccountsRepository
import com.example.exampleshop.app.model.products.ProductsRepository
import com.example.exampleshop.app.model.products.entities.Product
import com.example.exampleshop.app.screens.base.BaseViewModel
import com.example.exampleshop.app.utils.logger.Logger
import com.example.exampleshop.app.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    accountsRepository: AccountsRepository,
    logger: Logger
): BaseViewModel(accountsRepository, logger) {

    private var _products: MutableLiveData<Result<List<Product>>> = MutableLiveData()
    val products = _products.share()

    private var _settings: MutableLiveData<Boolean> = MutableLiveData()
    val settings = _settings.share()



    init {
        getProducts()
        checkAccount()
    }

    fun searchProduct(product: String){
        viewModelScope.launch {
            productsRepository.searchProduct(product).collect{
                _products.value = it
            }
        }
    }

    private fun checkAccount(){
        viewModelScope.launch {
            val account = accountsRepository.isAdmin()
            _settings.value = account
        }
    }

    private fun getProducts(){
        viewModelScope.launch {
            productsRepository.getAllProducts().collect{
                _products.value = it
            }
        }
    }



}