package com.intimace.ui.screens.shoppingCartPath

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.model.Product
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.theme.IntimaceTheme
import com.intimace.uistate.ShoppingCartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun ShoppingCartScreen(
    navController: NavHostController = rememberNavController(),
    shoppingCartUiState: ShoppingCartUiState,
    onProceedToCheckout: () -> Unit = {},
    onBack: () -> Unit = {},
    onRemove: (Product, Double) -> Unit,
    onAdd: (Product, Double) -> Unit,
    onSubtract: (Product, Double) -> Unit,
) {
    var showDeletedOverlay by remember { mutableStateOf<Pair<Boolean, String?>>(false to null) }
    var selectedNav by remember { mutableIntStateOf(3) }

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 120.dp) // leave room for bottom nav
        ) {
            // Header
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onBack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Text(
                        "Shopping Cart",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Empty state
            if (shoppingCartUiState.products.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Your cart is empty", color = Color.Gray)
                    }
                }
            } else {
                // Product items
                itemsIndexed(shoppingCartUiState.products) { index, (product, quantity) ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFF2F3F5)),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(product.img),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = product.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    product.price.toPeso(),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            // qty + delete
                            var qty by remember { mutableIntStateOf(1) }
                            Column(horizontalAlignment = Alignment.End) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = {
                                        // decrease
                                        if (qty > 1) {
                                            qty--
                                            onSubtract(product, product.price)
                                        }
                                    }) {
                                        Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease")
                                    }

                                    Card(shape = RoundedCornerShape(8.dp), elevation = CardDefaults.cardElevation(0.dp)) {
                                        Text(
                                            qty.toString(),
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }

                                    IconButton(onClick = {
                                        // increase
                                        if (qty < product.quantity) {
                                            qty++
                                            onAdd(product, product.price)
                                        }
                                    }) {
                                        Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                IconButton(onClick = { onRemove(product, product.price) }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFEF6C6C))
                                }
                            }
                        }
                    }
                }
            }

            // Summary & checkout - put as final item in the list
            item {
                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Subtotal", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                            Text(shoppingCartUiState.subtotal.toPeso(), style = MaterialTheme.typography.bodyMedium)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Shipping", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                            Text(shoppingCartUiState.shipping.toPeso(), style = MaterialTheme.typography.bodyMedium)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(shoppingCartUiState.total.toPeso(), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = { onProceedToCheckout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(36.dp),
                    enabled = shoppingCartUiState.products.isNotEmpty()
                ) {
                    Text("Proceed to Checkout", color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.height(120.dp))
            }
        }
    }

    // overlays
    if (showDeletedOverlay.first) {
        ItemDeletedOverlay(onDismiss = { showDeletedOverlay = false to null })
    }
}

fun Double.toPeso(): String = "₱%,.2f".format(this)

@Composable
fun ItemDeletedOverlay(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        ItemDeletedDialogContent(onDismiss = onDismiss)
    }
}

@Composable
fun ItemDeletedDialogContent(onDismiss: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDFF6E9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Deleted", tint = Color(0xFF2E7D32))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Item Deleted", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Item removed from cart.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onDismiss) { Text("Dismiss") }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ShoppingCartScreenPreview() {
//    IntimaceTheme {
//        ShoppingCartScreen(shoppingCartUiState = ShoppingCartUiState())
//    }
//}