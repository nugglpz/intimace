package com.intimace.uistate

import com.intimace.model.Product

data class CheckoutUiState(
    val products: List<Pair<Product, Int>> = emptyList(),
    val subtotal: Double = 0.0,
    val shipping: Double = 0.0,
    val total: Double = 0.0,
    val fullName: String = "",
    val address: String = "",
    val city: String = "",
    val postalCode: String = "",
    val cardNumber: String = "",
    val expirationDate: String = "",
    val cvv: String = ""
)