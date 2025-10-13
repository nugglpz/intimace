package com.intimace.viewmodel

import androidx.lifecycle.ViewModel
import com.intimace.uistate.SettingsUiState
import com.intimace.uistate.ShoppingCartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShoppingCartViewModel : ViewModel() {
    private val _shoppingCartUiState = MutableStateFlow(ShoppingCartUiState())
    val shoppingCartUiState: StateFlow<ShoppingCartUiState> = _shoppingCartUiState.asStateFlow()
}