package com.intimace.ui.screens.guidesPath

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.intimace.data.guides
import com.intimace.ui.components.AppBottomNav

@Composable
fun GuideScreen(
    navController: NavHostController,
    guideIndex: Int = 0,
    onBack: () -> Unit = {},
    onToggleBookmark: (Boolean) -> Unit = {}
) {
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
                    Text("Guide Detail", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold)
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
            ) {
                Image(
                    painter = painterResource(guides[guideIndex].img),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Product image",
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // content card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // small category pill
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(stringResource(guides[guideIndex].title), style = MaterialTheme.typography.titleLarge, color = Color.Green, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    // multi-paragraph body (sample copy)
                    val body = stringResource(guides[guideIndex].description).trimIndent()

                    body.split("\n").forEach { para ->
                        Text(para, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}