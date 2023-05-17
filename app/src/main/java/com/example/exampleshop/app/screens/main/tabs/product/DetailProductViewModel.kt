package com.example.exampleshop.app.screens.main.tabs.product

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.exampleshop.app.model.Error
import com.example.exampleshop.app.model.Result
import com.example.exampleshop.app.model.Success
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
class DetailProductViewModel @Inject constructor(
    private val repository: ProductsRepository,
    accountsRepository: AccountsRepository,
    logger: Logger
): BaseViewModel(accountsRepository, logger) {

    private var _product: MutableLiveData<Result<List<Product>>> = MutableLiveData()
    val product = _product.share()

    fun getProduct(nameProduct: String){
        viewModelScope.launch {
            repository.searchProduct(nameProduct).collect{
                if (it is Success){
                    _product.value = it
                }else if (it is Error){
                   val e = it.error
                    Log.d("moshiError", "$e")
                }

            }
        }
    }


}