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
    val sex: Sex = Sex.FEMALE,
    val isRegular: Boolean? = null,
    val averageCycleDays: Int? = null,
    val firstDayOfLastPeriod: LocalDate? = null,
    val isSexuallyActive: Boolean? = null,
    val hasPartner: Boolean = true // TODO: make false when working on partner link feature
)

data class Account(
    var email: String = "",
    var username: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var pin: String = "",
    val name: String = "",
    var age: Int? = null,
    val sex: Sex = Sex.FEMALE,
    var isRegular: Boolean? = null,
    var averageCycleDays: Int? = null,
    var periodLength: Int? = null,
    var firstDayOfLastPeriod: LocalDate? = null,
    var isSexuallyActive: Boolean? = null,
    var hasPartner: Boolean = true // TODO: make false when working on partner link feature
)

enum class Sex {
    MALE, FEMALE
}