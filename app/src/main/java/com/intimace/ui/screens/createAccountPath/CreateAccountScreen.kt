package com.intimace.ui.screens.createAccountPath

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intimace.ui.components.AuthHeader
import com.intimace.ui.components.PasswordField
import com.intimace.ui.components.PrimaryButton
import com.intimace.ui.theme.IntimacePurple
import com.intimace.ui.theme.IntimaceTheme

@Composable
fun CreateAccountScreen(
    onCreateAccount: (String, String, String, String) -> Unit,
    onSignIn: () -> Unit
) {
    IntimaceTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(40.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(28.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 28.dp, vertical = 32.dp)
                    ) {
                        AuthHeader(
                            title = "Create Account",
                            subtitle = "Sign up to get started"
                        )

                        Spacer(Modifier.height(32.dp))

                        var email by remember { mutableStateOf("") }
                        var username by remember { mutableStateOf("") }
                        var password by remember { mutableStateOf("") }
                        var confirmPassword by remember { mutableStateOf("") }
                        var pin by remember { mutableStateOf("") }

                        var emailError by remember { mutableStateOf<String?>(null) }
                        var passwordError by remember { mutableStateOf<String?>(null) }
                        var confirmPasswordError by remember { mutableStateOf<String?>(null) }
                        var pinError by remember { mutableStateOf<String?>(null) }

                        fun validateEmail(input: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
                        fun passwordsMatch() = password.isNotEmpty() && password == confirmPassword
                        fun isValidPin() = pin.length == 4 && pin.all { it.isDigit() }

                        // Email
                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it
                                emailError = if (it.isNotEmpty() && !validateEmail(it)) "Invalid email address" else null
                            },
                            label = { Text("Email") },
                            isError = emailError != null,
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = IntimacePurple,
                                unfocusedBorderColor = Color(0xFFE0E0E0),
                                errorBorderColor = Color.Red
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        emailError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(start = 16.dp, top = 4.dp)) }

                        Spacer(Modifier.height(16.dp))

                        // Username
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Username") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = IntimacePurple,
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(16.dp))

                        // Password
                        PasswordField(
                            password = password,
                            onPasswordChange = { password = it },
                            label = "Password"
                        )

                        Spacer(Modifier.height(16.dp))

                        // Confirm Password
                        PasswordField(
                            password = confirmPassword,
                            onPasswordChange = {
                                confirmPassword = it
                                confirmPasswordError = if (it.isNotEmpty() && !passwordsMatch()) "Passwords do not match" else null
                            },
                            label = "Confirm Password"
                        )

// Show error manually below (just like we do for email & PIN)
                        confirmPasswordError?.let {
                            Text(
                                text = it,
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        // Safety PIN
                        OutlinedTextField(
                            value = pin,
                            onValueChange = {
                                if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                                    pin = it
                                    pinError = if (it.length == 4) null else "PIN must be exactly 4 digits"
                                }
                            },
                            label = { Text("Safety PIN (4 digits)") },
                            placeholder = { Text("••••") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = IntimacePurple,
                                unfocusedBorderColor = Color(0xFFE0E0E0),
                                errorBorderColor = Color.Red
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (pinError != null) {
                            Text(pinError!!, color = Color.Red, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
                        }

                        Spacer(Modifier.height(8.dp))
                        Text(
                            "This PIN adds an extra layer of privacy protection",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF888888)
                        )

                        Spacer(Modifier.weight(1f))

                        // Create Account Button — always IntimacePurple
                        PrimaryButton(
                            text = "Create Account",
                            onClick = { onCreateAccount(email, username, password, pin) },
                            enabled = email.isNotEmpty() &&
                                    username.isNotEmpty() &&
                                    password.isNotEmpty() &&
                                    passwordsMatch() &&
                                    isValidPin(),
                            containerColor = IntimacePurple,
                            contentColor = Color.White
                        )

                        Spacer(Modifier.height(20.dp))

                        // Sign In Link
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Already have an account? ", color = Color(0xFF666666))
                            TextButton(onClick = onSignIn) {
                                Text("Sign in", color = IntimacePurple, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateAccountScreenPreview() {
    IntimaceTheme {
        CreateAccountScreen(
            onCreateAccount = { _, _, _, _ -> },
            onSignIn = {}
        )
    }
}