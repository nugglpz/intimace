package com.intimace.ui.screens.settingsPath

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LinkOff
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intimace.data.currentAccount
import com.intimace.ui.components.PrimaryButton
import com.intimace.ui.components.WhiteOutlinedFieldTrailing
import com.intimace.ui.theme.*
import com.intimace.uistate.SettingsUiState
import com.intimace.uistate.Sex

@Composable
fun PartnerLinkScreen(
    settingsUiState: SettingsUiState,
    onBack: () -> Unit = {},
    onToggle: (String) -> Unit = {},
    onUnlink: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IntimaceGradient)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(20.dp))

        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = IntimacePurple)
            }
            Text(
                text = if (currentAccount.hasPartner) "Partner Link Settings" else "Partner Link",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = IntimacePurple
            )
        }

        Spacer(Modifier.height(32.dp))

        if (currentAccount.hasPartner) {
            // LINKED STATE — Connected Partner
            Card(
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(28.dp)) {
                    Text("Connected Partner", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = IntimacePurple)
                    Spacer(Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(IntimacePurple.copy(0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = IntimacePurple, modifier = Modifier.size(44.dp))
                        }
                        Spacer(Modifier.width(20.dp))
                        Column {
                            Text("Alex Smith", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text("Connected since May 15, 2023", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF666666))
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    PrimaryButton(
                        text = "Unlink Partner",
                        onClick = onUnlink,
                        containerColor = Color(0xFFFFF0F0),
                        contentColor = Color(0xFFD32F2F),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // Information Sharing
            Card(
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(28.dp)) {
                    Text("Information Sharing", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = IntimacePurple)
                    Spacer(Modifier.height(8.dp))
                    Text("Choose what your partner can see", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF666666))

                    Spacer(Modifier.height(24.dp))

                    PartnerSharingToggle(
                        title = "Cycle Predictions",
                        subtitle = "Period and fertility window dates",
                        checked = settingsUiState.cyclePredictionsSharingEnabled,
                        onCheckedChange = { onToggle("cyclePredictionsSharing") }
                    )
                    PartnerSharingToggle(
                        title = "Mood & Symptoms",
                        subtitle = "Share logged mood and symptoms",
                        checked = settingsUiState.moodAndSymptomsSharingEnabled,
                        onCheckedChange = { onToggle("moodAndSymptomsSharing") }
                    )
                    PartnerSharingToggle(
                        title = "Intimate Activity",
                        subtitle = "Share intimate activity logs",
                        checked = settingsUiState.intimateActivitySharingEnabled,
                        onCheckedChange = { onToggle("intimateActivitySharing") }
                    )
                    PartnerSharingToggle(
                        title = "Notifications",
                        subtitle = "Allow partner to receive alerts",
                        checked = settingsUiState.notificationsSharingEnabled,
                        onCheckedChange = { onToggle("notificationsSharing") }
                    )
                }
            }
        }
        else {
            // NOT LINKED — Invite or Enter Code
            if (currentAccount.sex == Sex.FEMALE) {
                Card(
                    shape = RoundedCornerShape(28.dp),
                    elevation = CardDefaults.cardElevation(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(28.dp)) {
                        Text("Share Your Cycle", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = IntimacePurple)
                        Spacer(Modifier.height(12.dp))
                        Text("Give your partner your invite code so they can stay informed and supportive.", color = Color(0xFF666666))

                        Spacer(Modifier.height(28.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("ABCD1234", fontWeight = FontWeight.Bold, fontSize = 28.sp, color = IntimacePurple, modifier = Modifier.weight(1f))
                            IconButton(onClick = { /* Copy to clipboard */ }) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = IntimacePurple)
                            }
                        }

                        Spacer(Modifier.height(16.dp))
                        Text("Share this code with your partner", color = Color(0xFF888888))
                    }
                }
            } else {
                Card(
                    shape = RoundedCornerShape(28.dp),
                    elevation = CardDefaults.cardElevation(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(28.dp)) {
                        Text("Connect with Your Partner", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = IntimacePurple)
                        Spacer(Modifier.height(12.dp))
                        Text("Enter your partner’s invite code to stay connected to her cycle.", color = Color(0xFF666666))

                        Spacer(Modifier.height(24.dp))

                        var code by remember { mutableStateOf("") }
                        WhiteOutlinedFieldTrailing(
                            value = code,
                            onValueChange = { code = it.uppercase().take(8) },
                            labelText = "Enter Invite Code",
                            placeholderText = "e.g. ABCD1234"
                        )

                        Spacer(Modifier.height(24.dp))

                        PrimaryButton(
                            text = "Link with Partner",
                            onClick = { /* Link logic */ },
                            enabled = code.length == 8
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(140.dp))
    }
}

@Composable
private fun PartnerSharingToggle(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Medium, color = Color(0xFF1A1A1A))
            Spacer(Modifier.height(4.dp))
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF666666))
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = IntimacePurple,
                checkedTrackColor = IntimacePurple.copy(0.4f)
            )
        )
    }
}

// FLAWLESS PREVIEW
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PartnerLinkScreenPreview() {
    IntimaceTheme {
        PartnerLinkScreen(
            settingsUiState = SettingsUiState(
                cyclePredictionsSharingEnabled = true,
                moodAndSymptomsSharingEnabled = false,
                intimateActivitySharingEnabled = true,
                notificationsSharingEnabled = true
            ),
            onBack = {},
            onToggle = {},
            onUnlink = {}
        )
    }
}