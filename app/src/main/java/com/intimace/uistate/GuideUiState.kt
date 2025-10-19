package com.intimace.uistate

import com.intimace.model.Guide

data class GuideUiState(
    val guidesList: List<Guide> = emptyList(),
    val guideIndex: Int = 0
) {
}