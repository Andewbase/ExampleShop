package com.example.catalog.presentation.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.catalog.R
import com.example.catalog.databinding.FragmentProductDetailsBinding
import com.example.core.Container
import com.example.presentation.BaseScreen
import com.example.presentation.args
import com.example.presentation.viewBinding
import com.example.presentation.viewModelCreator
import javax.inject.Inject

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    class Screen(
        val productId: String
    ) : BaseScreen

    @Inject
    lateinit var factory: ProductDetailsViewModel.Factory

    private val viewModel by viewModelCreator { factory.create(args()) }

    private val binding by viewBinding<FragmentProductDetailsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.productFlow.observe(viewLifecycleOwner){ container ->
            when(container){
                is Container.Success ->{
                    binding.productDetailProgressBar.visibility = View.INVISIBLE
                    binding.productIdTextView.text = container.value.productId
                    binding.titleTextView.text = container.value.title
                    binding.descriptionTextView.text = container.value.description
                    binding.priceTextView.text = container.value.price
                }
                is Container.Pending -> binding.productDetailProgressBar.visibility = View.VISIBLE
                is Container.Error -> Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }

}