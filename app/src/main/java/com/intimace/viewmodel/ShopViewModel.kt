package com.intimace.viewmodel

import androidx.lifecycle.ViewModel
import com.intimace.data.products
import com.intimace.model.Product
import com.intimace.uistate.ShopUiState
import com.intimace.uistate.ShoppingCartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShopViewModel : ViewModel() {
    private val _shopUiState = MutableStateFlow(ShopUiState())
    val shopUiState: StateFlow<ShopUiState> = _shopUiState.asStateFlow()

    fun setCurrentProduct(product: Product) {
        _shopUiState.update { currentState ->
            currentState.copy(
                currentProduct = product
            )
        }
    }

    fun setProductList() {
        _shopUiState.update { currentState ->
            currentState.copy(
                productList = products
            )
        }
    }

    fun setSearchQuery(searchQuery: String) {
        _shopUiState.update { currentState ->
            currentState.copy(
                searchQuery = searchQuery
            )
        }
    }


}