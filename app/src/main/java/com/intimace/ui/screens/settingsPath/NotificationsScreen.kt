package com.intimace.ui.screens.settingsPath

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.components.SettingsRow
import com.intimace.ui.components.TimePickerTextField
import com.intimace.ui.components.WhiteOutlinedFieldTrailing

@Composable
fun NotificationsScreen(
    navController: NavHostController = rememberNavController(),
    onBack: () -> Unit = {}
) {
    var selectedNav by remember { mutableIntStateOf(4) }
    val scroll = rememberScrollState()

    var periodReminder by remember { mutableStateOf(true) }
    var pillReminder by remember { mutableStateOf(true) }
    var ovulationAlert by remember { mutableStateOf(true) }
    var cycleSummary by remember { mutableStateOf(false) }
    var healthTips by remember { mutableStateOf(true) }

    var periodTime by remember { mutableStateOf("8 PM") }
    var pillTime by remember { mutableStateOf("8 AM") }
    var entryTimeMillis by remember { mutableStateOf<Long?>(null) }

    Scaffold { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(innerPadding)
            .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onBack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }

                Text("Notifications", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(12.dp))

            SettingsRow(icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color(0xFF7C3AED)) }, title = "Notification Settings", subtitle = "Choose which notifications you'd like to receive and how you'd like to receive them", onClick = null, showArrow = false)
            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Period Reminder", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Get notified when your period is approaching", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(checked = periodReminder, onCheckedChange = { periodReminder = it })
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Pill Reminder", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Daily reminder to take birth control", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(checked = pillReminder, onCheckedChange = { pillReminder = it })
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Ovulation Alert", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Get notified during fertile window", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(checked = ovulationAlert, onCheckedChange = { ovulationAlert = it })
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Cycle Summary", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Weekly summary of your cycle data", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(checked = cycleSummary, onCheckedChange = { cycleSummary = it })
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Health Tips", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Receive health and wellness tips", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(checked = healthTips, onCheckedChange = { healthTips = it })
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Notification Time Preferences", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    TimePickerTextField(
                        label = "Period Reminder Time",
                        selectedTime = entryTimeMillis,
                        onTimeSelected = { entryTimeMillis = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    var entryTimeMillis by remember { mutableStateOf<Long?>(null) }
                    TimePickerTextField(
                        label = "Pill Reminder Time",
                        selectedTime = entryTimeMillis,
                        onTimeSelected = { entryTimeMillis = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}