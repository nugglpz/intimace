package com.intimace.uistate

import com.intimace.model.Product

class ShoppingCartUiState(
    val products: List<Product> = emptyList(),
    val subtotal: Double = 0.0,
    val shipping: Double = 0.0,
    val total: Double = 0.0
) {
}