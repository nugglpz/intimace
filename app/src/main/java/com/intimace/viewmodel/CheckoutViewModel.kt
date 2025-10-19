package com.intimace.viewmodel

import androidx.lifecycle.ViewModel
import com.intimace.data.orders
import com.intimace.model.Order
import com.intimace.model.Product
import com.intimace.model.Timeline
import com.intimace.model.TimelineStep
import com.intimace.uistate.CalendarUiState
import com.intimace.uistate.CheckoutUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalTime
import kotlin.collections.List

class CheckoutViewModel : ViewModel() {
    private val _checkoutUiState = MutableStateFlow(CheckoutUiState())
    val checkoutUiState: StateFlow<CheckoutUiState> = _checkoutUiState.asStateFlow()

    fun placeOrder(products: List<Pair<Product, Int>>, subtotal: Double, shipping: Double, total: Double, shipFullName: String, shipAddress: String, shipCity: String, shipPostal: String, cardNumber: String, cardExpiry: String, cardCvv: String) {
        _checkoutUiState.update { currentState ->
            currentState.copy(
                products = products,
                subtotal = subtotal,
                shipping = shipping,
                total = total,
                fullName = shipFullName,
                address = shipAddress,
                city = shipCity,
                postalCode = shipPostal,
                cardNumber = cardNumber,
                expirationDate = cardExpiry,
                cvv = cardCvv
            )
        }

        val newOrder = Order(
            orderId = "ORD-2025-${kotlin.random.Random.nextInt(1000, 9999)}",
            placementDate = LocalDate.now(),
            status = "Order Placed",
            subtotal = subtotal,
            shipping = shipping,
            total = total,
            address = shipAddress,
            trackingNumber = "TRK${kotlin.random.Random.nextInt(0, 99999)}${kotlin.random.Random.nextInt(0, 99999)}PH",
            timeline = Timeline(
                listOf(
                    TimelineStep(
                        "Order Placed",
                        LocalDate.now(),
                        LocalTime.now()
                    )
                )
            ),
            productsOrdered = products
        )
        orders = orders + newOrder
    }

}