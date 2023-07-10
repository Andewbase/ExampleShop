package com.example.catalog.presentation.createproduct

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.example.catalog.R
import com.example.catalog.databinding.CreateProductDialogBinding
import com.example.catalog.domain.entities.CreateProduct
import com.example.catalog.domain.entities.CreateProductField
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProductFragmentDialog: DialogFragment() {

    private val viewModel by viewModels<CreateProductViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.create_product, null)
            .setTitle(R.string.create_product_title)
            .setView(R.layout.create_product_dialog)
            .create()
        dialog.setOnShowListener {
            val view = dialog.findViewById<View>(R.id.dialogRoot)!!
            val binding = CreateProductDialogBinding.bind(view)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                binding.apply {
                    viewModel.createProduct(createProduct())
                    setupListeners()
                }
            }
            binding.observeState(dialog)
        }
        return dialog
    }

    private fun CreateProductDialogBinding.observeState(lifecycleOwner: LifecycleOwner){
        viewModel.stateLiveValue.observe(lifecycleOwner) { state ->
            progressBar.isInvisible = !state.showProgressBar

            cleanUpErrors()
            if (state.fieldErrorMessage != null){
                val textInput = getTextInputByField(state.fieldErrorMessage.first, nameProductTextInput, descriptionProductTextInput, quantityProductTextInput, priceProductTextInput)
                textInput.error = state.fieldErrorMessage.second
                textInput.isErrorEnabled = true
            }
        }
    }

    private fun CreateProductDialogBinding.setupListeners(){
        nameProductEditText.addTextChangedListener {viewModel.clearError(CreateProductField.TITLE)}
        descriptionProductEditText.addTextChangedListener { viewModel.clearError(CreateProductField.DESCRIPTION) }
        quantityProductEditText.addTextChangedListener { viewModel.clearError(CreateProductField.QUANTITY) }
        priceProductEditText.addTextChangedListener { viewModel.clearError(CreateProductField.PRICE) }
    }


    private fun CreateProductDialogBinding.createProduct(): CreateProduct{
        return CreateProduct(
            title = nameProductEditText.text.toString(),
            description = descriptionProductEditText.text.toString(),
            quantity = quantityProductEditText.text.toString(),
            price = priceProductEditText.text.toString()
        )
    }

    private fun CreateProductDialogBinding.cleanUpErrors(){
        nameProductTextInput.error = null
        descriptionProductTextInput.error = null
        quantityProductTextInput.error = null
        priceProductTextInput.error = null
        nameProductTextInput.isErrorEnabled = false
        descriptionProductTextInput.isErrorEnabled = false
        quantityProductTextInput.isErrorEnabled = false
        priceProductTextInput.isErrorEnabled = false
    }

    private fun getTextInputByField(
        field: CreateProductField,
        nameProductTextInput: TextInputLayout,
        descriptionProductTextInput: TextInputLayout,
        quantityTextInput: TextInputLayout,
        priceProductTextInput: TextInputLayout
    ): TextInputLayout{
        return when (field){
            CreateProductField.TITLE -> nameProductTextInput
            CreateProductField.DESCRIPTION -> descriptionProductTextInput
            CreateProductField.QUANTITY -> quantityTextInput
            CreateProductField.PRICE -> priceProductTextInput
        }
    }

}