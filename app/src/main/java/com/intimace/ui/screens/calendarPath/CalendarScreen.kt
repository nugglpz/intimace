package com.intimace.ui.screens.calendarPath

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import com.intimace.ui.components.AppBottomNav
import com.intimace.uistate.CalendarUiState
import com.intimace.viewmodel.CalendarViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import java.util.Locale
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.compose.rememberNavController
import com.intimace.ui.theme.IntimaceTheme

@Composable
fun CalendarScreen(
    navController: NavHostController,
    month: Int,
    year: Int = 2025,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    calendarViewModel: CalendarViewModel
) {
    // monthZeroBased: 0 = January, 8 = September

    val scrollState = rememberScrollState()

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text("Calendar", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            // Calendar card
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Month header
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onPrevious) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Prev") }
                        Text(
                            text = Calendar.getInstance().apply { set(Calendar.YEAR, year); set(Calendar.MONTH, month) }.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " ${year}",
                            style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = onNext) { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next") }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Weekday labels
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        listOf("S","M","T","W","T","F","S").forEach { d ->
                            Text(d, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Calendar grid
                    MonthCalendar(year = year, monthZeroBased = month)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Legend and forecast card
            Text("Legend", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                // small legend items
                val legend = listOf(
                    Pair("Menstruation Phase", Color(0xFFFFECEC)),
                    Pair("Follicular Phase", Color(0xFFDCEBFF)),
                    Pair("Ovulation Phase", Color(0xFFF3E8FF)),
                    Pair("Luteal Phase", Color(0xFFFFF2D6))
                )
                legend.forEach { (label, color) ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 6.dp)) {
                        Box(modifier = Modifier.size(16.dp).clip(RoundedCornerShape(8.dp)).background(color))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(label, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Fertility Forecast", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(Color(0xFFFF6B6B)))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("High chance of pregnancy: ${monthName(month)} 13-15", style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(Color(0xFFFFC857)))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Medium chance of pregnancy: ${monthName(month)} 12, 16", style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(Color(0xFF5BD078)))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Low chance of pregnancy: ${monthName(month)} 1-11, 17-31", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

fun monthName(monthZeroBased: Int): String {
    val cal = Calendar.getInstance()
    cal.set(Calendar.MONTH, monthZeroBased)
    return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) ?: ""
}

@Composable
fun MonthCalendar(year: Int, monthZeroBased: Int) {
    // Build list of day cells (including leading + trailing blanks so grid is full)
    val cal = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, monthZeroBased)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val firstWeekday = cal.get(Calendar.DAY_OF_WEEK) // 1=Sunday
    val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

    val cells = mutableListOf<Int?>()
    val leadingBlanks = (firstWeekday - Calendar.SUNDAY + 7) % 7
    repeat(leadingBlanks) { cells.add(null) }
    for (d in 1..daysInMonth) cells.add(d)
    // pad trailing blanks to make full weeks
    while (cells.size % 7 != 0) cells.add(null)

    // color mapping for demo
    fun bgForDay(d: Int?): Color {
        if (d == null) return Color.Transparent
        return when {
            d in 5..9 -> Color(0xFFFFE6E6)   // menstrual
            d in 10..11 -> Color(0xFFDCEBFF) // follicular
            d == 14 -> Color(0xFFF3E8FF)     // ovulation
            else -> Color(0xFFFFF2D6)        // luteal
        }
    }

    val rows = cells.size / 7
    val cellHeight = 56.dp
    // Use LazyVerticalGrid to force 7 equal-width cells per row (no spanning)
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier
            .fillMaxWidth()
            .height((rows * (cellHeight + 12.dp))), // estimate total height (cell + padding)
        userScrollEnabled = false,
        content = {
            items(cells.size) { idx ->
                val day = cells[idx]
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .height(cellHeight)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (day != null) {
                        Card(
                            modifier = Modifier.fillMaxSize(),
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(0.dp),
                            colors = CardDefaults.cardColors(containerColor = bgForDay(day))
                        ) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(text = day.toString(), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(6.dp), color = Color.DarkGray)
                            }
                        }
                    } else {
                        // empty cell (keeps grid shape)
                        Spacer(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalendarScreenPreview() {
    IntimaceTheme { // Assuming IntimaceTheme is your app's theme, as seen in the OrderScreenPreview
        // Mock NavHostController
        val navController = rememberNavController()

        // Mock CalendarViewModel
        val calendarViewModel = CalendarViewModel() // Ensure CalendarViewModel has a no-arg constructor or provide mock data

        // Render CalendarScreen with sample month and year
        CalendarScreen(
            navController = navController,
            month = 8, // September (0-based index)
            year = 2025,
            onPrevious = { /* No-op for preview */ },
            onNext = { /* No-op for preview */ },
            calendarViewModel = calendarViewModel
        )
    }
}