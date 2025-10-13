package com.intimace.uistate

import com.intimace.model.Month

data class CalendarUiState (
    val monthIndex: Int = 10,
    val month: Month? = null,
    val year: Int = 2025,
    val highChanceOfPregnancyForecast: String? = null,
    val mediumChanceOfPregnancyForecast: String? = null,
    val lowChanceOfPregnancyForecast: String? = null,
) {
}