package com.intimace.uistate

import java.time.LocalTime

data class SettingsUiState(
    val periodReminderTime: LocalTime? = null,
    val pillReminderTime: LocalTime? = null,

    val periodReminderEnabled: Boolean = false,
    val pillReminderEnabled: Boolean = false,
    val ovulationAlertEnabled: Boolean = false,
    val cycleSummaryEnabled: Boolean = false,
    val healthTipsEnabled: Boolean = false,
    val appLockEnabled: Boolean = false,
    val hideSensitiveContentEnabled: Boolean = false,
    val analyticsEnabled: Boolean = false,

    val cyclePredictionsSharingEnabled: Boolean = false,
    val moodAndSymptomsSharingEnabled: Boolean = false,
    val intimateActivitySharingEnabled: Boolean = false,
    val notificationsSharingEnabled: Boolean = false,
)

data class AppSettings(
    val periodReminderTime: LocalTime? = null,
    val pillReminderTime: LocalTime? = null,

    val periodReminderEnabled: Boolean = false,
    val pillReminderEnabled: Boolean = false,
    val ovulationAlertEnabled: Boolean = false,
    val cycleSummaryEnabled: Boolean = false,
    val healthTipsEnabled: Boolean = false,
    val appLockEnabled: Boolean = false,
    val hideSensitiveContentEnabled: Boolean = false,
    val analyticsEnabled: Boolean = false,

    val cyclePredictionsSharingEnabled: Boolean = false,
    val moodAndSymptomsSharingEnabled: Boolean = false,
    val intimateActivitySharingEnabled: Boolean = false,
    val notificationsSharingEnabled: Boolean = false,
)