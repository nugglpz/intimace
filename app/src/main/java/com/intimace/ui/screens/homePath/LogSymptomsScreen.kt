package com.intimace.ui.screens.homePath

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.components.DatePickerTextField
import com.intimace.ui.components.SymptomChip
import com.intimace.ui.theme.IntimaceTheme

@Composable
fun LogSymptomsScreen(
    navController: NavHostController = rememberNavController(),
    onSave: (Long?, String?, List<String>?, Double?, String?) -> Unit,
    onBack: () -> Unit = {}
) {
    var selectedNav by remember { mutableIntStateOf(1) }
    val scrollState = rememberScrollState()
    var entryDateMillis by remember { mutableLongStateOf(0L) } // use 0L as default, treat 0 as "not set"
    var mood by remember { mutableStateOf<String?>(null) }
    var selectedSymptoms by remember { mutableStateOf<List<String>>(emptyList()) }
    var temperature by remember { mutableStateOf<String?>(null) }
    var notes by remember { mutableStateOf<String?>(null) }

    val symptomsOptions = listOf(
        "Cramps","Headache","Bloating","Breast Tenderness","Acne","Fatigue","Spotting","Heavy Flow","Cravings"
    )

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") }
                Text("Log Symptoms", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            DatePickerTextField(
                label = "Date",
                selectedDateMillis = if (entryDateMillis == 0L) null else entryDateMillis,
                onDateSelectedMillis = { entryDateMillis = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Mood selector (6 emoji tiles)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("How are you feeling today?", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(12.dp))
                    val moods = listOf(
                        "🙂" to "Happy",
                        "😐" to "Neutral",
                        "☹️" to "Sad",
                        "😡" to "Angry",
                        "😴" to "Tired",
                        "🤢" to "Nauseous"
                    )

                    // grid 3 columns
                    val rows = moods.chunked(3)
                    rows.forEach { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            row.forEach { (emoji, label) ->
                                Card(
                                    onClick = { mood = label },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(92.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (mood == label)
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                        else
                                            Color.Transparent,
                                        contentColor = if (mood == label)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.onSurface
                                    ),
                                    border = BorderStroke(
                                        1.dp,
                                        if (mood == label)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            Color.Gray.copy(alpha = 0.5f)
                                    )
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Text(emoji, fontSize = 28.sp)
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(label, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }


            Spacer(modifier = Modifier.height(12.dp))

            // Symptoms chips using LazyVerticalGrid
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp, )) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Symptoms", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(12.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.height(((symptomsOptions.size / 3 + 1) * 56).dp)
                    ) {
                        items(items = symptomsOptions) { s ->
                            SymptomChip(
                                selectedSymptoms = selectedSymptoms,
                                label = s,
                                onToggle = { label ->
                                    selectedSymptoms = if (selectedSymptoms.contains(label)) {
                                        selectedSymptoms - label
                                    } else {
                                        selectedSymptoms + label
                                    }
                                },
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Body Temperature", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = temperature.orEmpty(),
                        onValueChange = { temperature = it },
                        placeholder = { Text("e.g., 98.6") },
                        label = { Text("Your temperature in °F") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Tracking basal body temperature can help identify ovulation patterns", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = notes.orEmpty(),
                onValueChange = { notes = it },
                placeholder = { Text("Add any additional notes here...") },
                label = { Text("Notes") },
                modifier = Modifier.fillMaxWidth().height(140.dp),
                singleLine = false,
                maxLines = 6
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    // Convert temperature safely
                    val tempDouble = temperature?.toDoubleOrNull()
                    // treat entryDateMillis == 0L as null (not set)
                    val dateMillisToPass: Long? = if (entryDateMillis == 0L) null else entryDateMillis
                    onSave(dateMillisToPass, mood, selectedSymptoms.ifEmpty { null }, tempDouble, notes)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(36.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Save")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save Entry", color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogSymptomsScreenPreview() {
    IntimaceTheme { // Assuming IntimaceTheme is your app's theme, as seen in previous previews
        // Mock NavHostController
        val navController = rememberNavController()

        // Render LogSymptomsScreen with mock callbacks
        LogSymptomsScreen(
            navController = navController,
            onSave = { dateMillis, mood, symptoms, temperature, notes ->
                // No-op for preview; simulate saving data
            },
            onBack = { /* No-op for preview */ }
        )
    }
}