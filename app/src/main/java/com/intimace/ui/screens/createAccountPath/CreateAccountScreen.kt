package com.intimace.ui.screens.createAccountPath

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.ui.components.AuthHeader
import com.intimace.ui.components.AuthTextField
import com.intimace.ui.components.PasswordField
import com.intimace.ui.components.PrimaryButton

@Composable
fun CreateAccountScreen(
    navController: NavHostController = rememberNavController(),
    onCreateAccount: (String, String, String, String) -> Unit,
    onSignIn: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 18.dp)) {

            Card(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .shadow(2.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)) {

                    AuthHeader(title = "Create Account", subtitle = "Sign up to get started")

                    Spacer(modifier = Modifier.height(18.dp))

                    var email by remember { mutableStateOf("") }
                    var username by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    var confirmPassword by remember { mutableStateOf("") }
                    var pin by remember { mutableStateOf("") }

                    var emailError by remember { mutableStateOf<String?>(null) }
                    var passwordError by remember { mutableStateOf<String?>(null) }
                    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
                    var pinError by remember { mutableStateOf<String?>(null) }

                    // Real-time email validation
                    fun validateEmail(input: String): Boolean {
                        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
                    }

                    fun validatePasswordAndConfirmationPassword(): Boolean {
                        if (password.isEmpty() || confirmPassword.isEmpty()) {
                            return false
                        }
                        return password == confirmPassword
                    }

                    // Email field
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


                    AuthTextField(value = username, onValueChange = { username = it }, label = "Username")
                    Spacer(modifier = Modifier.height(12.dp))
                    PasswordField(
                        password = password,
                        onPasswordChange = {
                            password = it
                            passwordError = if (it.isEmpty()) {
                                "Please enter a non-empty password."
                            } else null
                        },
                        label = "Password",
                    )

                    if (passwordError != null) {
                        Text(
                            text = passwordError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    PasswordField(
                        password = confirmPassword,
                        onPasswordChange = {
                            confirmPassword = it
                            confirmPasswordError = if (it.isNotEmpty() && !validatePasswordAndConfirmationPassword()) {
                                "Please enter a non-empty confirmation password."
                            } else null
                        },
                        label = "Re-enter your password",
                    )

                    if (confirmPasswordError != null) {
                        Text(
                            text = confirmPasswordError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                            pinError = if (pin.length != 4 || pin.any { ch -> !ch.isDigit() }) {
                                "Invalid PIN"
                            } else {
                                null
                            }
                        },
                        label = { Text("Safety PIN (for app access)") },
                        placeholder = { Text("4-digit PIN") },
                        trailingIcon = {
                            IconButton(onClick = { /* toggle if desired */ }) {
                                Icon(
                                    imageVector = Icons.Filled.Visibility,
                                    contentDescription = "Toggle PIN visibility"
                                )
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        isError = pinError != null
                    )

                    if (pinError != null) {
                        Text(
                            text = pinError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text("This PIN adds an extra layer of privacy protection", style = MaterialTheme.typography.bodySmall, color = Color.Gray)

                    Spacer(modifier = Modifier.weight(1f))

                    PrimaryButton(
                        text = "Create Account",
                        onClick = { onCreateAccount(email, username, password, pin) },
                        enabled = email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && pin.isNotEmpty() && validatePasswordAndConfirmationPassword()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Text("Already have an account?", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(onClick = onSignIn) {
                            Text("Sign in", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}