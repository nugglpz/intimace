package com.intimace.uistate

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val resetPasswordTriggered: Boolean = false,
    val resetEmail: String = "",
)