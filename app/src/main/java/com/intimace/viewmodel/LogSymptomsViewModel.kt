package com.intimace.viewmodel

import androidx.lifecycle.ViewModel
import com.intimace.uistate.LogSymptomsUiState
import com.intimace.uistate.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.time.ZoneId

class LogSymptomsViewModel : ViewModel() {
    private val _logSymptomsUiState = MutableStateFlow(LogSymptomsUiState())
    val logSymptomsUiState: StateFlow<LogSymptomsUiState> = _logSymptomsUiState.asStateFlow()

    fun initializeAll(
        entryDateMillis: Long?,
        mood: String?,
        symptoms: List<String>?,
        bodyTemperature: Double?,
        notes: String?
    ) {
        _logSymptomsUiState.update { currentState ->
            currentState.copy(
                date = entryDateMillis?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate() },
                mood = mood ?: "",
                symptoms = symptoms ?: emptyList(),
                bodyTemperature = bodyTemperature ?: 0.0,
                notes = notes ?: ""
            )
        }
    }
}
