package com.example.exampleshop.app.screens.main.tabs.product.dialog


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.exampleshop.R
import com.example.exampleshop.app.model.products.entities.CreateProduct
import com.example.exampleshop.app.screens.base.BaseDialogFragment
import com.example.exampleshop.app.utils.observeEvent
import com.example.exampleshop.databinding.CreateProductDialogBinding

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateProductDialogFragment: BaseDialogFragment<CreateProductDialogBinding>(CreateProductDialogBinding::inflate) {

    private val viewModel by viewModels<CreateProductViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createProductButton.setOnClickListener {createProductButtonPressed()}
        observeState()
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner){
        binding.nameProductTextInput.error = if (it.emptyTitleError) getString(R.string.field_is_empty) else null
        binding.descriptionProductTextInput.error = if (it.emptyDescriptionError) getString(R.string.field_is_empty) else null
        binding.priceProductTextInput.error = if (it.emptyPriceError) getString(R.string.field_is_empty) else null

        binding.nameProductTextInput.isEnabled = it.enableViews
        binding.descriptionProductTextInput.isEnabled = it.enableViews
        binding.priceProductTextInput.isEnabled = it.enableViews
        binding.createProductButton.isEnabled = it.enableViews
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun createProductButtonPressed(){
        val createProduct = CreateProduct(
            title = binding.nameProductEditText.text.toString(),
            description = binding.descriptionProductEditText.text.toString(),
            price = binding.priceProductEditText.text.toString()
        )
        viewModel.createProduct(createProduct)
        observeGoBackEvent()
    }

    private fun observeGoBackEvent() = viewModel.goBackEvent.observeEvent(viewLifecycleOwner){
        val action = R.id.action_createProductDialogFragment2_to_listProductsFragment
        findNavController().navigate(action)
    }



}