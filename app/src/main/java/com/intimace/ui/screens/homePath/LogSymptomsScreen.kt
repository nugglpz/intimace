package com.intimace.ui.screens.homePath

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intimace.ui.components.DatePickerTextField
import com.intimace.ui.components.SymptomChip
import com.intimace.ui.theme.IntimaceGradient
import com.intimace.ui.theme.IntimacePurple
import com.intimace.ui.theme.IntimaceTheme

@Composable
fun LogSymptomsScreen(
    onSave: (Long?, String?, List<String>?, Double?, String?) -> Unit,
    onBack: () -> Unit = {}
) {
    var entryDateMillis by remember { mutableStateOf<Long?>(null) }
    var mood by remember { mutableStateOf<String?>(null) }
    var selectedSymptoms by remember { mutableStateOf<List<String>>(emptyList()) }
    var temperature by remember { mutableStateOf<String?>(null) }
    var notes by remember { mutableStateOf<String?>(null) }

    val symptomsOptions = listOf(
        "Cramps", "Headache", "Bloating", "Breast Tenderness",
        "Acne", "Fatigue", "Spotting", "Heavy Flow", "Cravings"
    )
    val moods = listOf("Happy", "Neutral", "Sad", "Angry", "Tired", "Nauseous")

    // THIS IS THE MAGIC FIX
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenHeight = maxHeight

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(IntimaceGradient)
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
                .heightIn(max = screenHeight) // THIS LINE KILLS THE INFINITE CONSTRAINT
        ) {
            Spacer(Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = IntimacePurple)
                }
                Text("Log Symptoms", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = IntimacePurple)
            }

            Spacer(Modifier.height(20.dp))

            DatePickerTextField(
                label = "Date",
                selectedDateMillis = entryDateMillis,
                onDateSelectedMillis = { entryDateMillis = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            // Mood Card
            // Mood Selector – WHITE CARD
            // Mood Selector – SMALL TEXT ONLY (big emoji text gone)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text("How are you feeling today?", fontWeight = FontWeight.SemiBold, color = IntimacePurple)
                    Spacer(Modifier.height(16.dp))

                    // Real emojis + small label below (big text removed)
                    val moods = listOf(
                        "Happy" to "Happy",
                        "Neutral" to "Neutral",
                        "Sad" to "Sad",
                        "Angry" to "Angry",
                        "Tired" to "Tired",
                        "Nauseous" to "Nauseous"
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(moods) { (emoji, label) ->
                            val selected = mood == label
                            Card(
                                onClick = { mood = if (selected) null else label },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (selected) IntimacePurple.copy(alpha = 0.12f) else Color.White
                                ),
                                border = BorderStroke(2.dp, if (selected) IntimacePurple else Color(0xFFE0E0E0)),
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 8.dp else 4.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize().padding(vertical = 12.dp)
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.bodySmall,  // ← small text stays
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selected) IntimacePurple else Color.Unspecified
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Symptoms Card
            Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(12.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp)) {
                    Text("Symptoms", fontWeight = FontWeight.SemiBold, color = IntimacePurple)
                    Spacer(Modifier.height(16.dp))
                    LazyVerticalGrid(columns = GridCells.Fixed(3), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(symptomsOptions) { symptom ->
                            SymptomChip(
                                selectedSymptoms = selectedSymptoms,
                                label = symptom,
                                onToggle = { label ->
                                    selectedSymptoms = if (selectedSymptoms.contains(label)) selectedSymptoms - label else selectedSymptoms + label
                                },
                                modifier = Modifier.height(48.dp)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Temperature Card
            Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(12.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp)) {
                    Text("Body Temperature", fontWeight = FontWeight.SemiBold, color = IntimacePurple)
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = temperature.orEmpty(),
                        onValueChange = { temperature = it },
                        placeholder = { Text("e.g., 98.6") },
                        label = { Text("Temperature in °F") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = IntimacePurple, unfocusedBorderColor = Color(0xFFE0E0E0))
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Tracking basal body temperature can help identify ovulation patterns", style = MaterialTheme.typography.bodySmall, color = Color(0xFF888888))
                }
            }

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = notes.orEmpty(),
                onValueChange = { notes = it },
                label = { Text("Notes") },
                placeholder = { Text("Add any additional notes here...") },
                modifier = Modifier.fillMaxWidth().height(160.dp),
                maxLines = 6,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = IntimacePurple, unfocusedBorderColor = Color(0xFFE0E0E0))
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    val tempDouble = temperature?.toDoubleOrNull()
                    onSave(entryDateMillis, mood, selectedSymptoms.ifEmpty { null }, tempDouble, notes)
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IntimacePurple),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 12.dp)
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(12.dp))
                Text("Save Entry", color = Color.White, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(140.dp)) // Extra safe space for bottom nav + keyboard
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogSymptomsScreenPreview() {
    IntimaceTheme {
        LogSymptomsScreen(onSave = { _, _, _, _, _ -> }, onBack = {})
    }
}