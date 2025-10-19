package com.intimace.viewmodel

import androidx.lifecycle.ViewModel
import com.intimace.data.currentAccount
import com.intimace.uistate.CalendarUiState
import com.intimace.uistate.CreateAccountUiState
import com.intimace.uistate.Sex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.Instant
import java.time.ZoneId

class CreateAccountViewModel : ViewModel() {
    private val _createAccountUiState = MutableStateFlow(CreateAccountUiState())
    val createAccountUiState: StateFlow<CreateAccountUiState> = _createAccountUiState.asStateFlow()

    fun initializeEmailUsernamePasswordPin(email: String, username: String, password: String, pin: String): Unit {
        _createAccountUiState.value = _createAccountUiState.value.copy(
            email = email,
            username = username,
            password = password,
            pin = pin
        )
    }

    fun initializeNameAgeSex(name: String, age: Int, sex: String): Unit {
        val sexValue = if (sex == "male") Sex.MALE else Sex.FEMALE
        _createAccountUiState.value = _createAccountUiState.value.copy(
            name = name,
            age = age,
            sex = sexValue
        )
    }

    fun initializeIsRegularAverageCycleDaysFirstDayOfLastPeriod(isRegular: Boolean?, averageCycleDays: String?, firstDayOfLastPeriodMillis: Long?): Unit {
        _createAccountUiState.value = _createAccountUiState.value.copy(
            isRegular = isRegular,
            averageCycleDays = averageCycleDays?.toInt(),
            firstDayOfLastPeriod = if (firstDayOfLastPeriodMillis != null) {Instant.ofEpochMilli(firstDayOfLastPeriodMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()} else null
        )
    }

    fun initializeIsSexuallyActive(isSexuallyActive: Boolean?): Unit {
        _createAccountUiState.value = _createAccountUiState.value.copy(
            isSexuallyActive = isSexuallyActive
        )

        currentAccount = currentAccount.copy(
            email = _createAccountUiState.value.email,
            username = _createAccountUiState.value.username,
            password = _createAccountUiState.value.password,
            pin = _createAccountUiState.value.pin,
            name = _createAccountUiState.value.name,
            age = _createAccountUiState.value.age,
            sex = _createAccountUiState.value.sex,
            isRegular = _createAccountUiState.value.isRegular,
            averageCycleDays = _createAccountUiState.value.averageCycleDays,
            firstDayOfLastPeriod = _createAccountUiState.value.firstDayOfLastPeriod
        )
    }
}