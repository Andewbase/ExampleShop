package com.example.exampleshop.app.screens.main.tabs.product


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.exampleshop.R
import com.example.exampleshop.app.model.Empty
import com.example.exampleshop.app.model.Error
import com.example.exampleshop.app.model.Pending
import com.example.exampleshop.app.model.Success
import com.example.exampleshop.app.screens.base.BaseFragment
import com.example.exampleshop.app.screens.main.tabs.product.adapter.ProductAdapter
import com.example.exampleshop.app.screens.main.tabs.product.adapter.ProductItem
import com.example.exampleshop.databinding.FragmentProductListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListProductsFragment : BaseFragment(R.layout.fragment_product_list) {

    private lateinit var binding: FragmentProductListBinding

    override val viewModel by viewModels<ListProductViewModel>()

    private val adapter by lazy { ProductAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductListBinding.bind(view)

        searchResult()

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
                val action =
                    ListProductsFragmentDirections.actionListProductsFragmentToDetailsProductsFragment2(
                        productItem.title
                    )
                findNavController().navigate(action)
            }

        })

        binding.addProductButton.setOnClickListener { findNavController().navigate(goCreateDialog()) }
    }

    private fun searchResult(){
        binding.searchViewProducts.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isBlank() || query == QUERY_EMPTY){
                    viewModel.searchProduct(QUERY_EMPTY)
                }else{
                    viewModel.searchProduct(query)
                    binding.searchViewProducts.clearFocus()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })
    }
    private fun goCreateDialog() = R.id.action_listProductsFragment_to_createProductDialogFragment2

    private companion object{
        const val QUERY_EMPTY = ""
    }

}