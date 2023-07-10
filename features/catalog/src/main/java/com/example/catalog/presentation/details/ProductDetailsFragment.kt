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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
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

        with(binding){
            observeState()
        }
    }

    private fun FragmentProductDetailsBinding.observeState(){
        viewModel.productFlow.observe(viewLifecycleOwner){container ->
            when(container){
                is Container.Success ->{
                    productDetailProgressBar.visibility = View.INVISIBLE
                    productIdValueTextView.text = container.value.productId
                    titleValueTextView.text = container.value.title
                    descriptionValueTextView.text = container.value.description
                    priceValueTextView.text = container.value.price
                    quantityValueTextView.text = container.value.quantity
                }
                is Container.Pending -> productDetailProgressBar.visibility = View.VISIBLE
                is Container.Error -> Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }

}