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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intimace.data.guides
import com.intimace.model.Guide
import com.intimace.ui.theme.IntimaceGradient
import com.intimace.ui.theme.IntimacePurple
import com.intimace.ui.theme.IntimaceTheme

@Composable
fun GuidesScreen(
    guidesList: List<Guide>,
    onOpenGuide: (Int) -> Unit = {},
    onBack: () -> Unit = {}     // kept for NavHost, but not used here
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IntimaceGradient)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(24.dp))

        // Header — "Guides" in the UPPER LEFT (just like your original design)
        Text(
            text = "Guides",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = IntimacePurple
        )

        Spacer(Modifier.height(32.dp))

        // Guides List — premium cards as before
        guidesList.forEachIndexed { index, guide ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clickable { onOpenGuide(index) },
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = guide.img),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(100.dp)
                        )
                    }

                    Spacer(Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(guide.title),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = IntimacePurple
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = stringResource(guide.description).take(120) + "...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF666666),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = IntimacePurple.copy(alpha = 0.6f),
                        modifier = Modifier
                            .size(28.dp)
                            .rotate(180f)
                    )
                }
            }
        }

        Spacer(Modifier.height(140.dp)) // Safe space for bottom nav
    }
}


// BEAUTIFUL PREVIEW
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GuidesScreenPreview() {
    IntimaceTheme {
        GuidesScreen(
            guidesList = guides,
            onOpenGuide = {},
            onBack = {}
        )
    }
}