package com.intimace.uistate

import com.intimace.model.Product

data class ShoppingCartUiState(
    val products: List<Pair<Product, Int>> = emptyList(),
    val subtotal: Double = 0.0,
    val shipping: Double = 0.0,
    val total: Double = 0.0
)