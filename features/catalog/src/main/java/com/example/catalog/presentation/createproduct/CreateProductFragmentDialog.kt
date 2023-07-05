package com.example.catalog.presentation.createproduct

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.catalog.R
import com.example.catalog.databinding.CreateProductDialogBinding
import com.example.catalog.domain.entities.CreateProduct
import com.example.catalog.domain.entities.CreateProductField
import com.example.presentation.live.observeEvent
import com.example.presentation.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProductFragmentDialog: DialogFragment() {

    private val binding by viewBinding<CreateProductDialogBinding>()

    private val viewModel by viewModels<CreateProductViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.create_product, null)
            .setTitle(R.string.create_product_title)
            .setView(R.layout.create_product_dialog)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                viewModel.createProduct(binding.createProduct())
            }
            binding.observeState()
            binding.setupListeners()
        }
        return dialog
    }

    private fun observeEvents(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {

            viewModel.clearFieldLiveEventValue.observeEvent(viewLifecycleOwner){
                getEditTextByField(it).text.clear()
            }

            viewModel.focusFieldLiveEventValue.observeEvent(viewLifecycleOwner) {
                getEditTextByField(it).requestFocus()
                getEditTextByField(it).selectAll()
            }
        }
    }

    private fun CreateProductDialogBinding.observeState(){
        viewModel.stateLiveValue.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isInvisible = !state.showProgressBar

            cleanUpErrors()
            if (state.fieldErrorMessage != null){
                val textInput = getTextInputByField(state.fieldErrorMessage.first)
                textInput.error = state.fieldErrorMessage.second
                textInput.isErrorEnabled = true
            }
        }
    }

    private fun CreateProductDialogBinding.setupListeners(){
        nameProductEditText.addTextChangedListener {viewModel.clearError(CreateProductField.TITLE)}
        descriptionProductEditText.addTextChangedListener { viewModel.clearError(CreateProductField.DESCRIPTION) }
        priceProductEditText.addTextChangedListener { viewModel.clearError(CreateProductField.PRICE) }
    }


    private fun CreateProductDialogBinding.createProduct(): CreateProduct{
        return CreateProduct(
            title = nameProductEditText.text.toString(),
            description = descriptionProductEditText.text.toString(),
            price = priceProductEditText.text.toString()
        )
    }

    private fun CreateProductDialogBinding.cleanUpErrors(){
        nameProductTextInput.error = null
        descriptionProductTextInput.error = null
        priceProductTextInput.error = null
        nameProductTextInput.isErrorEnabled = false
        descriptionProductTextInput.isErrorEnabled = false
        priceProductTextInput.isErrorEnabled = false
    }

    private fun getEditTextByField(field: CreateProductField): EditText {
        return when (field){
            CreateProductField.TITLE -> binding.nameProductEditText
            CreateProductField.DESCRIPTION -> binding.descriptionProductEditText
            CreateProductField.PRICE -> binding.priceProductEditText
        }
    }

    private fun getTextInputByField(field: CreateProductField): TextInputLayout{
        return when (field){
            CreateProductField.TITLE -> binding.nameProductTextInput
            CreateProductField.DESCRIPTION -> binding.descriptionProductTextInput
            CreateProductField.PRICE -> binding.priceProductTextInput
        }
    }

}