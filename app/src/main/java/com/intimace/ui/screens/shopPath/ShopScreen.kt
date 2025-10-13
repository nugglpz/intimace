package com.intimace.ui.screens.shopPath

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.data.products
import com.intimace.model.Product
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.components.ProductCard

@Composable
fun ShopScreen(
    navController: NavHostController = rememberNavController(),
    onOpenProduct: (Product) -> Unit,
    onAddToCart: (Product) -> Unit,
    onOpenCart: () -> Unit,
    onOpenOrders: () -> Unit
) {
    var selectedNav by remember { mutableIntStateOf(3) } // shop index
    val scrollState = rememberScrollState()

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Birth Control Shop", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Box icon (orders / packages)
                    IconButton(onClick = { /* open orders */ }) {
                        Icon(imageVector = Icons.Default.AllInbox, contentDescription = "Orders (box)")
                    }

                    // cart icon with small red badge (keeps the same look as your screenshot)
                    Box(modifier = Modifier.wrapContentSize()) {
                        IconButton(onClick = { /* open cart */ }) {
                            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                        }
                        // red circular badge positioned over the cart
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .background(Color(0xFFFF6B6B), CircleShape)
                                .align(Alignment.TopEnd)
                                .offset(x = (-6).dp, y = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("2", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Search field placeholder
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.LightGray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Search products...", color = Color.LightGray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Product list
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                products.forEach { p ->
                    ProductCard(
                        img = p.img,
                        type = p.type,
                        name = p.name,
                        location = p.location,
                        price = p.price,
                        onClick = { onOpenProduct(p) },
                        onAddToCart = { onAddToCart(p) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(120.dp)) // safe spacing for bottom nav
        }
    }
}