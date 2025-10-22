package com.intimace.ui.screens.shopPath

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
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
import androidx.navigation.compose.rememberNavController
import com.intimace.model.Product
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.screens.shoppingCartPath.toPeso

@Composable
fun ProductScreen(
    navController: NavHostController = rememberNavController(),
    currentProduct: Product,
    onAddToCart: (Product, Double) -> Unit,
    onOpenCart: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val price = currentProduct.price.toPeso()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { onBack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Text("Product Detail", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onOpenCart) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Large product image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFEFEFF1)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(currentProduct.img),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Product image",
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Content card
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(currentProduct.type, color = Color.Yellow, style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("${currentProduct.quantity} LEFT", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(currentProduct.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("${currentProduct.birthControlHubName} | ${currentProduct.location}", style = MaterialTheme.typography.bodySmall, color = Color.Green)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(currentProduct.price.toPeso(), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        currentProduct.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onAddToCart(currentProduct, currentProduct.price) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(36.dp)
                    ) {
                        Text("Add to Cart", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}