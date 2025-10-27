package com.intimace.ui.screens.loginPath

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.ui.components.AuthHeader
import com.intimace.ui.components.AuthTextField
import com.intimace.ui.components.PasswordField
import com.intimace.ui.components.PrimaryButton

@Composable
fun LoginScreen(
    navController: NavHostController = rememberNavController(),
    onCreateAccount: () -> Unit,
    onForgotPassword: () -> Unit,
    onSignIn: (String, String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .shadow(2.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 18.dp)
                ) {
                    AuthHeader(title = "Welcome back", subtitle = "Sign in to continue")

                    Spacer(modifier = Modifier.height(18.dp))

                    var email by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    var emailError by remember { mutableStateOf<String?>(null) }

                    // Real-time email validation
                    fun validateEmail(input: String): Boolean {
                        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
                    }

                    // Email field with validation
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = if (it.isNotEmpty() && !validateEmail(it)) {
                                "Please enter a valid email address"
                            } else null
                        },
                        label = { Text("Email") },
                        isError = emailError != null,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (emailError != null) {
                        Text(
                            text = emailError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    PasswordField(
                        password = password,
                        onPasswordChange = { password = it },
                        label = "Password"
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onForgotPassword) {
                            Text("Forgot password?", color = MaterialTheme.colorScheme.primary)
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Disable Sign In button if invalid
                    PrimaryButton(
                        text = "Sign In",
                        onClick = {
                            if (validateEmail(email)) {
                                onSignIn(email, password)
                            } else {
                                emailError = "Please enter a valid email address"
                            }
                        },
                        enabled = emailError == null && email.isNotEmpty() && password.isNotEmpty()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Don't have an account?",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(onClick = onCreateAccount) {
                            Text("Sign up", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            navController = rememberNavController(),
            onCreateAccount = {},
            onForgotPassword = {},
            onSignIn = { _, _ -> }
        )
    }
}

