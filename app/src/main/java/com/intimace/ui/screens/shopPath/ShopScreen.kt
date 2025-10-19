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
import androidx.compose.material.icons.filled.Inventory
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.data.products
import com.intimace.model.Product
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.components.ProductCard
import com.intimace.uistate.ShoppingCartUiState

@Composable
fun ShopScreen(
    navController: NavHostController = rememberNavController(),
    shoppingCartUiState: ShoppingCartUiState,
    onOpenProduct: (Product) -> Unit,
    onAddToCart: (Product, Double) -> Unit,
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
                    IconButton(onClick = onOpenOrders) {
                        Icon(imageVector = Icons.Default.Inventory, contentDescription = "Orders (box)")
                    }

                    // cart icon with small red badge (keeps the same look as your screenshot)
                    Box(modifier = Modifier.wrapContentSize()) {
                        IconButton(onClick = onOpenCart) {
                            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                        }
                        // red circular badge positioned over the cart
                        if (shoppingCartUiState.products.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .background(Color(0xFFFF6B6B), CircleShape)
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-6).dp, y = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    shoppingCartUiState.products.size.toString(),
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
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
                products.forEach { product ->
                    ProductCard(
                        img = product.img,
                        type = product.type,
                        name = product.name,
                        location = product.location,
                        price = product.price,
                        onClick = { onOpenProduct(product) },
                        onAddToCart = { onAddToCart(product, product.price) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(120.dp)) // safe spacing for bottom nav
        }
    }
}