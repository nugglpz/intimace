package com.intimace.viewmodel

import androidx.lifecycle.ViewModel
import com.intimace.uistate.LogSymptomsUiState
import com.intimace.uistate.OrderHistoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderHistoryViewModel : ViewModel() {
    private val _orderHistoryUiState = MutableStateFlow(OrderHistoryUiState())
    val orderHistoryUiState: StateFlow<OrderHistoryUiState> = _orderHistoryUiState.asStateFlow()
}