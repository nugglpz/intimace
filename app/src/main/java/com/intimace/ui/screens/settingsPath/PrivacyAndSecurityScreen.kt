package com.intimace.ui.screens.settingsPath

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.intimace.ui.components.PrimaryButton
import com.intimace.ui.theme.*
import com.intimace.uistate.SettingsUiState

@Composable
fun PrivacyAndSecurityScreen(
    settingsUiState: SettingsUiState,
    onToggle: (String) -> Unit = {},
    onDelete: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    var showChangePinDialog by remember { mutableStateOf(false) }

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
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = IntimacePurple
                )
            }
            Text(
                text = "Privacy & Security",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = IntimacePurple
            )
        }

        Spacer(Modifier.height(32.dp))

        // Security Settings Card
        Card(
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Security Settings",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = IntimacePurple
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Control your privacy preferences and security settings to protect your data",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF666666)
                )

                Spacer(Modifier.height(24.dp))

                PrivacyToggleRow(
                    title = "App Lock",
                    subtitle = "Require PIN to access the app",
                    checked = settingsUiState.appLockEnabled,
                    onCheckedChange = { onToggle("appLock") }
                )
                PrivacyToggleRow(
                    title = "Hide Sensitive Content",
                    subtitle = "Blur sensitive information when app is minimized",
                    checked = settingsUiState.hideSensitiveContentEnabled,
                    onCheckedChange = { onToggle("hideSensitiveContent") }
                )
                PrivacyToggleRow(
                    title = "Analytics",
                    subtitle = "Help improve the app by sharing usage data",
                    checked = settingsUiState.analyticsEnabled,
                    onCheckedChange = { onToggle("analytics") }
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // Change PIN Card
        Card(
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Change PIN",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = IntimacePurple
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Update your 4-digit security PIN used to access the app",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF666666)
                )
                Spacer(Modifier.height(20.dp))

                PrimaryButton(
                    text = "Change PIN",
                    onClick = { showChangePinDialog = true },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // Danger Zone
        Card(
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0F0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Data Management",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(36.dp)
                ) {
                    Text("Delete My Account", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(Modifier.height(140.dp))
    }

    // PIN Dialog
    if (showChangePinDialog) {
        ChangePinDialog(
            currentPin = "1234", // Replace with real PIN from ViewModel later
            onConfirm = { newPin ->
                // TODO: Save new PIN
                showChangePinDialog = false
            },
            onDismiss = { showChangePinDialog = false }
        )
    }
}

@Composable
private fun PrivacyToggleRow(
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
            Text(text = title, fontWeight = FontWeight.Medium, color = Color(0xFF1A1A1A))
            Spacer(Modifier.height(4.dp))
            Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF666666))
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = IntimacePurple,
                checkedTrackColor = IntimacePurple.copy(alpha = 0.4f)
            )
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChangePinDialog(
    currentPin: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val frCurrent = remember { FocusRequester() }
    val frNew = remember { FocusRequester() }
    val frConfirm = remember { FocusRequester() }

    var pinCurrent by remember { mutableStateOf("") }
    var pinNew by remember { mutableStateOf("") }
    var pinConfirm by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        frCurrent.requestFocus()
        keyboard?.show()
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change PIN",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = IntimacePurple
                )
                Spacer(Modifier.height(24.dp))

                PinField(label = "Current PIN", value = pinCurrent, onValueChange = { pinCurrent = it }, focusRequester = frCurrent, next = frNew)
                Spacer(Modifier.height(16.dp))
                PinField(label = "New PIN", value = pinNew, onValueChange = { pinNew = it }, focusRequester = frNew, next = frConfirm)
                Spacer(Modifier.height(16.dp))
                PinField(label = "Confirm New PIN", value = pinConfirm, onValueChange = { pinConfirm = it }, focusRequester = frConfirm)

                if (error != null) {
                    Spacer(Modifier.height(12.dp))
                    Text(error!!, color = Color(0xFFD32F2F), fontWeight = FontWeight.Medium)
                }

                Spacer(Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = {
                        pinCurrent = ""; pinNew = ""; pinConfirm = ""; error = null
                        frCurrent.requestFocus()
                    }) { Text("Clear") }

                    PrimaryButton(
                        text = "Confirm",
                        onClick = {
                            when {
                                pinCurrent != currentPin -> error = "Current PIN is incorrect"
                                pinNew.length != 4 -> error = "New PIN must be 4 digits"
                                pinNew != pinConfirm -> error = "PINs do not match"
                                else -> { onConfirm(pinNew); onDismiss() }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun PinField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    next: FocusRequester? = null
) {
    val keyboard = LocalSoftwareKeyboardController.current

    Column {
        Text(label, style = MaterialTheme.typography.labelMedium, color = Color(0xFF666666))
        Spacer(Modifier.height(8.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable { focusRequester.requestFocus(); keyboard?.show() }
        ) {
            BasicTextField(
                value = value,
                onValueChange = {
                    val filtered = it.filter { char -> char.isDigit() }.take(4)
                    onValueChange(filtered)
                    if (filtered.length == 4) next?.requestFocus()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .alpha(0f)
                    .fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                repeat(4) { index ->
                    val hasDigit = value.getOrNull(index) != null
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (hasDigit) IntimacePurple else Color.Transparent)
                            .border(2.dp, if (value.length == index) IntimacePurple else Color(0xFFE0E0E0), RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (hasDigit) {
                            Box(Modifier.size(16.dp).background(Color.White, CircleShape))
                        }
                    }
                }
            }
        }
    }
}

// GORGEOUS PREVIEW
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrivacyAndSecurityScreenPreview() {
    IntimaceTheme {
        PrivacyAndSecurityScreen(
            settingsUiState = SettingsUiState(
                appLockEnabled = true,
                hideSensitiveContentEnabled = false,
                analyticsEnabled = true
            ),
            onToggle = {},
            onDelete = {},
            onBack = {}
        )
    }
}