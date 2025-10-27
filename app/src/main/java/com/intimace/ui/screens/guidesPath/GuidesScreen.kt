package com.intimace.ui.screens.guidesPath

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.data.guides
import com.intimace.model.Guide
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.theme.IntimaceTheme

@Composable
fun GuidesScreen(
    navController: NavHostController = rememberNavController(),
    guidesList: List<Guide>,
    onOpenGuide: (Int) -> Unit = {},
    onBack: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text("Guides", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            guidesList.forEachIndexed { id, guide ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onOpenGuide(id) },
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = { onOpenGuide(id) }
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        // thumbnail placeholder
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                        ) {
                            Image(
                                painter = painterResource(guides[id].img),
                                modifier = Modifier.size(90.dp),
                                contentDescription = "Product image",
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier
                            .fillMaxWidth()
                        ) {
                            Text(text = stringResource(guides[id].title), style = MaterialTheme.typography.titleMedium, color = Color.Black, fontWeight = FontWeight.SemiBold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = stringResource(guides[id].description), style = MaterialTheme.typography.bodySmall, color = Color.Black, maxLines = 3, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp)) // safe spacing for bottom nav
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuidesScreenPreview() {
    IntimaceTheme {
        GuidesScreen(guidesList = guides)
    }
}