package com.example.catalog.presentation.catalog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.catalog.R
import com.example.catalog.databinding.FragmentCatalogBinding
import com.example.catalog.domain.entities.ProductItem
import com.example.core.Container
import com.example.presentation.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private val binding by viewBinding<FragmentCatalogBinding>()

    private val viewModel by viewModels<CatalogViewModel>()

    private val adapter by lazy { CatalogAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerviewProducts.adapter = adapter

        viewModel.adminLiveValue.observe(viewLifecycleOwner){
            if (it){ binding.addProductButton.visibility = View.VISIBLE }
        }

        viewModel.productLiveValue.observe(viewLifecycleOwner){ result ->
            when(result){
                is Container.Success ->{
                    binding.productListProgressBar.visibility = View.INVISIBLE
                    adapter.submitList(result.value)
                }
                is Container.Pending -> binding.productListProgressBar.visibility = View.VISIBLE
                is Container.Error -> Toast.makeText(requireActivity(), "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }

        adapter.setOnClick(object : CatalogAdapter.OnItemClick{
            override fun onItemClick(productItem: ProductItem) {
                viewModel.launchDetails(productItem)
            }
        })
    }
}