package com.intimace.ui.screens.orderHistoryPath

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.data.orders
import com.intimace.model.Order
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.screens.shoppingCartPath.toPeso
import com.intimace.ui.theme.IntimaceTheme
import java.time.format.DateTimeFormatter
import java.util.Locale

val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US)
val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.US)

@Composable
fun OrderScreen(
    navController: NavHostController = rememberNavController(),
    order: Order,
    onBack: () -> Unit = {}
) {
    var selectedNav by remember { mutableIntStateOf(3) }
    val scrollState = rememberScrollState()

    Scaffold { innerPadding ->
        innerPadding
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Header
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onBack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Order Details",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Top summary card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                // If order.orderId is a String, use Text(order.orderId)
                                Text(
                                    text = order.orderId,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row {
                                    Text("Placed on", style = MaterialTheme.typography.bodySmall, color = Color.White, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = order.placementDate.format(dateFormatter),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Green
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xFFDFF6E9))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(order.status, color = Color(0xFF2E7D32), style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Divider()

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Order Timeline", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(12.dp))

                        // timeline - use Column with forEachIndexed (safe, small lists)
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            order.timeline.timelineSteps.forEachIndexed { idx, step ->
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    // timeline icon + line
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFFDFF6E9)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.LocalShipping,
                                                contentDescription = null,
                                                tint = Color(0xFF2E7D32)
                                            )
                                        }
                                        if (idx != order.timeline.timelineSteps.size - 1) {
                                            Box(
                                                modifier = Modifier
                                                    .width(2.dp)
                                                    .height(40.dp)
                                                    .background(Color(0xFFDFF6E9))
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column {
                                        Text(
                                            text = step.status,
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Row {
                                            Text(step.date.format(dateFormatter), style = MaterialTheme.typography.bodySmall, color = Color.Green)
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Text(text = step.time.format(timeFormatter), style = MaterialTheme.typography.bodySmall, color = Color.Green)
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Divider()
                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Delivery Information", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Address")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = order.address, style = MaterialTheme.typography.bodySmall)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.LocalShipping, contentDescription = "Tracking")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Tracking Number: ", style = MaterialTheme.typography.bodySmall, color = Color.White)
                            Text(text = order.trackingNumber, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Order items card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Order Items", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)

                        // Items list: use Column + forEach (safe for small order lists)
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            order.productsOrdered.forEach { (product, quantity) ->
                                Column {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(56.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(Color(0xFFF2F2F4))
                                        ) {
                                            Image(
                                                painter = painterResource(product.img),
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text("Qty: $quantity", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                        }
                                        Text(product.price.toPeso(), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                                    }

                                    Divider()
                                }
                            }
                        }
                    }
                }
            }

            // Order summary card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Order Summary", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Subtotal", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                            Text(order.subtotal.toPeso(), style = MaterialTheme.typography.bodyMedium)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Shipping", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                            Text( order.shipping.toPeso(), style = MaterialTheme.typography.bodyMedium)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(order.total.toPeso(), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrderScreenPreview() {
    IntimaceTheme {
        OrderScreen(order = orders[0])
    }
}