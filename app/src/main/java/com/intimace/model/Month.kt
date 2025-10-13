package com.intimace.model

import androidx.compose.ui.graphics.Color

enum class MenstruationPhase(val color: Color) {
    MENSTRUATION(Color(0xFFFFECEC)),
    FOLLICULAR(Color(0xFFDCEBFF)),
    OVULATION(Color(0xFFF3E8FF)),
    LUTEAL(Color(0xFFFFF2D6))
}

enum class FertilityForecast(val color: Color) {
    HIGH(Color(0xFFFF6B6B)),
    MEDIUM(Color(0xFFFFC857)),
    LOW(Color(0xFF5BD078))
}

data class Day (
    val phase: String,
    val phaseColor: Color = MenstruationPhase.valueOf(phase).color,
    val forecast: String,
    val forecastColor: Color = FertilityForecast.valueOf(forecast).color
)

data class Month(val index: Int, val days: List<Day>) {
}