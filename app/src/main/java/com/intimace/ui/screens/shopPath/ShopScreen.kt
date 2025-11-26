package com.intimace.ui.screens.shopPath

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.intimace.data.products
import com.intimace.model.Product
import com.intimace.ui.components.ProductCard
import com.intimace.uistate.ShoppingCartUiState
import com.intimace.ui.theme.IntimaceGradient
import com.intimace.ui.theme.IntimacePurple
import com.intimace.ui.theme.IntimaceTheme

@Composable
fun ShopScreen(
    shoppingCartUiState: ShoppingCartUiState,
    onOpenProduct: (Product) -> Unit,
    onAddToCart: (Product, Double) -> Unit,
    onOpenCart: () -> Unit,
    onOpenOrders: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IntimaceGradient)           // YOUR LUXURIOUS GRADIENT
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(20.dp))

        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Birth Control Shop",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = IntimacePurple
            )

            Row {
                // Orders Icon
                IconButton(onClick = onOpenOrders) {
                    Icon(
                        imageVector = Icons.Default.Inventory,
                        contentDescription = "Orders",
                        tint = IntimacePurple,          // YOUR ROYAL PURPLE
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Cart Icon with Red Badge
                Box {
                    IconButton(onClick = onOpenCart) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = IntimacePurple,          // YOUR ROYAL PURPLE
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    if (shoppingCartUiState.products.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .background(Color(0xFFFF3B30), CircleShape)
                                .align(Alignment.TopEnd)
                                .offset(x = 8.dp, y = (-4).dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = shoppingCartUiState.products.size.toString(),
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Search Bar — Premium White Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color(0xFF888888))
                Spacer(Modifier.width(12.dp))
                Text("Search products...", color = Color(0xFF888888), style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(Modifier.height(28.dp))

        // Product Grid/List
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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

        Spacer(Modifier.height(140.dp)) // Safe space for bottom nav
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShopScreenPreview() {
    val mockCartState = ShoppingCartUiState(
        products = emptyList() // or listOf(Product(...)) if you want to show badge
    )

    ShopScreen(
        shoppingCartUiState = mockCartState,
        onOpenProduct = {},
        onAddToCart = { _, _ -> },
        onOpenCart = {},
        onOpenOrders = {}
    )
}