package com.example.exampleshop.app.screens.main.tabs.product

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.exampleshop.R
import com.example.exampleshop.app.model.Empty
import com.example.exampleshop.app.model.Error
import com.example.exampleshop.app.model.Pending
import com.example.exampleshop.app.model.Success
import com.example.exampleshop.app.screens.base.BaseFragment
import com.example.exampleshop.databinding.FragmentDetailsProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsProductsFragment : BaseFragment(R.layout.fragment_details_product) {

    private lateinit var binding: FragmentDetailsProductBinding

    override val viewModel by viewModels<DetailProductViewModel>()

    private val safeArgs: DetailsProductsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsProductBinding.bind(view)

        val nameProduct = safeArgs.nameProduct

        viewModel.getProduct(nameProduct)

        viewModel.product.observe(viewLifecycleOwner){ result ->

            when(result){
                is Success -> {
                    binding.productDetailProgressBar.visibility = View.INVISIBLE
                    binding.productIdTextView.text = result.value.first().productId
                    binding.titleTextView.text = result.value.first().title
                    binding.descriptionTextView.text = result.value.first().description
                    binding.priceTextView.text = result.value.first().price
                }

                is Pending -> binding.productDetailProgressBar.visibility = View.VISIBLE
                is Empty -> Toast.makeText(requireContext(), "Пусто", Toast.LENGTH_SHORT).show()
                is Error -> Toast.makeText(requireActivity(), "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }

}