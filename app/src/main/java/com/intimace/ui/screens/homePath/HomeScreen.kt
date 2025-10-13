package com.intimace.ui.screens.homePath

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalMall
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
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.components.QuickActionItem

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    onLogSymptoms: () -> Unit = {},
    onCalendar: () -> Unit = {},
    onGuides: () -> Unit = {},
    onShop: () -> Unit = {},
    onProfile: () -> Unit = {}
) {
    var selectedNav by remember { mutableIntStateOf(0) }

    Scaffold() { innerPadding ->
        // body content scrolls independently; bottomBar remains fixed
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Greeting
            Text("Hi, Cassie", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Friday, September 19", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))

            // Tip card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF6ECFF))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Just the Tip", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Remember to take your birth control pill today at 9:00 PM", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Cycle card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Cycle Day 14", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Calendar", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Calendar")
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Fertility today", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Spacer(modifier = Modifier.height(6.dp))
                    // Simple progress bar showing phases (visual only)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        // These boxes represent the 4 phases - colored rectangles with rounded ends
                        val phaseColors = listOf(Color(0xFFFFE6E6), Color(0xFFDCEBFF), Color(0xFFF2E8FF), Color(0xFFFFF2D6))
                        val labels = listOf("Menstrual", "Follicular", "Ovulation", "Luteal")
                        phaseColors.forEachIndexed { i, c ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f).padding(4.dp)) {
                                Box(
                                    modifier = Modifier
                                        .height(12.dp)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(c)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Next period expected on October 3", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Actions grid (2 columns)
            Text("Quick Actions", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    QuickActionItem(label = "Log Symptoms", icon = Icons.Default.Favorite, modifier = Modifier.weight(1f)) { onLogSymptoms() }
                    QuickActionItem(label = "Guides", icon = Icons.Default.Book, modifier = Modifier.weight(1f)) { onGuides() }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    QuickActionItem(label = "Shop", icon = Icons.Default.LocalMall, modifier = Modifier.weight(1f)) { onShop() }
                    QuickActionItem(label = "Partner", icon = Icons.Default.AccountCircle, modifier = Modifier.weight(1f)) { onProfile() }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Recent insights card
            Text("Recent Insights", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Your cycle patterns", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Your last 3 cycles were consistent at 28–29 days. This regularity helps predict future cycles more accurately.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(120.dp)) // spacing so bottom nav doesn't overlap content
        }
    }
}