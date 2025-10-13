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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.intimace.ui.components.BottomNavigationControls

@Composable
fun FourthWelcomeScreen(
    onComplete: (Boolean?) -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        var isSexuallyActive: Boolean? by remember { mutableStateOf(null) }

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(Modifier.width(4.dp))
                Text("Back", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.height(16.dp))

            Text("Sexual Activity", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            Text("Are you sexually active?", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { isSexuallyActive = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Yes")
                }
                OutlinedButton(
                    onClick = { isSexuallyActive = false },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("No")
                }
            }

            Spacer(Modifier.height(16.dp))
            Text("This information helps us provide personalized health insights and recommendations.", style = MaterialTheme.typography.bodySmall)
        }

        BottomNavigationControls(step = 4, totalSteps = 4, onContinue = { onComplete(isSexuallyActive) }, onSkip = onSkip, isFinalStep = true)
    }
}
