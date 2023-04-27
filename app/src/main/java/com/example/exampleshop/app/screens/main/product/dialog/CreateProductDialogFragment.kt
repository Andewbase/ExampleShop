package com.example.exampleshop.app.screens.main.product.dialog


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.exampleshop.R
import com.example.exampleshop.app.model.products.entities.CreateProdut
import com.example.exampleshop.app.screens.base.BaseDialogFragment
import com.example.exampleshop.databinding.CreateProductDialogBinding

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateProductDialogFragment: BaseDialogFragment<CreateProductDialogBinding>(CreateProductDialogBinding::inflate) {


    private val viewModel by viewModels<CreateProductViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createProductButton.setOnClickListener {createProduct()}

    }

    private fun createProduct() {
        val nameProduct = binding.nameProductEditText.text.toString()
        val descriptionProduct = binding.descriptionProductEditText.text.toString()
        val priceProduct = binding.priceProductEditText.text.toString()
        val createProduct = CreateProdut(
            title = nameProduct,
            description = descriptionProduct,
            price = priceProduct.toDouble()
        )
        viewModel.createProduct(createProduct)
        val action = R.id.action_createProductDialogFragment_to_productListFragment
        findNavController().navigate(action)
    }



}