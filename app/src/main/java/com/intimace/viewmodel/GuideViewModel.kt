package com.intimace.viewmodel

import androidx.lifecycle.ViewModel
import com.intimace.uistate.CreateAccountUiState
import com.intimace.uistate.GuideUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GuideViewModel : ViewModel() {
    private val _guideUiState = MutableStateFlow(GuideUiState())
    val guideUiState: StateFlow<GuideUiState> = _guideUiState.asStateFlow()

    fun setGuideIndex(index: Int) {
        _guideUiState.update { currentState ->
            currentState.copy(
                guideIndex = index
            )
        }
    }
}