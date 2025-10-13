package com.intimace.uistate

import java.time.LocalDate

data class CreateAccountUiState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val pin: String = "",
    val name: String = "",
    val age: Int? = null,
    val sex: String = "",
    val isRegular: Boolean? = null,
    val averageCycleDays: Int? = null,
    val firstDayOfLastPeriod: LocalDate? = null,
    val isSexuallyActive: Boolean? = null
) {
}