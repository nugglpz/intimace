package com.intimace.ui.screens.settingsPath

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.intimace.data.currentAccount
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.components.SettingsRow
import com.intimace.uistate.CreateAccountUiState

@Composable
fun SettingsScreen(
    navController: NavHostController = rememberNavController(),
    createAccountUiState: CreateAccountUiState,
    onEditProfile: () -> Unit = {},
    onNotifications: () -> Unit = {},
    onPrivacy: () -> Unit = {},
    onPartnerLink: () -> Unit = {},
    onHelp: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var selectedNav by remember { mutableIntStateOf(4) }
    val scroll = rememberScrollState()

    Scaffold() { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(innerPadding)
            .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text("Settings", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))

            // profile card
            Card(shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp), modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(64.dp).clip(CircleShape).background(Color(0xFFF3E9FF)), contentAlignment = Alignment.Center) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "avatar", tint = Color(0xFF7C3AED))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(currentAccount.name.ifEmpty { "Lovely Human" }, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Text(currentAccount.name.ifEmpty { "No email added" }, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SettingsRow(icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color(0xFF7C3AED)) }, title = "Edit Profile", subtitle = "Personal information & preferences", onClick = onEditProfile)
            Spacer(modifier = Modifier.height(8.dp))
            SettingsRow(icon = { Icon(imageVector = Icons.Default.Notifications, contentDescription = null, tint = Color(0xFF7C3AED)) }, title = "Notifications", subtitle = "Manage alerts & reminders", onClick = onNotifications)
            Spacer(modifier = Modifier.height(8.dp))
            SettingsRow(icon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color(0xFF7C3AED)) }, title = "Privacy & Security", subtitle = "Control data sharing & access", onClick = onPrivacy)
            Spacer(modifier = Modifier.height(8.dp))
            SettingsRow(icon = { Icon(imageVector = Icons.Default.Link, contentDescription = null, tint = Color(0xFF7C3AED)) }, title = "Partner Link Settings", subtitle = "Manage what information is shared", onClick = onPartnerLink)
            Spacer(modifier = Modifier.height(8.dp))
            SettingsRow(icon = { Icon(imageVector = Icons.Default.Help, contentDescription = null, tint = Color(0xFF7C3AED)) }, title = "Help & Support", subtitle = "FAQs, contact us, feedback", onClick = onHelp)
            Spacer(modifier = Modifier.height(8.dp))
            SettingsRow(icon = { Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null, tint = Color(0xFFEF6C6C)) }, title = "Log Out", subtitle = "Sign out of your account", onClick = onLogout, trailingContent = {
                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFFEF6C6C))
            })

            Spacer(modifier = Modifier.height(32.dp))
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Intimace v1.0.0", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text("© 2023 Intimace. All rights reserved.", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}