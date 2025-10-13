package com.intimace.ui.screens.guidesPath

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.intimace.ui.components.AppBottomNav

@Composable
fun GuideScreen(
    navController: NavHostController,
    onBack: () -> Unit = {},
    onToggleBookmark: (Boolean) -> Unit = {}
) {
    val guideId = 1
    var selectedNav by remember { mutableIntStateOf(2) }
    val scrollState = rememberScrollState()
    var bookmarked by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // header row with back + title + bookmark
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onBack() }) {
                    IconButton(onClick = { onBack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guide Detail", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                }

                IconButton(onClick = {
                    bookmarked = !bookmarked
                    onToggleBookmark(bookmarked)
                }) {
                    Icon(imageVector = if (bookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder, contentDescription = "Bookmark")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // large image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFEEEEEE))
            )

            Spacer(modifier = Modifier.height(12.dp))

            // content card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // small category pill
                    Text("Education", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Understanding Your Cycle", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    // multi-paragraph body (sample copy)
                    val body = """
                        Understanding your menstrual cycle is key to understanding your overall reproductive health. 
                        
                        A typical menstrual cycle lasts about 28 days, but can range from 21 to 35 days. The cycle begins on the first day of your period. During this time, the lining of your uterus sheds, resulting in menstrual bleeding. This phase typically lasts 3–7 days.
                        
                        Following your period, your body prepares for ovulation. Estrogen levels rise, causing the lining of your uterus to thicken. Around day 14 of a 28-day cycle, ovulation occurs when an egg is released from one of your ovaries. If the egg isn't fertilized, hormone levels drop, triggering the shedding of the uterine lining, and your cycle begins again.
                        
                        Tracking your cycle can help you predict when you'll get your period, identify patterns in symptoms, and better understand your fertility window.
                    """.trimIndent()

                    body.split("\n\n").forEach { para ->
                        Text(para, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}