package com.intimace.uistate

import com.intimace.model.Product
import com.intimace.data.products

data class ShopUiState(
    val productList: List<Product>? = null,
    val currentProduct: Product = products[0],
    val searchQuery: String = ""
) {
}