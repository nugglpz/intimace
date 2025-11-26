package com.intimace.ui.screens.settingsPath

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intimace.data.currentAccount
import com.intimace.ui.components.SettingsRow
import com.intimace.uistate.CreateAccountUiState
import com.intimace.ui.theme.*

@Composable
fun SettingsScreen(
    createAccountUiState: CreateAccountUiState,
    onEditProfile: () -> Unit = {},
    onNotifications: () -> Unit = {},
    onPrivacy: () -> Unit = {},
    onPartnerLink: () -> Unit = {},
    onHelp: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IntimaceGradient)           // YOUR ROYAL GRADIENT
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(20.dp))

        // Header
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = IntimacePurple
        )

        Spacer(Modifier.height(32.dp))

        // Profile Card — Premium White
        Card(
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar Circle
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(IntimacePurple.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = IntimacePurple,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(Modifier.width(20.dp))

                Column {
                    Text(
                        text = currentAccount.name.ifEmpty { "Lovely Human" },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = IntimacePurple
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = currentAccount.email.ifEmpty { "No email added" },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF666666)
                    )
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        // Settings Rows — Clean & Elegant
        SettingsRow(
            icon = { Icon(Icons.Default.Person, null, tint = IntimacePurple) },
            title = "Edit Profile",
            subtitle = "Personal information & preferences",
            onClick = onEditProfile
        )
        Spacer(Modifier.height(12.dp))

        SettingsRow(
            icon = { Icon(Icons.Default.Notifications, null, tint = IntimacePurple) },
            title = "Notifications",
            subtitle = "Manage alerts & reminders",
            onClick = onNotifications
        )
        Spacer(Modifier.height(12.dp))

        SettingsRow(
            icon = { Icon(Icons.Default.Lock, null, tint = IntimacePurple) },
            title = "Privacy & Security",
            subtitle = "Control data sharing & access",
            onClick = onPrivacy
        )
        Spacer(Modifier.height(12.dp))

        SettingsRow(
            icon = { Icon(Icons.Default.Link, null, tint = IntimacePurple) },
            title = "Partner Link Settings",
            subtitle = "Manage what information is shared",
            onClick = onPartnerLink
        )
        Spacer(Modifier.height(12.dp))

        SettingsRow(
            icon = { Icon(Icons.Default.Help, null, tint = IntimacePurple) },
            title = "Help & Support",
            subtitle = "FAQs, contact us, feedback",
            onClick = onHelp
        )
        Spacer(Modifier.height(24.dp))

        // LOG OUT — RED FOR IMPACT
        SettingsRow(
            icon = { Icon(Icons.Default.ExitToApp, null, tint = Color(0xFFEF4444)) },
            title = "Log Out",
            subtitle = "Sign out of your account",
            onClick = onLogout,
            trailingContent = {
                Icon(Icons.Default.ChevronRight, null, tint = Color(0xFFEF4444))
            }
        )

        Spacer(Modifier.height(40.dp))

        // Footer
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Intimace v1.0.0",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "© 2025 Intimace. Made with love in the Philippines",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.6f)
            )
        }

        Spacer(Modifier.height(140.dp)) // safe space for bottom nav
    }
}

// FLAWLESS PREVIEW
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    IntimaceTheme {
        SettingsScreen(
            createAccountUiState = CreateAccountUiState(
                name = "Maria Lopez",
                email = "maria@intimace.ph"
            ),
            onEditProfile = {},
            onNotifications = {},
            onPrivacy = {},
            onPartnerLink = {},
            onHelp = {},
            onLogout = {}
        )
    }
}