package com.intimace.uistate

import com.intimace.model.Order

data class OrderHistoryUiState(
    val orders: List<Order> = emptyList(),
    val currentOrder: Order = com.intimace.data.orders[0]
) {
}