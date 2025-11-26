package com.intimace.ui.screens.shopPath

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intimace.model.Product
import com.intimace.ui.components.PrimaryButton
import com.intimace.ui.theme.*
import com.intimace.ui.screens.shoppingCartPath.toPeso

@Composable
fun ProductScreen(
    currentProduct: Product,
    onAddToCart: (Product, Double) -> Unit,
    onOpenCart: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IntimaceGradient)           // YOUR SIGNATURE GRADIENT
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = IntimacePurple,          // YOUR ROYAL PURPLE
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = "Product Details",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = IntimacePurple
            )

            Spacer(Modifier.weight(1f))

            IconButton(onClick = onOpenCart) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = IntimacePurple,          // YOUR ROYAL PURPLE
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Product Image – Premium Card
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Image(
                painter = painterResource(id = currentProduct.img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(24.dp))
            )
        }

        Spacer(Modifier.height(28.dp))

        // Product Info Card – White & Clean
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                // Type (e.g., "Pills", "IUD")
                Text(
                    text = currentProduct.type.uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = IntimacePurple
                )

                Spacer(Modifier.height(6.dp))

                // Stock Warning
                Text(
                    text = "${currentProduct.quantity} LEFT ONLY",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF3B30) // signature red
                )

                Spacer(Modifier.height(12.dp))

                // Product Name
                Text(
                    text = currentProduct.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )

                Spacer(Modifier.height(8.dp))

                // Hub & Location
                Text(
                    text = "${currentProduct.birthControlHubName} • ${currentProduct.location}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF666666)
                )

                Spacer(Modifier.height(20.dp))

                // Price
                Text(
                    text = currentProduct.price.toPeso(),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = IntimacePurple
                )

                Spacer(Modifier.height(20.dp))

                // Description
                Text(
                    text = currentProduct.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF444444),
                    lineHeight = 28.sp
                )

                Spacer(Modifier.height(32.dp))

                // Add to Cart Button – Your PrimaryButton
                PrimaryButton(
                    text = "Add to Cart",
                    onClick = { onAddToCart(currentProduct, currentProduct.price) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp)
                )

                Spacer(Modifier.height(20.dp))
            }
        }

        Spacer(Modifier.height(140.dp)) // safe space for bottom nav
    }
}

// GORGEOUS PREVIEW
// GORGEOUS, WORKING PREVIEW — NO MORE ERRORS!
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductScreenPreview() {
    IntimaceTheme {
        val mockProduct = Product(
            img = android.R.drawable.ic_menu_gallery, // built-in Android icon (always exists)
            type = "Pills",
            name = "Trust Pills",
            quantity = 8,
            birthControlHubName = "Intimace Hub",
            location = "Quezon City",
            price = 450.0,
            description = "Combined oral contraceptive with iron supplement. Take daily."
        )

        ProductScreen(
            currentProduct = mockProduct,
            onAddToCart = { _, _ -> },
            onOpenCart = {},
            onBack = {}
        )
    }
}