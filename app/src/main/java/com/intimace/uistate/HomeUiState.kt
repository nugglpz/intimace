package com.intimace.uistate

import java.time.LocalDate

data class HomeUiState(
    val justTheTip: String = "",
    val cycleDayNumber: Int? = null,
    val fertilityPrediction: String? = null,
    val phase: String? = null,
    val phaseCompletionPercentage: Double = 0.0,
    val nextPeriodDate: LocalDate? = null,
    val recentInsights: String = ""
) {
}