package com.intimace.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.intimace.uistate.CalendarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalendarViewModel : ViewModel() {
    private val _calendarUiState = MutableStateFlow(CalendarUiState())
    val calendarUiState: StateFlow<CalendarUiState> = _calendarUiState.asStateFlow()

    var monthIndex by mutableIntStateOf(10)
    var year by mutableIntStateOf(2025)

    fun decrementMonthIndex() {
        monthIndex --
        if (monthIndex == -1) {
            monthIndex = 11
            year--
        }
        _calendarUiState.update { currentState ->
            currentState.copy(
                    monthIndex = monthIndex,
                    year = year
            )
        }
    }

    fun incrementMonthIndex() {
        monthIndex++
        if (monthIndex == 12) {
            monthIndex = 0
            year++
        }
        _calendarUiState.update { currentState ->
            currentState.copy(
                monthIndex = monthIndex,
                year = year
            )
        }
    }



}