package com.intimace.ui.screens.settingsPath

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.components.SettingsRow
import com.intimace.ui.theme.IntimaceTheme
import com.intimace.uistate.SettingsUiState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.SoftwareKeyboardController

@Composable
fun PrivacyAndSecurityScreen(
    navController: NavHostController = rememberNavController(),
    settingsUiState: SettingsUiState,
    onToggle: (String) -> Unit = {},
    onDelete: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    var selectedNav by remember { mutableIntStateOf(4) }
    val scroll = rememberScrollState()

    // 🔹 State for showing the Change PIN dialog
    var showChangePinDialog by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text(
                    "Privacy & Security",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            SettingsRow(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color(0xFF7C3AED)
                    )
                },
                title = "Security Settings",
                subtitle = "Control your privacy preferences and security settings to protect your data",
                onClick = null,
                showArrow = false
            )
            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("App Lock", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Require PIN to access the app",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                        Switch(
                            checked = settingsUiState.appLockEnabled,
                            onCheckedChange = { onToggle("appLock") }
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Hide Sensitive Content", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Blur sensitive information when app is minimized",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                        Switch(
                            checked = settingsUiState.hideSensitiveContentEnabled,
                            onCheckedChange = { onToggle("hideSensitiveContent") }
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Analytics", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Help improve the app by sharing usage data",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                        Switch(
                            checked = settingsUiState.analyticsEnabled,
                            onCheckedChange = { onToggle("analytics") }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Change PIN block
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "Change PIN",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Update your 4-digit security PIN used to access the app",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { showChangePinDialog = true }, // ✅ show dialog
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(36.dp)
                    ) {
                        Text("Change PIN")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Danger zone delete account
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "Data Management",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onDelete,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(36.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Delete My Account", color = Color(0xFFFFFFFF))
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }

    // 🔹 Dialog appears outside Scaffold for proper overlay behavior
    if (showChangePinDialog) {
        ChangePinDialog(
            currentPin = "1234", // Replace with actual stored PIN
            onConfirm = { newPin ->
                println("New PIN set: $newPin")
                showChangePinDialog = false
            },
            onDismiss = {
                showChangePinDialog = false
            }
        )
    }
}



/**
 * Change PIN dialog with three PIN inputs:
 *  - current PIN (first)
 *  - new PIN (second)
 *  - confirm new PIN (third)
 *
 * @param currentPin the user's current PIN to validate against (plain 4-digit string)
 * @param mask whether to mask digits with dots
 * @param onConfirm called with the new PIN when validation succeeds
 * @param onDismiss called when user cancels
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChangePinDialog(
    currentPin: String,
    mask: Boolean = true,
    onConfirm: (newPin: String) -> Unit,
    onDismiss: () -> Unit
) {
    // focus requesters
    val frCurrent = remember { FocusRequester() }
    val frNew = remember { FocusRequester() }
    val frConfirm = remember { FocusRequester() }

    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    // pin states
    var pinCurrent by remember { mutableStateOf("") }
    var pinNew by remember { mutableStateOf("") }
    var pinConfirm by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    // When the dialog appears, request focus on the first field and show keyboard
    LaunchedEffect(Unit) {
        // small delay can help on some devices, but usually not necessary; uncomment if needed
        // kotlinx.coroutines.delay(100)
        frCurrent.requestFocus()
        keyboard?.show()
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Surface(modifier = Modifier.padding(20.dp), color = MaterialTheme.colorScheme.surface) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Change PIN", style = MaterialTheme.typography.titleMedium)
                        IconButton(onClick = onDismiss) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Next
                    )

                    @Composable
                    fun PinEntryRow(
                        pinValue: String,
                        onPinChange: (String) -> Unit,
                        focusRequester: FocusRequester,
                        nextFocusRequester: FocusRequester? = null,
                        maskDigits: Boolean,
                        pinValueBeforeFinished: Boolean
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    focusRequester.requestFocus()
                                    keyboard?.show()
                                }
                                .padding(vertical = 8.dp)
                        ) {
                            BasicTextField(
                                value = pinValue,
                                onValueChange = { new ->
                                    val filtered = new.filter { it.isDigit() }.take(4)
                                    onPinChange(filtered)
                                    if (filtered.length == 4) {
                                        nextFocusRequester?.requestFocus()
                                    }
                                },
                                keyboardOptions = keyboardOptions,
                                textStyle = TextStyle(fontSize = 16.sp), // keep a small font so cursor works
                                singleLine = true,
                                modifier = Modifier
                                    .focusRequester(focusRequester)
                                    .focusable(true)
                                    .alpha(0f)
                                    .fillMaxWidth()
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                for (i in 0 until 4) {
                                    val ch = pinValue.getOrNull(i)
                                    DigitBox(char = ch, showMask = maskDigits, isActive = pinValue.length == i && pinValueBeforeFinished)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Current PIN.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    PinEntryRow(
                        pinValue = pinCurrent,
                        onPinChange = {
                            pinCurrent = it
                            if (pinCurrent.length == 4 ) {
                                errorMessage = if (pinCurrent != currentPin)
                                    "Current PIN incorrect"
                                else
                                    null
                            }
                        },
                        focusRequester = frCurrent,
                        nextFocusRequester = frNew,
                        maskDigits = mask,
                        pinValueBeforeFinished = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "New PIN",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    PinEntryRow(
                        pinValue = pinNew,
                        onPinChange = { pinNew = it },
                        focusRequester = frNew,
                        nextFocusRequester = frConfirm,
                        maskDigits = mask,
                        pinValueBeforeFinished = pinCurrent == currentPin

                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Re-enter new PIN",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    PinEntryRow(
                        pinValue = pinConfirm,
                        onPinChange = {
                            pinConfirm = it
                            if (pinConfirm.length == 4 ) {
                                errorMessage = if (pinConfirm != pinNew)
                                    "Confirmation PIN incorrect"
                                else
                                    null

                            }
                        },
                        focusRequester = frConfirm,
                        nextFocusRequester = null,
                        maskDigits = mask,
                        pinValueBeforeFinished = pinCurrent == currentPin && pinNew.length == 4
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = {
                            pinCurrent = ""
                            pinNew = ""
                            pinConfirm = ""
                            errorMessage = null
                            frCurrent.requestFocus()
                            keyboard?.show()
                        }) {
                            Text("Clear")
                        }

                        Button(onClick = {
                            when {
                                pinCurrent.length != 4 -> {
                                    errorMessage = "Enter current 4-digit PIN"
                                    frCurrent.requestFocus()
                                }
                                pinCurrent != currentPin -> {
                                    errorMessage = "Current PIN incorrect"
                                    frCurrent.requestFocus()
                                }
                                pinNew.length != 4 -> {
                                    errorMessage = "Enter new 4-digit PIN"
                                    frNew.requestFocus()
                                }
                                pinConfirm.length != 4 -> {
                                    errorMessage = "Confirm your new PIN"
                                    frConfirm.requestFocus()
                                }
                                pinNew != pinConfirm -> {
                                    errorMessage = "New PIN and confirmation do not match"
                                    pinNew = ""
                                    pinConfirm = ""
                                    frNew.requestFocus()
                                }
                                else -> {
                                    keyboard?.hide()
                                    focusManager.clearFocus(true)
                                    errorMessage = null
                                    onConfirm(pinNew)
                                }
                            }
                        }) {
                            Text("Confirm")
                        }
                    }

                    if (!errorMessage.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun DigitBox(char: Char?, showMask: Boolean, isActive: Boolean) {
    val borderColor = if (isActive) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.4f)
    val fillColor = Color.Transparent

    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(fillColor)
            .border(width = 1.5.dp, color = borderColor, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (char != null) {
            if (showMask) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black)
                )
            } else {
                Text(text = char.toString(), style = TextStyle(fontSize = 20.sp))
            }
        }
    }
}

// single reusable composable row for PIN entry
@Composable
fun PinEntryRow(
    pinValue: String,
    onPinChange: (String) -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester? = null,
    prevFocusRequester: FocusRequester? = null,
    maskDigits: Boolean,
    keyboard: SoftwareKeyboardController? = null
) {
    Box(
        modifier = Modifier
            .clickable {
                focusRequester.requestFocus()
                keyboard?.show()
            }
            .padding(vertical = 8.dp)
    ) {
        BasicTextField(
            value = pinValue,
            onValueChange = { new ->
                // allow only digits and max 4
                val filtered = new.filter { it.isDigit() }.take(4)
                onPinChange(filtered)

                // auto-advance when length reaches 4
                if (filtered.length == 4) {
                    nextFocusRequester?.requestFocus()
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Next
            ),
            textStyle = TextStyle(fontSize = 0.sp), // hide visible text; we render boxes
            singleLine = true,
            modifier = Modifier
                .focusRequester(focusRequester)
                .focusable(true)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            for (i in 0 until 4) {
                val ch = pinValue.getOrNull(i)
                DigitBox(char = ch, showMask = maskDigits, isActive = pinValue.length == i)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChangePinDialog() {
    MaterialTheme {
        ChangePinDialog(
            currentPin = "1234",
            mask = true,
            onConfirm = { newPin -> println("New PIN: $newPin") },
            onDismiss = {}
        )
    }
}
