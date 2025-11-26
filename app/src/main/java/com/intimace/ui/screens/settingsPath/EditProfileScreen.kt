package com.intimace.ui.screens.settingsPath

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intimace.ui.components.PrimaryButton
import com.intimace.ui.components.WhiteOutlinedFieldTrailing
import com.intimace.ui.theme.IntimaceGradient
import com.intimace.ui.theme.IntimacePurple
import com.intimace.ui.theme.IntimaceTheme

@Composable
fun EditProfileScreen(
    onBack: () -> Unit = {},
    onSave: (String, String, Int, Int, Int) -> Unit = { _, _, _, _, _ -> }
) {
    var name by remember { mutableStateOf("Maria Lopez") }
    var email by remember { mutableStateOf("maria@intimace.ph") }
    var age by remember { mutableStateOf("28") }
    var avgCycle by remember { mutableStateOf("28") }
    var periodLength by remember { mutableStateOf("5") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IntimaceGradient)           // YOUR SIGNATURE GRADIENT
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = IntimacePurple,          // YOUR ROYAL PURPLE
                    modifier = Modifier.size(28.dp)
                )
            }

            Text(
                text = "Edit Profile",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = IntimacePurple,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(Modifier.height(32.dp))

        // Personal Information Card
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Personal Information",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = IntimacePurple
                )
                Spacer(Modifier.height(16.dp))

                WhiteOutlinedFieldTrailing(
                    value = name,
                    onValueChange = { name = it },
                    labelText = "Name",
                    placeholderText = "Your full name"
                )
                Spacer(Modifier.height(12.dp))

                WhiteOutlinedFieldTrailing(
                    value = email,
                    onValueChange = { email = it },
                    labelText = "Email",
                    placeholderText = "your@email.com"
                )
                Spacer(Modifier.height(12.dp))

                WhiteOutlinedFieldTrailing(
                    value = age,
                    onValueChange = { if (it.all { char -> char.isDigit() } || it.isEmpty()) age = it },
                    labelText = "Age",
                    placeholderText = "Your age",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Cycle Information Card
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Cycle Information",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = IntimacePurple
                )
                Spacer(Modifier.height(16.dp))

                WhiteOutlinedFieldTrailing(
                    value = avgCycle,
                    onValueChange = { if (it.all { char -> char.isDigit() } || it.isEmpty()) avgCycle = it },
                    labelText = "Average Cycle Length (days)",
                    placeholderText = "e.g. 28"
                )
                Spacer(Modifier.height(12.dp))

                WhiteOutlinedFieldTrailing(
                    value = periodLength,
                    onValueChange = { if (it.all { char -> char.isDigit() } || it.isEmpty()) periodLength = it },
                    labelText = "Period Length (days)",
                    placeholderText = "e.g. 5"
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // Save Button
        PrimaryButton(
            text = "Save Changes",
            onClick = {
                onSave(
                    name,
                    email,
                    age.toIntOrNull() ?: 0,
                    avgCycle.toIntOrNull() ?: 28,
                    periodLength.toIntOrNull() ?: 5
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
        )

        Spacer(Modifier.height(140.dp)) // safe space for bottom nav
    }
}

// GORGEOUS PREVIEW — WORKS 100%
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditProfileScreenPreview() {
    IntimaceTheme {
        EditProfileScreen(
            onBack = {},
            onSave = { _, _, _, _, _ -> }
        )
    }
}