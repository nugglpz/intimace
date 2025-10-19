package com.intimace.viewmodel

import androidx.lifecycle.ViewModel
import com.intimace.model.Product
import com.intimace.uistate.SettingsUiState
import com.intimace.uistate.ShoppingCartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShoppingCartViewModel : ViewModel() {
    private val _shoppingCartUiState = MutableStateFlow(ShoppingCartUiState())
    val shoppingCartUiState: StateFlow<ShoppingCartUiState> = _shoppingCartUiState.asStateFlow()

    fun addToCart(product: Product, price: Double) {
        if (!(_shoppingCartUiState.value.products.any { it.first == product })) {
            _shoppingCartUiState.update { currentState ->
                val newProducts = currentState.products + (product to 1)

                // compute subtotal from the *new* products list
                val newSubtotal = currentState.subtotal + price // adjust if price is already Double

                // example shipping rule: $10 per item (change to your rule)
                val newShipping = currentState.shipping + 10.0

                // total derived from subtotal + shipping
                val newTotal = newSubtotal + newShipping

                currentState.copy(
                    products = newProducts,
                    subtotal = newSubtotal,
                    shipping = newShipping,
                    total = newTotal
                )
            }
        }
    }

    fun removeFromCart(product: Product, price: Double) {
        if (_shoppingCartUiState.value.products.any { it.first == product }) {
            _shoppingCartUiState.update { currentState ->
                val quantity: Int = currentState.products.find { it.first == product }?.second ?: 0
                val newProducts = currentState.products - (product to (currentState.products.find{ it.first == product }?.second
                    ?: 0))

                // compute subtotal from the *new* products list
                val newSubtotal = currentState.subtotal - (price * quantity) // adjust if price is already Double
                val newShipping = currentState.shipping - 10.0 // example shipping rule
                val newTotal = newSubtotal + newShipping

                currentState.copy(
                    products = newProducts,
                    subtotal = newSubtotal,
                    shipping = newShipping,
                    total = newTotal
                )
            }
        }
    }

    fun addQuantity(product: Product, price: Double) {
        if (_shoppingCartUiState.value.products.any { it.first == product }) {
            _shoppingCartUiState.update { currentState ->
                val newProducts = currentState.products.map { (productNow, quantity) ->
                    if (productNow == product)
                        productNow to (quantity + 1)
                    else
                        productNow to quantity
                }
                currentState.copy(
                    products = newProducts,
                    subtotal = currentState.subtotal + price,
                    total = currentState.total + price
                )
            }
        }
    }

    fun subtractQuantity(product: Product, price: Double) {
        if (_shoppingCartUiState.value.products.any { it.first == product }) {
            _shoppingCartUiState.update { currentState ->
                val newProducts = currentState.products.map { (productNow, quantity) ->
                    if (productNow == product)
                        productNow to (quantity - 1)
                    else
                        productNow to quantity
                }
                currentState.copy(
                    products = newProducts,
                    subtotal = currentState.subtotal - price,
                    total = currentState.total - price
                )
            }
        }
    }
}