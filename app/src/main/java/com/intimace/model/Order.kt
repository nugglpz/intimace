package com.intimace.model

import androidx.annotation.StringRes

data class Order(
    @StringRes val orderId: Int,
    @StringRes val placementDate: Int,
    @StringRes val status: Int,
    @StringRes val subtotal: Int,
    @StringRes val shipping: Int,
    @StringRes val total: Int,
    @StringRes val address: Int,
    @StringRes val trackingNumber: Int,
    val productsOrdered: List<Product>
)
