package com.intimace.ui.screens.settingsPath

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.components.WhiteOutlinedFieldTrailing

@Composable
fun EditProfileScreen(
    navController: NavHostController = rememberNavController(),
    onBack: () -> Unit = {},
    onSave: (String, String, Int, Int, Int) -> Unit
) {
    var selectedNav by remember { mutableIntStateOf(4) }
    val scroll = rememberScrollState()

    // sample state
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var avgCycle by remember { mutableStateOf("") }
    var periodLength by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(innerPadding)
            .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onBack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Text("Edit Profile", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Personal Information
            Text("Personal Information", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            WhiteOutlinedFieldTrailing(value = name, onValueChange = { name = it }, labelText = "Name", placeholderText = "Your updated name")
            Spacer(modifier = Modifier.height(8.dp))
            WhiteOutlinedFieldTrailing(value = email, onValueChange = { email = it }, labelText = "Email", placeholderText = "Your updated email")
            Spacer(modifier = Modifier.height(8.dp))
            WhiteOutlinedFieldTrailing(value = age, onValueChange = { age = it }, labelText = "Age", placeholderText = "Your updated age", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

            Spacer(modifier = Modifier.height(12.dp))

            // Cycle Information
            Text("Cycle Information", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            WhiteOutlinedFieldTrailing(value = avgCycle, onValueChange = { avgCycle = it }, labelText = "Average Cycle Length (days)", placeholderText = "Your new ave. cycle length")
            Spacer(modifier = Modifier.height(8.dp))
            WhiteOutlinedFieldTrailing(value = periodLength, onValueChange = { periodLength = it }, labelText = "Period Length (days)", placeholderText = "Your new period length")

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { onSave(name, email, age.toInt(), avgCycle.toInt(), periodLength.toInt()) }, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(36.dp)) {
                Text("Save Changes")
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}