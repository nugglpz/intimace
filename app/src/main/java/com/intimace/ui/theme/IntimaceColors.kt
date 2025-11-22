// File: com.intimace.ui.theme/IntimaceColors.kt
package com.intimace.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val IntimacePurple = Color(0xFF640068)
val IntimaceLightPurple = Color(0xFFF8F4FF)
val IntimaceGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFFF8F4FF),
        Color(0xFFE8DAFF),
        Color(0xFFD8C9FF)
    )
)

// Optional: Phase colors (so you never hardcode again)
val MenstruationColor = Color(0xFFFFECEC)
val FollicularColor = Color(0xFFDCEBFF)
val OvulationColor = Color(0xFFF3E8FF)
val LutealColor = Color(0xFFFFF2D6)