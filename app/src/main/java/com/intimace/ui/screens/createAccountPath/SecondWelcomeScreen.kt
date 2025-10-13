package com.intimace.ui.screens.createAccountPath

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.intimace.ui.components.BottomNavigationControls

@Composable
fun SecondWelcomeScreen(
    onContinue: (String, Int, String) -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    var sex: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }
    var age: String by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(Modifier.width(4.dp))
                Text("Back", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.height(16.dp))

            Text("Basic Information", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(value = name, onValueChange = {name = it}, placeholder = { Text("How should we call you?") }, label = { Text("Name (optional)") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(value = age, onValueChange = {age = it}, placeholder = { Text("Your age") }, label = { Text("Age") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),)
            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { sex = "female" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (sex == "female")
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        else
                            Color.Transparent,
                        contentColor = if (sex == "female")
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (sex == "female")
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray.copy(alpha = 0.5f)
                    )
                ) {
                    Text("Female")
                }
                OutlinedButton(
                    onClick = { sex = "male" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (sex == "male")
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        else
                            Color.Transparent,
                        contentColor = if (sex == "male")
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (sex == "male")
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray.copy(alpha = 0.5f)
                    )
                ) {
                    Text("Male")
                }
            }

        }

        BottomNavigationControls(step = 2, totalSteps = 4, onContinue = { onContinue(name, age.toInt(), sex) }, onSkip = onSkip)
    }
}