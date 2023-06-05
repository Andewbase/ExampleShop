package com.example.catalog.presentation.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.catalog.databinding.ItemProductBinding
import com.example.catalog.domain.entities.ProductItem

class CatalogAdapter: ListAdapter<ProductItem, CatalogAdapter.ProductViewHolder>(
    DiffUtilCallbackProduct
) {

    private var onItemClick: OnItemClick? = null

    fun setOnClick(onItemClick: OnItemClick){
        this.onItemClick = onItemClick
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(productItem: ProductItem){
            with(binding){
                productIdTextView.text = productItem.productId
                titleTextView.text = productItem.title
                priceTextView.text = productItem.price

                root.setOnClickListener{ onItemClick?.onItemClick(productItem) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productItem = getItem(position)

        holder.bind(productItem)
    }

    interface OnItemClick{
        fun onItemClick(productItem: ProductItem)
    }

    companion object DiffUtilCallbackProduct: DiffUtil.ItemCallback<ProductItem>() {

        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean =
            oldItem.productId == newItem.productId

        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean =
            oldItem == newItem

    }
}