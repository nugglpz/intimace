package com.intimace.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import java.time.LocalTime


@Composable
fun AuthHeader(title: String, subtitle: String? = null, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        if (!subtitle.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    }
}

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    // rely on Material3 default styling (no explicit colors)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { if (placeholder.isNotEmpty()) Text(placeholder) },
        label = { if (label.isNotEmpty()) Text(label) },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        leadingIcon = leadingIcon,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    label: String = "Password",
    placeholder: String = "",
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(label) },
        placeholder = { if (placeholder.isNotEmpty()) Text(placeholder) },
        singleLine = true,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { visible = !visible }) {
                Icon(
                    imageVector = if (visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (visible) "Hide password" else "Show password"
                )
            }
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(50),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text = text, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun EmailSentScreen(onDone: () -> Unit = {}) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "Email sent", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(84.dp))
            Spacer(modifier = Modifier.height(18.dp))
            Text("Check your email", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("We sent a link to reset your password. Follow the instructions in that email to continue.", style = MaterialTheme.typography.bodyMedium, color = Color.Gray, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(28.dp))
            PrimaryButton(text = "Back to sign in", onClick = onDone, modifier = Modifier.fillMaxWidth(0.85f))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(
    label: String,
    selectedDateMillis: Long?,
    onDateSelectedMillis: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    // Formatter using legacy APIs (works on API 21+)
    val dateFormatter = remember {
        java.text.SimpleDateFormat("MMM d, yyyy", java.util.Locale.getDefault())
    }

    var showDialog by remember { mutableStateOf(false) }

    // Use millis-based API for DatePicker state
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDateMillis)

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        onDateSelectedMillis(millis)
                    }
                    showDialog = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    OutlinedTextField(
        value = selectedDateMillis?.let { dateFormatter.format(java.util.Date(it)) } ?: "",
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        placeholder = { Text("mm/dd/yyyy") },
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Filled.CalendarToday, contentDescription = "Select date")
            }
        },
        modifier = modifier
            .clickable { showDialog = true }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerTextField(
    label: String,
    selectedTime: Long?, // Time in milliseconds since midnight
    onTimeSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
    is24Hour: Boolean = false
) {
    val timeFormatter = remember {
        java.text.SimpleDateFormat(
            if (is24Hour) "HH:mm" else "hh:mm a",
            java.util.Locale.getDefault()
        )
    }

    var showDialog by remember { mutableStateOf(false) }

    // Convert selectedTime (Long) → hour/minute
    val (initialHour, initialMinute) = remember(selectedTime) {
        if (selectedTime != null) {
            val totalMinutes = (selectedTime / 60000L).toInt()
            totalMinutes / 60 to totalMinutes % 60
        } else {
            val now = java.time.LocalTime.now()
            now.hour to now.minute
        }
    }

    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = is24Hour
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Select Time") },
            text = { TimePicker(state = timePickerState) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    val totalMillis =
                        (timePickerState.hour * 60L + timePickerState.minute) * 60_000L
                    onTimeSelected(totalMillis)
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Convert Long → formatted string for display
    val formattedTime = remember(selectedTime) {
        selectedTime?.let {
            val hours = (it / 3600000L).toInt()
            val minutes = ((it % 3600000L) / 60000L).toInt()
            val cal = java.util.Calendar.getInstance().apply {
                set(java.util.Calendar.HOUR_OF_DAY, hours)
                set(java.util.Calendar.MINUTE, minutes)
            }
            timeFormatter.format(cal.time)
        } ?: ""
    }

    OutlinedTextField(
        value = formattedTime,
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        placeholder = { Text(if (is24Hour) "HH:mm" else "hh:mm AM/PM") },
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Filled.AccessTime, contentDescription = "Select time")
            }
        },
        modifier = modifier.clickable { showDialog = true }
    )
}


@Composable
fun QuickActionItem(label: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .height(96.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
            Icon(imageVector = icon, contentDescription = label, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun AppBottomNav(
    navController: NavHostController,
    cartItemCount: Int = 0,
    modifier: Modifier = Modifier
) {
    // derive selected index from current destination if you like, else the caller can pass state
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        tonalElevation = 6.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        val selectedColor = MaterialTheme.colorScheme.primary
        val unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant

        fun navigateTo(route: String) {
            if (currentRoute != route) {
                navController.navigate(route) {
                    // avoid building large backstack when switching tabs
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }

        // Home
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = { navigateTo("home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                unselectedIconColor = unselectedColor
            )
        )

        // Calendar
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Calendar") },
            label = { Text("Calendar") },
            selected = currentRoute == "calendar",
            onClick = { navigateTo("calendar") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                unselectedIconColor = unselectedColor
            )
        )

        // Guides
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Book, contentDescription = "Guides") },
            label = { Text("Guides") },
            selected = currentRoute == "guides",
            onClick = { navigateTo("guides") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                unselectedIconColor = unselectedColor
            )
        )

        // Shop (with optional badge)
        NavigationBarItem(
            icon = {
                if (cartItemCount > 0) {
                    BadgeBox(count = cartItemCount) {
                        Icon(imageVector = Icons.Default.LocalMall, contentDescription = "Shop")
                    }
                } else {
                    Icon(imageVector = Icons.Default.LocalMall, contentDescription = "Shop")
                }
            },
            label = { Text("Shop") },
            selected = currentRoute == "shop",
            onClick = { navigateTo("shop") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                unselectedIconColor = unselectedColor
            )
        )

        // Profile -> user requested that the profile button link to SettingsScreen
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = currentRoute == "settings" || currentRoute == "profile",
            onClick = { navigateTo("settings") }, // links to SettingsScreen per your request
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                unselectedIconColor = unselectedColor
            )
        )
    }
}

