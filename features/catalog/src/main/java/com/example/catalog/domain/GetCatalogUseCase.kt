package com.example.catalog.domain

import com.example.catalog.domain.entities.ProductItem
import com.example.catalog.domain.repositories.ProductsRepository
import com.example.core.Container
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCatalogUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {

    fun getProductsItem(nameProduct: String): Flow<Container<List<ProductItem>>>{
        if (nameProduct.isNotBlank()){
            return productsRepository.searchProduct(nameProduct).map { container ->
                container.map { list ->
                    list.map { product ->
                        ProductItem(
                            product.productId,
                            product.title,
                            product.price
                        )
                    }
                }
            }
        }else{
            return productsRepository.searchProduct(QUERY_EMPTY).map { container ->
                container.map { list ->
                    list.map { product ->
                        ProductItem(
                            product.productId,
                            product.title,
                            product.price
                        )
                    }
                }
            }
        }
    }

    fun isAdmin(): Boolean{
       return productsRepository.isAdmin()
    }


    private companion object{
        const val QUERY_EMPTY = ""
    }

}