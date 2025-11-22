package com.intimace.ui.screens.homePath

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.data.currentAccount
import com.intimace.ui.components.QuickActionItem
import com.intimace.ui.theme.IntimaceGradient     // ← shared from IntimaceColors.kt
import com.intimace.ui.theme.IntimacePurple       // ← shared from IntimaceColors.kt
import com.intimace.ui.theme.IntimaceTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    onLogSymptoms: () -> Unit = {},
    onCalendar: () -> Unit = {},
    onGuides: () -> Unit = {},
    onShop: () -> Unit = {},
    onProfile: () -> Unit = {}
) {
    val name = currentAccount.name.ifEmpty { "Lovely Human" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IntimaceGradient)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(12.dp))

        // Greeting
        Text("Hi, $name!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = IntimacePurple)
        Text(
            LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH)),
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF888888)
        )
        Spacer(Modifier.height(20.dp))

        // Just the Tip – WHITE CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("Just the Tip", fontWeight = FontWeight.SemiBold, color = IntimacePurple)
                Spacer(Modifier.height(8.dp))
                Text("Remember to take your birth control pill today at 9:00 PM", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(Modifier.height(20.dp))

        // Cycle Card – WHITE + elevated
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(Modifier.padding(22.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Cycle Day 14", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = IntimacePurple)

                    Button(
                        onClick = onCalendar,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = IntimacePurple),
                        shape = RoundedCornerShape(30.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                        border = BorderStroke(1.5.dp, IntimacePurple.copy(alpha = 0.3f)),
                        modifier = Modifier.height(52.dp)
                    ) {
                        Text("Calendar", fontWeight = FontWeight.Medium)
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(Modifier.height(16.dp))
                Text("Fertility today", style = MaterialTheme.typography.bodyLarge)

                Spacer(Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth()) {
                    listOf(Color(0xFFFFECEC), Color(0xFFDCEBFF), Color(0xFFF3E8FF), Color(0xFFFFF2D6)).forEach { color ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .height(14.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(color)
                        )
                    }
                }

                val nextPeriod = currentAccount.firstDayOfLastPeriod?.plusDays(30)
                    ?.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)) ?: "June 5, 2025"

                Spacer(Modifier.height(12.dp))
                Text("Next period expected on $nextPeriod", fontWeight = FontWeight.Bold, color = IntimacePurple)
            }
        }

        Spacer(Modifier.height(24.dp))

        Text("Quick Actions", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = IntimacePurple)
        Spacer(Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionItem(
                label = "Log Symptoms", icon = Icons.Default.FavoriteBorder, iconTint = IntimacePurple,
                modifier = Modifier.weight(1f), onClick = onLogSymptoms,
                containerColor = Color.White, contentColor = Color.Black
            )
            QuickActionItem(
                label = "Guides", icon = Icons.Default.Book, iconTint = IntimacePurple,
                modifier = Modifier.weight(1f), onClick = onGuides,
                containerColor = Color.White, contentColor = Color.Black
            )
        }

        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionItem(
                label = "Shop", icon = Icons.Default.LocalMall, iconTint = IntimacePurple,
                modifier = Modifier.weight(1f), onClick = onShop,
                containerColor = Color.White, contentColor = Color.Black
            )
            QuickActionItem(
                label = "Partner", icon = Icons.Default.AccountCircle, iconTint = IntimacePurple,
                modifier = Modifier.weight(1f), onClick = onProfile,
                containerColor = Color.White, contentColor = Color.Black
            )
        }

        Spacer(Modifier.height(24.dp))

        // Recent Insights – WHITE CARD
        Text("Recent Insights", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = IntimacePurple)
        Spacer(Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(Modifier.padding(22.dp)) {
                Text("Your cycle patterns", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(12.dp))
                Text(
                    "Your last 3 cycles were consistent at 28–29 days. This regularity helps predict future cycles more accurately.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF666666)
                )
                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.width(5.dp).height(36.dp).background(IntimacePurple.copy(alpha = 0.7f), RoundedCornerShape(3.dp)))
                    Spacer(Modifier.width(12.dp))
                    Text("Average cycle length: 28.3 days", fontWeight = FontWeight.SemiBold, color = IntimacePurple)
                }
            }
        }

        Spacer(Modifier.height(120.dp))
    }
}