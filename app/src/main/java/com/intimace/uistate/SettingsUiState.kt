package com.intimace.uistate

import java.time.LocalTime

class SettingsUiState(
    val periodReminderTime: LocalTime? = null,
    val pillReminderTime: LocalTime? = null,

    val periodReminderEnabled: Boolean = false,
    val pillReminderEnabled: Boolean = false,
    val ovulationAlertEnabled: Boolean = false,
    val cycleSummaryEnabled: Boolean = false,
    val healthTipsEnabled: Boolean = false,
    val appLockEnabled: Boolean = false,
    val analyticsEnabled: Boolean = false,

    val cyclePredictionsSharingEnabled: Boolean = false,
    val moodAndSymptomsSharingEnabled: Boolean = false,
    val intimateActivitySharingEnabled: Boolean = false,
    val notificationsSharingEnabled: Boolean = false,

    val currentPinInput: String? = null,
    val newPinInput: String? = null,
    val confirmNewPinInput: String? = null
) {

}