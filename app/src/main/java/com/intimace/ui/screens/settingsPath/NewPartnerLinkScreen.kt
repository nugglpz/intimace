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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.intimace.ui.components.WhiteOutlinedFieldTrailing

@Composable
fun NewPartnerLinkScreen(
    navController: NavHostController = rememberNavController(),
    onBack: () -> Unit = {}
) {
    var selectedNav by remember { mutableIntStateOf(4) }
    val scroll = rememberScrollState()

    var inviteCode by remember { mutableStateOf("ABCD1234") }
    var inputCode by remember { mutableStateOf("") }

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
                Text("Partner Link", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(12.dp))

            Text("Share your cycle information with a trusted partner to help them stay informed about your reproductive health.", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Your Invite Code", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(0.dp)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(inviteCode, modifier = Modifier.weight(1f), fontWeight = FontWeight.SemiBold)
                            IconButton(onClick = { /* copy to clipboard */ }) {
                                Icon(imageVector = Icons.Default.CopyAll, contentDescription = "Copy")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Share this code with your partner to link your accounts", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("QR Code", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(Color(0xFFEFEFF0)),
                        contentAlignment = Alignment.Center
                    ) {
                        // placeholder for QR
                        Text("QR CODE", color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Your partner can scan this QR code to connect", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Enter Partner's Code", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    WhiteOutlinedFieldTrailing(value = inputCode, onValueChange = { inputCode = it }, labelText = "Enter partner's code", placeholderText = "Enter partner's code")
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { /* link action */ }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(36.dp)) {
                        Text("Link Partner")
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}