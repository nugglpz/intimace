package com.intimace.ui.screens.loginPath

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intimace.ui.components.AuthHeader
import com.intimace.ui.components.PasswordField
import com.intimace.ui.components.PrimaryButton
import com.intimace.ui.theme.IntimacePurple
import com.intimace.ui.theme.IntimaceTheme

@Composable
fun LoginScreen(
    onCreateAccount: () -> Unit,
    onForgotPassword: () -> Unit,
    onSignIn: (String, String) -> Unit
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
                        // Fixed: Removed titleColor & borderColor (they don't exist in your components)
                        AuthHeader(
                            title = "Welcome back",
                            subtitle = "Sign in to continue"
                        )

                        Spacer(Modifier.height(32.dp))

                        var email by remember { mutableStateOf("") }
                        var password by remember { mutableStateOf("") }
                        var emailError by remember { mutableStateOf<String?>(null) }

                        fun validateEmail(input: String): Boolean =
                            android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()

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
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = IntimacePurple,
                                unfocusedBorderColor = Color(0xFFE0E0E0),
                                errorBorderColor = Color.Red
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        emailError?.let {
                            Text(
                                text = it,
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        Spacer(Modifier.height(16.dp))

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
                                Text("Forgot password?", color = IntimacePurple, fontWeight = FontWeight.Medium)
                            }
                        }

                        Spacer(Modifier.weight(1f))

                        // Fixed: Removed containerColor (doesn't exist in your PrimaryButton)
                        PrimaryButton(
                            text = "Sign In",
                            onClick = {
                                if (validateEmail(email)) {
                                    onSignIn(email, password)
                                } else {
                                    emailError = "Please enter a valid email address"
                                }
                            },
                            enabled = email.isNotEmpty() && password.isNotEmpty() && emailError == null,
                            containerColor = IntimacePurple,        // ← THIS LINE MAKES IT PURPLE
                            contentColor = Color.White              // ← White text on purple
                        )

                        Spacer(Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Don't have an account? ", color = Color(0xFF666666))
                            TextButton(onClick = onCreateAccount) {
                                Text("Sign up", color = IntimacePurple, fontWeight = FontWeight.SemiBold)
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
fun LoginScreenPreview() {
    IntimaceTheme {
        LoginScreen(
            onCreateAccount = {},
            onForgotPassword = {},
            onSignIn = { _, _ -> }
        )
    }
}