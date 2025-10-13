package com.intimace.viewmodel

import androidx.lifecycle.ViewModel
import com.intimace.uistate.ShopUiState
import com.intimace.uistate.ShoppingCartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShopViewModel : ViewModel() {
    private val _shopUiState = MutableStateFlow(ShopUiState())
    val shopUiState: StateFlow<ShopUiState> = _shopUiState.asStateFlow()
}