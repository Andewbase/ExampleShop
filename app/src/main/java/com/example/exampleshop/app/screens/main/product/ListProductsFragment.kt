package com.example.exampleshop.app.screens.main.product


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.exampleshop.R
import com.example.exampleshop.app.model.Empty
import com.example.exampleshop.app.model.Error
import com.example.exampleshop.app.model.Pending
import com.example.exampleshop.app.model.Success
import com.example.exampleshop.app.screens.base.BaseFragment
import com.example.exampleshop.app.screens.main.product.adapter.ProductAdapter
import com.example.exampleshop.app.screens.main.product.adapter.ProductItem
import com.example.exampleshop.databinding.FragmentProductListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListProductsFragment : BaseFragment<FragmentProductListBinding>(FragmentProductListBinding::inflate) {

    override val viewModel by viewModels<ListProductViewModel>()

    private val adapter by lazy { ProductAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerviewProducts.adapter = adapter

        viewModel.settings.observe(viewLifecycleOwner){
            if (it){
                binding.addProductButton.visibility = View.VISIBLE
            }
        }

        viewModel.products.observe(viewLifecycleOwner){ result ->

            when(result){
                is Success ->{
                    binding.productListProgressBar.visibility = View.INVISIBLE
                    adapter.submitList(
                        result.value.map {
                            it.toProductItem()
                        }
                    )
                }
                is Pending -> binding.productListProgressBar.visibility = View.VISIBLE
                is Error -> Toast.makeText(requireActivity(), "Ошибка", Toast.LENGTH_SHORT).show()
                is Empty -> Toast.makeText(requireContext(), "Пусто", Toast.LENGTH_SHORT).show()
            }

        }

        adapter.setOnClick(object : ProductAdapter.OnItemClick{
            override fun onItemClick(productItem: ProductItem) {
                val action = ListProductsFragmentDirections.actionProductListFragmentToDetailsProductsFragment(productItem.title)
                findNavController().navigate(action)
            }

        })

        binding.addProductButton.setOnClickListener { findNavController().navigate(goCreateDialog()) }
    }

    private fun goCreateDialog() = R.id.action_productListFragment_to_createProductDialogFragment

}