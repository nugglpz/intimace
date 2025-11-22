package com.intimace.ui.screens.calendarPath

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.ui.theme.IntimaceTheme
import com.intimace.viewmodel.CalendarViewModel
import java.text.SimpleDateFormat
import java.util.*

// INTIMACE BRANDING ONLY
val intimacePurple = Color(0xFF640068)
val intimaceGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFFF8F4FF),
        Color(0xFFE8DAFF),
        Color(0xFFD8C9FF)
    )
)

@Composable
fun CalendarScreen(
    navController: NavHostController,
    month: Int,
    year: Int = 2025,
    onPrevious: () -> Unit = {},
    onNext: () -> Unit = {},
    calendarViewModel: CalendarViewModel? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(intimaceGradient)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(12.dp))

        Text(
            text = "Calendar",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = intimacePurple
        )
        Spacer(Modifier.height(20.dp))

        // Calendar Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(Modifier.padding(20.dp)) {
                // Header
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onPrevious) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Previous", tint = intimacePurple)
                    }
                    Text(
                        text = "${monthNameSafe(month)} $year",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = intimacePurple
                    )
                    IconButton(onClick = onNext) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, "Next", tint = intimacePurple)
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Weekdays
                Row(Modifier.fillMaxWidth()) {
                    listOf("S", "M", "T", "W", "T", "F", "S").forEach {
                        Text(it, Modifier.weight(1f), textAlign = TextAlign.Center, color = Color(0xFF888888))
                    }
                }

                Spacer(Modifier.height(12.dp))

                // THIS IS THE ONLY CHANGE → Box with fixed height for the grid
                Box(modifier = Modifier.height(380.dp)) {   // Adjust this value if you want bigger/smaller cells
                    CalendarGrid(year = year, month = month)
                }
            }
        }

        // Rest of your beautiful UI (Legend + Forecast)
        Spacer(Modifier.height(24.dp))
        Text("Legend", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = intimacePurple)
        Spacer(Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(Modifier.padding(18.dp)) {
                listOf(
                    "Menstruation Phase" to Color(0xFFFFECEC),
                    "Follicular Phase" to Color(0xFFDCEBFF),
                    "Ovulation Phase" to Color(0xFFF3E8FF),
                    "Luteal Phase" to Color(0xFFFFF2D6)
                ).forEach { (label, color) ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 6.dp)) {
                        Box(Modifier.size(20.dp).clip(RoundedCornerShape(8.dp)).background(color))
                        Spacer(Modifier.width(12.dp))
                        Text(label, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("Fertility Forecast", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = intimacePurple)
                Spacer(Modifier.height(12.dp))
                ForecastRow("High chance of pregnancy", Color(0xFFFF6B6B), "${monthNameSafe(month)} 13–15")
                ForecastRow("Medium chance of pregnancy", Color(0xFFFFC857), "${monthNameSafe(month)} 12, 16")
                ForecastRow("Low chance of pregnancy", Color(0xFF5BD078), "${monthNameSafe(month)} 1–11, 17–31")
            }
        }

        Spacer(Modifier.height(120.dp))
    }
}

@Composable
private fun ForecastRow(text: String, color: Color, dates: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 5.dp)) {
        Box(Modifier.size(12.dp).clip(RoundedCornerShape(6.dp)).background(color))
        Spacer(Modifier.width(10.dp))
        Text("$text: $dates", style = MaterialTheme.typography.bodyMedium)
    }
}

// FIXED GRID — now safe inside verticalScroll
@Composable
private fun CalendarGrid(year: Int, month: Int) {
    val cal = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
    val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

    val cells = mutableListOf<Int?>()
    val leadingBlanks = (firstDayOfWeek - Calendar.SUNDAY + 7) % 7
    repeat(leadingBlanks) { cells.add(null) }
    for (d in 1..daysInMonth) cells.add(d)
    while (cells.size % 7 != 0) cells.add(null)

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cells.size) { index ->                // ← FIXED: use index instead of nullable items
            val day = cells[index]
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        when (day) {
                            in 5..9 -> Color(0xFFFFECEC)
                            in 10..13 -> Color(0xFFDCEBFF)
                            14, 15 -> Color(0xFFF3E8FF)
                            else -> Color(0xFFFFF2D6)
                        }.takeIf { day != null } ?: Color.Transparent
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (day != null) {
                    Text(
                        text = day.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF333333)
                    )
                }
            }
        }
    }
}
private fun monthNameSafe(monthZeroBased: Int): String =
    try {
        SimpleDateFormat("MMMM", Locale.getDefault()).format(
            Calendar.getInstance().apply { set(Calendar.MONTH, monthZeroBased) }.time
        )
    } catch (_: Exception) {
        "Month"
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalendarScreenPreview() {
    IntimaceTheme {
        CalendarScreen(
            navController = rememberNavController(),
            month = 10,
            year = 2025
        )
    }
}