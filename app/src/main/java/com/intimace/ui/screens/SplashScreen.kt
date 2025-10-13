package com.intimace.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.R
import com.intimace.ui.components.PrimaryButton

@Composable
fun SplashScreen(
    navController: NavHostController = rememberNavController(),
    onGetStarted: () -> Unit = {},
    onLogin: () -> Unit = {},
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(140.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.intimacelogo),
                    contentDescription = null,
                    modifier = Modifier.size(140.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Intimace", style = MaterialTheme.typography.displayLarge, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Your personal reproductive health companion", style = MaterialTheme.typography.bodyLarge, color = Color.Gray, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(28.dp))

            // Create Account / Get Started button
            PrimaryButton(text = "Log in", onClick = onLogin, modifier = Modifier.fillMaxWidth(0.8f))
            Spacer(modifier = Modifier.height(10.dp))
            PrimaryButton(text = "Get Started", onClick = onGetStarted, modifier = Modifier.fillMaxWidth(0.8f))

            // Text immediately below Create Account button (per your request)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Private, secure, and designed with your health in mind",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Short descriptive line above the Login button (per your request)

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}