@Composable
fun SymptomChip(
    selectedSymptoms: List<String>,
    label: String,
    onToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val selected = selectedSymptoms.contains(label)
    OutlinedButton(
        onClick = { onToggle(label) },
        modifier = modifier,
        // style it differently when selected
        border = BorderStroke(1.dp, if (selected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.5f)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent,
            contentColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    ) {
        Text(label)
    }
}


@Composable
private fun BadgeBox(count: Int, content: @Composable () -> Unit) {
    Box {
        content()
        // small top-right badge
        if (count > 0) {
            Badge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 6.dp, y = (-2).dp),
                containerColor = androidx.compose.ui.graphics.Color(0xFFEF4444),
                contentColor = androidx.compose.ui.graphics.Color.White
            ) {
                Text(text = if (count > 9) "9+" else count.toString(), style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun ProductCard(
    @DrawableRes img: Int,
    @StringRes type: Int,
    @StringRes name: Int,
    @StringRes location: Int,
    @StringRes price: Int,
    onClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            // image placeholde
            Box(
                modifier = Modifier
                    .size(74.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF2F2F4))
            ) {
                Image(
                    painter = painterResource(img),
                    modifier = Modifier.size(74.dp),
                    contentDescription = "Product image",
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(2f)) {
                Text(stringResource(type), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(stringResource(name), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(stringResource(location), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(price), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = onAddToCart,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.width(80.dp).height(45.dp)
            ) {
                Text(text = "Add to Cart", textAlign = TextAlign.Center, fontSize = 10.sp, lineHeight=12.sp)
            }
        }
    }
}

@Composable
fun WhiteOutlinedFieldTrailing(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    placeholderText: String = "",
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    trailingIcon: (@Composable (() -> Unit))? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { if (placeholderText.isNotEmpty()) Text(placeholderText) },
            label = { Text(labelText) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            trailingIcon = trailingIcon
        )
    }
}

@Composable
fun OrderCompleteOverlay(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        OrderCompleteDialogContent(onDismiss = onDismiss)
    }
}

@Composable
fun OrderCompleteDialogContent(onDismiss: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDFF6E9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Complete", tint = Color(0xFF2E7D32))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Order Complete", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Your order has been successfully placed.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onDismiss) { Text("Done") }
        }
    }
}

@Composable
fun SettingsRow(
    icon: @Composable (() -> Unit)? = null,
    title: String,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    showArrow: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = onClick != null) { onClick?.invoke() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF3E9FF)),
                    contentAlignment = Alignment.Center
                ) {
                    icon()
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                if (!subtitle.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }
            if (trailingContent != null) {
                trailingContent()
            } else {
                if (showArrow) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Next",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationControls(
    step: Int,
    totalSteps: Int,
    onContinue: () -> Unit,
    onSkip: (() -> Unit)? = null,
    isFinalStep: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Top row: pager dots (left) and "X of Y" (right)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Pager dots (left)
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(totalSteps) { index ->
                    val active = index < step
                    Box(
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .height(6.dp)
                            .width(if (active) 20.dp else 8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (active) MaterialTheme.colorScheme.primary else Color.LightGray)
                    )
                }
            }

            // Step label (right)
            Text(
                text = "$step of $totalSteps",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Bottom row: Skip (left) and Continue pinned to end (right)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: skip (or a placeholder to keep spacing consistent)
            if (onSkip != null) {
                TextButton(onClick = onSkip) {
                    Text("Skip for now", color = MaterialTheme.colorScheme.primary)
                }
            } else {
                // keep the left side empty to allow Continue to be pinned right
                Spacer(modifier = Modifier.width(8.dp))
            }

            // Right: Continue / Complete button pinned to the end
            Button(
                onClick = onContinue,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .defaultMinSize(minWidth = 160.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(if (isFinalStep) "Complete Setup" else "Continue", color = MaterialTheme.colorScheme.onPrimary)
                if (!isFinalStep) {
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = if (isFinalStep) "Complete" else "Continue"
                    )
                }
            }
        }
    }
}