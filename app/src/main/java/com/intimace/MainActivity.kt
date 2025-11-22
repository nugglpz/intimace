package com.intimace

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.intimace.data.currentAccount
import com.intimace.data.guides
import com.intimace.data.orders
import com.intimace.ui.components.AppBottomNav
import com.intimace.ui.screens.calendarPath.CalendarScreen
import com.intimace.ui.screens.checkoutPath.CheckoutScreen
import com.intimace.ui.screens.createAccountPath.CreateAccountScreen
import com.intimace.ui.screens.settingsPath.EditProfileScreen
import com.intimace.ui.screens.createAccountPath.FirstWelcomeScreen
import com.intimace.ui.screens.createAccountPath.FourthWelcomeScreen
import com.intimace.ui.screens.guidesPath.GuidesScreen
import com.intimace.ui.screens.settingsPath.HelpAndSupportScreen
import com.intimace.ui.screens.homePath.HomeScreen
import com.intimace.ui.screens.homePath.LogSymptomsScreen
import com.intimace.ui.screens.loginPath.LoginScreen
import com.intimace.ui.screens.settingsPath.NotificationsScreen
import com.intimace.ui.screens.orderHistoryPath.OrderHistoryScreen
import com.intimace.ui.screens.orderHistoryPath.OrderScreen
import com.intimace.ui.screens.settingsPath.PartnerLinkScreen
import com.intimace.ui.screens.settingsPath.PrivacyAndSecurityScreen
import com.intimace.ui.screens.shopPath.ProductScreen
import com.intimace.ui.screens.loginPath.ResetPasswordScreen
import com.intimace.ui.screens.createAccountPath.SecondWelcomeScreen
import com.intimace.ui.screens.settingsPath.SettingsScreen
import com.intimace.ui.screens.shopPath.ShopScreen
import com.intimace.ui.screens.shoppingCartPath.ShoppingCartScreen
import com.intimace.ui.screens.SplashScreen
import com.intimace.ui.screens.createAccountPath.ThirdWelcomeScreen
import com.intimace.ui.screens.guidesPath.GuideScreen
import com.intimace.ui.theme.IntimaceTheme
import com.intimace.viewmodel.CalendarViewModel
import com.intimace.viewmodel.CheckoutViewModel
import com.intimace.viewmodel.CreateAccountViewModel
import com.intimace.viewmodel.GuideViewModel
import com.intimace.viewmodel.HomeViewModel
import com.intimace.viewmodel.LogSymptomsViewModel
import com.intimace.viewmodel.LoginViewModel
import com.intimace.viewmodel.OrderHistoryViewModel
import com.intimace.viewmodel.SettingsViewModel
import com.intimace.viewmodel.ShopViewModel
import com.intimace.viewmodel.ShoppingCartViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntimaceTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    IntimaceApp()
                    innerPadding
                }
            }
        }
    }
}

