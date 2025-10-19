package com.intimace.model

import androidx.annotation.StringRes
import java.time.LocalDate
import java.time.LocalTime

data class Order(
    val orderId: String,
    val placementDate: LocalDate,
    val status: String,
    val subtotal: Double,
    val shipping: Double,
    val total: Double,
    val address: String,
    val trackingNumber: String,
    val timeline: Timeline,
    val productsOrdered: List<Pair<Product, Int>>
)

data class TimelineStep(
    val status: String,
    val date: LocalDate,
    val time: LocalTime
)

data class Timeline(
    val timelineSteps: List<TimelineStep>
)
