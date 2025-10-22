package com.intimace.ui.screens.checkoutPath

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intimace.model.Order
import com.intimace.model.Product
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.components.OrderCompleteOverlay
import com.intimace.ui.components.WhiteOutlinedFieldTrailing
import com.intimace.ui.screens.shoppingCartPath.toPeso
import com.intimace.uistate.ShoppingCartUiState
import org.apache.commons.validator.routines.CreditCardValidator
@Composable
fun CheckoutScreen(
    navController: NavHostController = rememberNavController(),
    shoppingCartUiState: ShoppingCartUiState,
    onBack: () -> Unit = {},
    onCompletePurchase: (List<Pair<Product, Int>>, Double, Double, Double, String, String, String, String, String, String, String) -> Unit
) {
    val tabs = listOf("Shipping", "Payment")
    var selectedTab by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()
    var showOrderComplete by remember { mutableStateOf(false) }
    var selectedNav by remember { mutableIntStateOf(3) }

    // Shipping state
    var shipFullName by remember { mutableStateOf("") }
    var shipAddress by remember { mutableStateOf("") }
    var shipCity by remember { mutableStateOf("") }
    var shipPostal by remember { mutableStateOf("") }

    // Payment state
    var cardNumber by remember { mutableStateOf("") }
    var cardExpiry by remember { mutableStateOf("") }
    var cardCvv by remember { mutableStateOf("") }

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onBack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Checkout", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { i, t ->
                    Tab(selected = selectedTab == i, onClick = { selectedTab = i }) {
                        Text(t, modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (selectedTab == 0) {
                        // Shipping form using WhiteOutlinedFieldTrailing (no outlinedTextFieldColors)
                        WhiteOutlinedFieldTrailing(
                            value = shipFullName,
                            onValueChange = { shipFullName = it },
                            labelText = "Full Name",
                            placeholderText = "Enter your full name",
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        WhiteOutlinedFieldTrailing(
                            value = shipAddress,
                            onValueChange = { shipAddress = it },
                            labelText = "Address",
                            placeholderText = "Enter your address",
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            WhiteOutlinedFieldTrailing(
                                value = shipCity,
                                onValueChange = { shipCity = it },
                                labelText = "City",
                                placeholderText = "e.g. Baguio City",
                                modifier = Modifier.weight(1f)
                            )
                            WhiteOutlinedFieldTrailing(
                                value = shipPostal,
                                onValueChange = { shipPostal = it },
                                labelText = "Postal Code",
                                placeholderText = "e.g. 2600",
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { selectedTab = 1 },
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            shape = RoundedCornerShape(36.dp)
                        ) {
                            Text("Continue to Payment", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    } else {
                        // Payment form using WhiteOutlinedFieldTrailing
                        WhiteOutlinedFieldTrailing(
                            value = cardNumber,
                            onValueChange = { cardNumber = it },
                            labelText = "Card Number",
                            placeholderText = "Enter your credit card number",
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            WhiteOutlinedFieldTrailing(
                                value = cardExpiry,
                                onValueChange = { cardExpiry = it },
                                labelText = "Expiration Date",
                                placeholderText = "MM/YY",
                                modifier = Modifier.weight(1f)
                            )
                            WhiteOutlinedFieldTrailing(
                                value = cardCvv,
                                onValueChange = { cardCvv = it },
                                labelText = "CVC",
                                placeholderText = "e.g. 123",
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(12.dp))
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
                        HorizontalDivider(
                            Modifier,
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(shoppingCartUiState.total.toPeso(), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                showOrderComplete = true
                                onCompletePurchase(shoppingCartUiState.products, shoppingCartUiState.subtotal, shoppingCartUiState.shipping, shoppingCartUiState.total, shipFullName, shipAddress, shipCity, shipPostal, cardNumber, cardExpiry, cardCvv)
                                      },
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            shape = RoundedCornerShape(36.dp),
                            enabled = shipFullName.isNotEmpty() && shipAddress.isNotEmpty() && shipCity.isNotEmpty() && shipPostal.isNotEmpty() && isCardNumberValid(cardNumber) && isExpiryDateValid(cardExpiry) && isCvvValid(cardCvv)
                        ) {
                            Text("Complete Purchase", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }

    if (showOrderComplete) {
        OrderCompleteOverlay(onDismiss = { showOrderComplete = false })
    }
}

fun isCardNumberValid(input: String): Boolean {
    val validator = CreditCardValidator()
    if (!validator.isValid(input)) {
        Log.e("validation", "CARD NUMBER VALIDATION FAILED")
        return false
    } else {
        Log.d("validation", "CARD NUMBER VALIDATION SUCCESS")
        return true
    }
}

fun isExpiryDateValid(input: String): Boolean {
    val expiryDatePattern = Regex("^(\\d{1,2})/(\\d{2})$")
    val match = expiryDatePattern.find(input.trim()) ?: return false

    val month = match.groupValues[1].toIntOrNull() ?: return false
    val year2 = match.groupValues[2].toIntOrNull() ?: return false

    // month must be 1..12
    if (month !in 1..12) return false

    // Accept two-digit years 00..99 (adjust policy as needed)
    if (year2 !in 0..99) return false

    return true
}


fun isCvvValid(input: String): Boolean {
    val cvvPattern = Regex("^\\d{3}\$")
    return cvvPattern.matches(input)
}