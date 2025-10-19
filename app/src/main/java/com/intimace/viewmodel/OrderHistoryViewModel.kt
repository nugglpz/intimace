package com.intimace.viewmodel

import androidx.lifecycle.ViewModel
import com.intimace.model.Order
import com.intimace.uistate.LogSymptomsUiState
import com.intimace.uistate.OrderHistoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OrderHistoryViewModel : ViewModel() {
    private val _orderHistoryUiState = MutableStateFlow(OrderHistoryUiState())
    val orderHistoryUiState: StateFlow<OrderHistoryUiState> = _orderHistoryUiState.asStateFlow()

    fun setOrders(orders: List<Order>) {
        _orderHistoryUiState.update { currentState ->
            currentState.copy(
                orders = orders
            )
        }
    }
    fun setCurrentOrder(order: Order) {
        _orderHistoryUiState.update { currentState ->
            currentState.copy(
                currentOrder = order
            )
        }

    }

}