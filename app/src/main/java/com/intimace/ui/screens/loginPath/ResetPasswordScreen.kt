package com.intimace.ui.screens.loginPath

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.ui.components.AuthTextField
import com.intimace.ui.components.PrimaryButton

@Composable
fun ResetPasswordScreen(
    navController: NavHostController = rememberNavController(),
    onSend: (String) -> Unit = {}
) {
    var email by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)) {

                    // <-- Back-to-login row above the title
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { /* navigate back */ }, modifier = Modifier.size(36.dp)) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                        TextButton(onClick = { /* navigate back */ }) {
                            Text("Back to login", color = MaterialTheme.colorScheme.primary)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Title + instructions
                    Text("Reset Password", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Enter the email associated with your account and we'll send a reset link.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(18.dp))

                    var emailError by remember { mutableStateOf<String?>(null) }

                    // Real-time email validation
                    fun validateEmail(input: String): Boolean {
                        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
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

                    Spacer(modifier = Modifier.weight(1f))

                    // Send button
                    PrimaryButton(text = "Send Reset Link",
                        onClick = { onSend(email) },
                        enabled = emailError == null && email.isNotEmpty()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // <-- "Remember your password?" container below the Send button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.06f))
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("Remember your password?", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.width(8.dp))
                            TextButton(onClick = { /* navigate to login */ }) {
                                Text("Back to login", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}