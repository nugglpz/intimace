package com.intimace.ui.screens.guidesPath

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intimace.data.guides
import com.intimace.ui.theme.IntimacePurple
import com.intimace.ui.theme.IntimaceTheme

@Composable
fun GuideScreen(
    guideIndex: Int = 0,
    onBack: () -> Unit = {},
    onToggleBookmark: (Boolean) -> Unit = {}
) {
    var bookmarked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        // Header: Back + Title + Bookmark
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onBack() }
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = IntimacePurple
                    )
                }
                Text(
                    text = "Guide",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = IntimacePurple
                )
            }

            IconButton(onClick = {
                bookmarked = !bookmarked
                onToggleBookmark(bookmarked)
            }) {
                Icon(
                    imageVector = if (bookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = IntimacePurple
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Guide Image
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = guides[guideIndex].img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        // Title
        Text(
            text = stringResource(guides[guideIndex].title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = IntimacePurple
        )

        Spacer(Modifier.height(12.dp))

        // Category Pill
        Card(
            colors = CardDefaults.cardColors(containerColor = IntimacePurple.copy(alpha = 0.15f)),
            shape = RoundedCornerShape(20.dp)
        ) {

            Text(
                text = stringResource(guides[guideIndex].title).uppercase(),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = IntimacePurple,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(Modifier.height(20.dp))

        // Body Text in White Card
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                val body = stringResource(guides[guideIndex].description).split("\n").filter { it.isNotBlank() }

                body.forEach { paragraph ->
                    Text(
                        text = paragraph.trim(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF333333),
                        lineHeight = 28.sp
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }
        }

        Spacer(Modifier.height(140.dp)) // Safe space for bottom nav
    }
}

// PREVIEW — LOOKS STUNNING
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GuideScreenPreview() {
    IntimaceTheme {
        GuideScreen(
            guideIndex = 0,
            onBack = {},
            onToggleBookmark = {}
        )
    }
}