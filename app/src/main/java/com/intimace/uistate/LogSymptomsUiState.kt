package com.intimace.uistate

import java.time.LocalDate

data class LogSymptomsUiState(
    val date: LocalDate? = null,
    val mood: String? = null,
    val symptoms: List<String> = emptyList(),
    val bodyTemperature: Double? = null,
    val notes: String? = null
)
