package com.intimace.viewmodel

import androidx.lifecycle.ViewModel
import com.intimace.uistate.HomeUiState
import com.intimace.uistate.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.util.Patterns
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    fun logUserIn(email: String, password: String) {
        _loginUiState.update { currentState ->
            currentState.copy(
                email = email,
                password = password,
                resetPasswordTriggered = false
            )
        }
    }

    fun initializeResetEmail(email: String) {
        _loginUiState.update { currentState ->
            currentState.copy(
                resetEmail = email,
                resetPasswordTriggered = false
            )
        }
    }
}
