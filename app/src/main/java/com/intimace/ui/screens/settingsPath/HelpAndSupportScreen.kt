package com.intimace.ui.screens.settingsPath

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.components.SettingsRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpAndSupportScreen(
    navController: NavHostController = rememberNavController(),
    onBack: () -> Unit = {}
) {
    var selectedNav by remember { mutableIntStateOf(4) }
    val scroll = rememberScrollState()
    var expandedQuestionIndex by remember { mutableIntStateOf(-1) } // No question expanded initially

    val faqs = listOf(
        "How do I track my period?" to
                "You can track your period by logging your start and end dates in the Calendar or Log Symptoms screen. The app will automatically update your cycle insights based on your entries.",

        "How accurate are the predictions?" to
                "Predictions improve over time as you consistently log your periods and symptoms. They’re based on averages from your past cycles, so regular tracking leads to better accuracy.",

        "Can I share my data with my doctor?" to
                "For now, not yet. However, that's currently being planned as a feature in a future update of Intimace.",

        "How do I connect with my partner?" to
                "Go to the Partner Link section under Settings. You can send an invite link or enter your partner’s code to sync select health insights privately.",

        "How do I change my privacy settings?" to
                "You can review or modify your privacy preferences in Settings → Privacy and Security. From there, you can control data sharing and app permissions."
    )

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Text(
                    "Help & Support",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            SettingsRow(
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Help,
                        contentDescription = null,
                        tint = Color(0xFF7C3AED)
                    )
                },
                title = "Support Options",
                subtitle = "Find answers to common questions about the app features and functionality",
                onClick = null,
                showArrow = false
            )

            Spacer(modifier = Modifier.height(12.dp))

            // FAQ Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column {
                    faqs.forEachIndexed { index, (question, answer) ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expandedQuestionIndex =
                                        if (expandedQuestionIndex == index) -1 else index
                                }
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = question,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = if (expandedQuestionIndex == index)
                                        Icons.Default.ExpandLess
                                    else
                                        Icons.Default.ExpandMore,
                                    contentDescription = "Toggle answer",
                                    tint = Color.Gray
                                )
                            }

                            // Expandable answer section
                            AnimatedVisibility(visible = expandedQuestionIndex == index) {
                                Text(
                                    text = answer,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.DarkGray,
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                )
                            }
                        }

                        if (index != faqs.lastIndex) {
                            HorizontalDivider(
                                color = DividerDefaults.color,
                                thickness = DividerDefaults.Thickness
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

