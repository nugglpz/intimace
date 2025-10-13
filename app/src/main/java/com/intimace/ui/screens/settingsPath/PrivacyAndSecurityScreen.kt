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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun PrivacyAndSecurityScreen(
    navController: NavHostController = rememberNavController(),
    onBack: () -> Unit = {}
) {
    var selectedNav by remember { mutableIntStateOf(4) }
    val scroll = rememberScrollState()

    var appLock by remember { mutableStateOf(true) }
    var biometric by remember { mutableStateOf(false) }
    var hideSensitive by remember { mutableStateOf(true) }
    var analytics by remember { mutableStateOf(true) }

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
                Text("Privacy & Security", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(12.dp))

            SettingsRow(icon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color(0xFF7C3AED)) }, title = "Security Settings", subtitle = "Control your privacy preferences and security settings to protect your data", onClick = null, showArrow = false)
            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("App Lock", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Require PIN to access the app", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(checked = appLock, onCheckedChange = { appLock = it })
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Biometric Authentication", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Use fingerprint or Face ID", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(checked = biometric, onCheckedChange = { biometric = it })
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Hide Sensitive Content", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Blur sensitive information when app is minimized", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(checked = hideSensitive, onCheckedChange = { hideSensitive = it })
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Analytics", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Help improve the app by sharing usage data", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Switch(checked = analytics, onCheckedChange = { analytics = it })
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Change PIN block
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Change PIN", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Update your 4-digit security PIN used to access the app", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { /* navigate to change PIN */ }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(36.dp)) {
                        Text("Change PIN")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Danger zone delete account
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Data Management", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { /* delete account */ }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(36.dp), colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF7C3AED))) {
                        Text("Delete My Account")
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}