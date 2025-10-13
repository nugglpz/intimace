package com.intimace.uistate

import com.intimace.model.Order

data class OrderHistoryUiState(
    val orders: List<Order>? = null,
    val orderIndex: Int? = 0,
) {
}