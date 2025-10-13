package com.intimace.ui.screens.settingsPath

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.components.SettingsRow

@Composable
fun PartnerLinkScreen(
    navController: NavHostController = rememberNavController(),
    onBack: () -> Unit = {},
    onNewPartner: () -> Unit = {}) {
    var selectedNav by remember { mutableIntStateOf(4) }
    val scroll = rememberScrollState()

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

                Text("Partner Link Settings", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(12.dp))

            SettingsRow(icon = { Icon(imageVector = Icons.Default.Link, contentDescription = null, tint = Color(0xFF7C3AED)) }, title = "Partner Sharing", subtitle = "Control what information is shared with your connected partner", onClick = null, showArrow = false)
            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Connected Partner", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(56.dp).clip(CircleShape).background(Color(0xFFE6F0FF)), contentAlignment = Alignment.Center) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = "partner", tint = Color(0xFF3B82F6))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Alex Smith", fontWeight = FontWeight.SemiBold)
                            Text("Connected since May 15, 2023", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = { /* change partner */ }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(36.dp)) {
                            Text("Change Partner")
                        }
                        Button(onClick = { /* unlink */ }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(36.dp)) {
                            Text("Unlink")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Information sharing toggles
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Information Sharing", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(12.dp))

                    var sharePredictions by remember { mutableStateOf(true) }
                    var shareMood by remember { mutableStateOf(false) }
                    var shareActivity by remember { mutableStateOf(true) }
                    var shareNotifications by remember { mutableStateOf(true) }

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Cycle Predictions", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Period and fertility window dates", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(checked = sharePredictions, onCheckedChange = { sharePredictions = it })
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Mood & Symptoms", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Share logged mood and symptoms", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(checked = shareMood, onCheckedChange = { shareMood = it })
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Intimate Activity", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Share intimate activity logs", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(checked = shareActivity, onCheckedChange = { shareActivity = it })
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Notifications", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Allow partner to receive notifications", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(checked = shareNotifications, onCheckedChange = { shareNotifications = it })
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}