@Composable
fun IntimaceApp(
    calendarViewModel: CalendarViewModel = viewModel(),
    checkoutViewModel: CheckoutViewModel = viewModel(),
    createAccountViewModel: CreateAccountViewModel = viewModel(),
    guideViewModel: GuideViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(),
    logSymptomsViewModel: LogSymptomsViewModel = viewModel(),
    orderHistoryViewModel: OrderHistoryViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel(),
    shoppingCartViewModel: ShoppingCartViewModel = viewModel(),
    shopViewModel: ShopViewModel = viewModel(),
) {
    val calendarUiState by calendarViewModel.calendarUiState.collectAsState()
    val checkoutUiState by checkoutViewModel.checkoutUiState.collectAsState()
    val createAccountUiState by createAccountViewModel.createAccountUiState.collectAsState()
    val guideUiState by guideViewModel.guideUiState.collectAsState()
    val homeUiState by homeViewModel.homeUiState.collectAsState()
    val loginUiState by loginViewModel.loginUiState.collectAsState()
    val logSymptomsUiState by logSymptomsViewModel.logSymptomsUiState.collectAsState()
    val orderHistoryUiState by orderHistoryViewModel.orderHistoryUiState.collectAsState()
    val settingsUiState by settingsViewModel.settingsUiState.collectAsState()
    val shoppingCartUiState by shoppingCartViewModel.shoppingCartUiState.collectAsState()
    val shopUiState by shopViewModel.shopUiState.collectAsState()


    val navController = rememberNavController()

    // ROUTES where we DO NOT want to show the bottom nav (first few screens)
    val hideBottomBarRoutes = setOf(
        "splash",
        "login",
        "createAccount",
        "resetPassword",
        "emailSent",
        "welcome1",
        "welcome2",
        "welcome3",
        "welcome4"
    )

    // Observe current destination so composable recomposes on navigation
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Check if any destination in the hierarchy matches the hide list.
    // Using hierarchy makes this robust if you have nested graphs or parameterized routes.
    val shouldHideBottomBar = currentDestination?.hierarchy
        ?.any { destination -> destination.route in hideBottomBarRoutes } == true


    Scaffold(
        bottomBar = {
            if (!shouldHideBottomBar) {
                AppBottomNav(navController = navController)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            NavHost(navController = navController, startDestination = "splash") {

                // Splash / Auth
                composable("splash") {
                    SplashScreen(
                        onGetStarted = { navController.navigate("createAccount") },
                        onLogin = { navController.navigate("login") }
                    )
                }

                composable("login") {
                    val context = LocalContext.current
                    LoginScreen(
                        onSignIn = { email, password ->
                            loginViewModel.logUserIn(email, password)
                            navController.navigate("home")
                            Toast.makeText(context, "Logging you in...", Toast.LENGTH_SHORT).show()
                        },
                        onCreateAccount = { navController.navigate("createAccount") },
                        onForgotPassword = {
                            loginUiState.copy(resetPasswordTriggered = true)
                            navController.navigate("resetPassword")
                        }
                    )
                }
                composable("createAccount") {
                    CreateAccountScreen(
                        onCreateAccount = { email, username, password, pin ->
                            createAccountViewModel.initializeEmailUsernamePasswordPin(email, username, password, pin)
                            navController.navigate("welcome1")
                        },
                        onSignIn = { navController.navigate("login") }
                    )
                }

                composable("resetPassword") {
                    val context = LocalContext.current
                    ResetPasswordScreen(
                        navController = navController,
                        onSend = { email ->
                            Toast.makeText(context, "Reset link has been sent to your email.", Toast.LENGTH_SHORT).show()
                            loginViewModel.initializeResetEmail(email)
                            navController.navigate("login")
                        }
                    )
                }

                // Onboarding / Welcome flow
                composable("welcome1") {
                    FirstWelcomeScreen(onContinue = {
                        navController.navigate("welcome2") },
                        onSkip = { navController.navigate("home") }
                    )
                }

                composable("welcome2") {
                    SecondWelcomeScreen(
                        onContinue = { name, age, sex ->
                            createAccountViewModel.initializeNameAgeSex(name, age, sex)
                            navController.navigate("welcome3")
                        },
                        onBack = { navController.popBackStack() },
                        onSkip = { navController.navigate("home") }
                    )
                }

                composable("welcome3") {
                    ThirdWelcomeScreen(
                        createAccountUiState = createAccountUiState,
                        onContinue = { isRegular, averageCycleDays, firstDayOfLastPeriod ->
                            createAccountViewModel.initializeIsRegularAverageCycleDaysFirstDayOfLastPeriod(isRegular, averageCycleDays, firstDayOfLastPeriod)
                            navController.navigate("welcome4")
                        },
                        onBack = { navController.popBackStack() },
                        onSkip = { navController.navigate("home") }
                    )
                }

                composable("welcome4") {
                    val context = LocalContext.current
                    FourthWelcomeScreen(
                        onComplete = { isSexuallyActive ->
                            createAccountViewModel.initializeIsSexuallyActive(isSexuallyActive)
                            Toast.makeText(context, "Welcome to Intimace!", Toast.LENGTH_SHORT).show()
                            navController.navigate("home")
                        },
                        onBack = { navController.popBackStack() },
                        onSkip = { navController.navigate("home") }
                    )
                }

                // Main tabs / primary screens
                composable("home") {
                    HomeScreen(
                        navController = navController,
                        onLogSymptoms = { navController.navigate("logSymptoms") },
                        onCalendar = { navController.navigate("calendar") },
                        onGuides = { navController.navigate("guides") },
                        onShop = { navController.navigate("shop") },
                        onProfile = { navController.navigate("profile") }
                    )
                }

                composable("logSymptoms") {
                    val context = LocalContext.current
                    LogSymptomsScreen(
                        onSave = { entryDateMillis, mood, symptoms, bodyTemperature, notes ->
                            logSymptomsViewModel.initializeAll(entryDateMillis, mood, symptoms, bodyTemperature, notes)
                            Toast.makeText(context, "Symptoms saved.", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        },
                        onBack = { navController.popBackStack() }
                    )
                }


                composable("calendar") {
                    CalendarScreen(
                        navController = navController,                    // ← Real navController
                        month = calendarUiState.monthIndex,               // 0-based (January = 0)
                        year = calendarUiState.year,
                        onPrevious = { calendarViewModel.decrementMonthIndex() },
                        onNext = { calendarViewModel.incrementMonthIndex() },
                        calendarViewModel = calendarViewModel             // ← Real ViewModel
                    )
                }

                composable("guides") {
                    GuidesScreen(
                        navController = navController,
                        guidesList = guides,
                        onOpenGuide = { index ->
                            guideViewModel.setGuideIndex(index)
                            navController.navigate("guide")
                        },
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("guide") {
                    GuideScreen(
                        guideIndex = guideUiState.guideIndex,
                        onBack = { navController.popBackStack() }
                    )
                }

                // Shop + product + cart
                composable("shop") {
                    val context = LocalContext.current
                    ShopScreen(
                        navController = navController,
                        shoppingCartUiState = shoppingCartUiState,
                        onOpenProduct = { product ->
                            shopViewModel.setCurrentProduct(product)
                            navController.navigate("product")
                        },
                        onOpenCart = { navController.navigate("cart") },
                        onOpenOrders = { navController.navigate("orders") },
                        onAddToCart = { product, price ->
                            shoppingCartViewModel.addToCart(product, price)
                            Toast.makeText(context, "Added to cart!", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                composable(
                    route = "product"
                ) { backStackEntry ->
                    val context = LocalContext.current
                    ProductScreen(
                        navController = navController,
                        currentProduct = shopUiState.currentProduct,
                        onAddToCart = { product, price ->
                            shoppingCartViewModel.addToCart(product, price)
                            Toast.makeText(context, "Added to cart!", Toast.LENGTH_SHORT).show()
                            navController.navigate("cart")
                        },
                        onOpenCart = { navController.navigate("cart") },
                        onBack = { navController.popBackStack() })
                }

                composable("cart") {
                    val context = LocalContext.current
                    ShoppingCartScreen(
                        navController = navController,
                        shoppingCartUiState = shoppingCartUiState,
                        onProceedToCheckout = { navController.navigate("checkout") },
                        onRemove = { product, price ->
                            shoppingCartViewModel.removeFromCart(product, price)
                            Toast.makeText(context, "Product removed from shopping cart.", Toast.LENGTH_SHORT).show()
                        },
                        onAdd = { product, price ->
                            shoppingCartViewModel.addQuantity(product, price)
                        },
                        onSubtract = { product, price ->
                            shoppingCartViewModel.subtractQuantity(product, price)
                        },
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("checkout") {
                    val context = LocalContext.current
                    CheckoutScreen(
                        navController = navController,
                        shoppingCartUiState = shoppingCartUiState,
                        onBack = { navController.popBackStack() },
                        onCompletePurchase = { productsOrdered, subtotal, shipping, total, shipFullName, shipAddress, shipCity, shipPostal, cardNumber, cardExpiry, cardCvv ->
                            checkoutViewModel.placeOrder(productsOrdered, subtotal, shipping, total, shipFullName, shipAddress, shipCity, shipPostal, cardNumber, cardExpiry, cardCvv)
                            Toast.makeText(context, "Order placed successfully!", Toast.LENGTH_SHORT).show()
                            navController.navigate("orders")
                        }
                    )
                }

                composable("orders") {
                    orderHistoryViewModel. setOrders(orders)
                    OrderHistoryScreen(
                        navController = navController,
                        orders = orderHistoryUiState.orders,
                        onOpenOrder = { order ->
                            orderHistoryViewModel.setCurrentOrder(order)
                            navController.navigate("order")
                        },
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("order") {
                    OrderScreen(
                        navController = navController,
                        order = orderHistoryUiState.currentOrder,
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("settings") {
                    val context = LocalContext.current
                    SettingsScreen(
                        navController = navController,
                        createAccountUiState = createAccountUiState,
                        onEditProfile = { navController.navigate("editProfile") },
                        onNotifications = { navController.navigate("notifications") },
                        onPrivacy = { navController.navigate("privacy") },
                        onPartnerLink = { navController.navigate("partnerLink") },
                        onHelp = { navController.navigate("help") },
                        onLogout = {
                            navController.navigate("login") {
                                Toast.makeText(context, "Logging you out...", Toast.LENGTH_SHORT).show()
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        }
                    )
                }

                composable("editProfile") {
                    val context = LocalContext.current
                    EditProfileScreen(
                        navController = navController,
                        onSave = { name, email, age, avgCycle, periodLength ->
                            settingsViewModel.editProfile(name, email, age, avgCycle, periodLength)
                            Toast.makeText(context, "Changes saved!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("notifications") {
                    NotificationsScreen(
                        navController = navController,
                        settingsUiState = settingsUiState,
                        onToggle = { setting ->
                            settingsViewModel.toggleNotificationSetting(setting)
                        },
                        onTimePreferenceSelected = { setting, time ->
                            settingsViewModel.setTimePreferences(setting, time)
                        },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("privacy") {
                    PrivacyAndSecurityScreen(
                        navController = navController,
                        settingsUiState = settingsUiState,
                        onToggle = { setting ->
                            settingsViewModel.toggleNotificationSetting(setting)
                        },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("partnerLink") {
                    val context = LocalContext.current
                    PartnerLinkScreen(
                        navController = navController,
                        settingsUiState = settingsUiState,
                        onToggle = { setting ->
                            settingsViewModel.toggleNotificationSetting(setting)
                        },
                        onBack = { navController.popBackStack() },
                        onUnlink = {
                            currentAccount.hasPartner = false
                            Toast.makeText(context, "Partner link removed!", Toast.LENGTH_SHORT).show()
                            navController.navigate("partnerLink")
                        }

                    )
                }

                composable("help") {
                    HelpAndSupportScreen(
                        navController = navController,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}