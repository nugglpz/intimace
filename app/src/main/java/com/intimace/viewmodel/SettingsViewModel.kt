package com.intimace.viewmodel

import androidx.lifecycle.ViewModel
import com.intimace.data.currentAccount
import com.intimace.uistate.OrderHistoryUiState
import com.intimace.uistate.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime

class SettingsViewModel : ViewModel() {
    private val _settingsUiState = MutableStateFlow(SettingsUiState())
    val settingsUiState: StateFlow<SettingsUiState> = _settingsUiState.asStateFlow()

    fun editProfile(name: String, email: String, age: Int, avgCycle: Int, periodLength: Int): Unit {
        currentAccount = currentAccount.copy(
            name = name,
            email = email,
            age = age,
            averageCycleDays = avgCycle,
            periodLength = periodLength
        )
    }

    fun toggleNotificationSetting(setting: String): Unit {
        _settingsUiState.update { currentState ->
            when(setting) {
                "periodReminder" -> {
                    currentState.copy(
                        periodReminderEnabled = !currentState.periodReminderEnabled
                    )
                }
                "pillReminder" -> {
                    currentState.copy(
                        pillReminderEnabled = !currentState.pillReminderEnabled
                    )
                }
                "ovulationAlert" -> {
                    currentState.copy(
                        ovulationAlertEnabled = !currentState.ovulationAlertEnabled
                    )
                }
                "cycleSummary" -> {
                    currentState.copy(
                        cycleSummaryEnabled = !currentState.cycleSummaryEnabled
                    )
                }
                "healthTips" -> {
                    currentState.copy(
                        healthTipsEnabled = !currentState.healthTipsEnabled
                    )
                }
                "appLock" -> {
                    currentState.copy(
                        appLockEnabled = !currentState.appLockEnabled
                    )
                }
                "hideSensitiveContent" -> {
                    currentState.copy(
                        hideSensitiveContentEnabled = !currentState.hideSensitiveContentEnabled
                    )
                }
                "analytics" -> {
                    currentState.copy(
                        analyticsEnabled = !currentState.analyticsEnabled
                    )
                }

                "cyclePredictionsSharing" -> {
                    currentState.copy(
                        cyclePredictionsSharingEnabled = !currentState.cyclePredictionsSharingEnabled
                    )
                }
                "moodAndSymptomsSharing" -> {
                    currentState.copy(
                        moodAndSymptomsSharingEnabled = !currentState.moodAndSymptomsSharingEnabled
                    )
                }
                "intimateActivitySharing" -> {
                    currentState.copy(
                        intimateActivitySharingEnabled = !currentState.intimateActivitySharingEnabled
                    )
                }
                "notificationsSharing" -> {
                    currentState.copy(
                        notificationsSharingEnabled = !currentState.notificationsSharingEnabled
                    )
                }
                else -> currentState
            }
        }
    }

    fun setTimePreferences(setting: String, time: LocalTime): Unit {
        _settingsUiState.update { currentState ->
            when(setting) {
                "periodReminder" -> {
                    currentState.copy(
                        periodReminderTime = time
                    )
                }

                "pillReminder" -> {
                    currentState.copy(
                        pillReminderTime = time
                    )
                }

                else -> currentState
            }
        }
    }

    fun updatePin(pin: String): Unit {

    }
}