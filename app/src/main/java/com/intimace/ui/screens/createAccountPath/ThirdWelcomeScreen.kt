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
import androidx.compose.runtime.mutableIntStateOf
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
import com.intimace.ui.components.DatePickerTextField
import com.intimace.uistate.CreateAccountUiState

@Composable
fun ThirdWelcomeScreen(
    createAccountUiState: CreateAccountUiState,
    onContinue: (Boolean?, String?, Long?) -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        var isRegular: Boolean? by remember { mutableStateOf(createAccountUiState.isRegular) }
        var averageCycleDays: String? by remember { mutableStateOf(createAccountUiState.averageCycleDays.toString()) }
        var firstDayOfLastPeriodMillis: Long? by remember { mutableStateOf(createAccountUiState.firstDayOfLastPeriod?.toEpochDay()) }


        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(Modifier.width(4.dp))
                Text("Back", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.height(16.dp))

            Text("Menstrual Cycle", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            Text("Is your cycle regular?", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { isRegular = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isRegular == true)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        else
                            Color.Transparent,
                        contentColor = if (isRegular == true)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (isRegular == true)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray.copy(alpha = 0.5f)
                    ),
                ) {
                    Text("Yes")
                }
                OutlinedButton(
                    onClick = { isRegular = false },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isRegular == false)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        else
                            Color.Transparent,
                        contentColor = if (isRegular == false)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (isRegular == false)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray.copy(alpha = 0.5f)
                    ),
                ) {
                    Text("No")
                }
            }


            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = averageCycleDays.toString(), onValueChange = { averageCycleDays = it }, placeholder = { Text("e.g., 28") }, label = { Text("Average cycle duration (days)") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            Spacer(Modifier.height(8.dp))
            Text("The typical cycle is 28 days, but can range from 21–35 days.", style = MaterialTheme.typography.bodySmall)

            Spacer(Modifier.height(16.dp))
            // add a state to hold the selected date (put this near other state vars in the screen)
            // NEW (millis-based)

            DatePickerTextField(
                label = "First day of last period",
                selectedDateMillis = firstDayOfLastPeriodMillis,
                onDateSelectedMillis = { firstDayOfLastPeriodMillis = it },
                modifier = Modifier.fillMaxWidth()
            )


        }

        BottomNavigationControls(step = 3, totalSteps = 4, onContinue = { onContinue(isRegular, averageCycleDays, firstDayOfLastPeriodMillis) }, onSkip = onSkip)
    }
}