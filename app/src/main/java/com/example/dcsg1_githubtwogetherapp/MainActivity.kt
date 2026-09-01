package com.example.dcsg1_githubtwogetherapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dcsg1_githubtwogetherapp.ui.theme.DCSG1_GithubTwogetherAPPTheme
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch
import com.facebook.FacebookSdk
import io.github.jan.supabase.auth.handleDeeplinks

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)

        // Apply saved language on every app launch
        val savedLangCode = LanguagePreferences.getSavedLanguage(this)
        val locale = java.util.Locale(savedLangCode)
        java.util.Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        enableEdgeToEdge()
        handleDeepLink(intent)
        setContent {
            DCSG1_GithubTwogetherAPPTheme {
                var showSplash by remember { mutableStateOf(true) }
                var isLoggedIn by remember { mutableStateOf(false) }
                var sessionChecked by remember { mutableStateOf(false) }
                var selectedHomeTab by remember { mutableStateOf(0) }
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    supabase.auth.sessionStatus.collect { status ->
                        when (status) {
                            is SessionStatus.Authenticated -> {
                                loadCurrentUserProfile()
                                val wasAlreadyChecked = sessionChecked
                                isLoggedIn = true
                                sessionChecked = true

                                val currentRoute = navController.currentDestination?.route
                                if (wasAlreadyChecked && (currentRoute == "login" || currentRoute == "register")) {
                                    navController.popBackStack("home", inclusive = false)
                                }
                            }

                            is SessionStatus.NotAuthenticated -> {
                                isLoggedIn = false
                                sessionChecked = true
                            }

                            else -> { /* still loading */
                            }
                        }
                    }
                }

                if (showSplash || !sessionChecked) {
                    SplashScreen(onTimeout = { showSplash = false })
                } else {
                    LaunchedEffect(PasswordResetState.isPendingReset.value) {
                        if (PasswordResetState.isPendingReset.value) {
                            navController.navigate("reset_password")
                            PasswordResetState.isPendingReset.value = false
                        }
                    }
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                isLoggedIn = isLoggedIn,
                                selectedTab = selectedHomeTab,
                                onTabSelected = { selectedHomeTab = it },
                                onNavigateToLogin = { navController.navigate("login") },
                                onLogout = { scope.launch { logoutUser(); isLoggedIn = false } },
                                onEditProfile = { navController.navigate("edit_profile") },
                                onHelpSupport = { navController.navigate("help_support") },
                                onLanguage = { navController.navigate("language") },
                                onVendorClick = { vendor -> navController.navigate("vendorDetail/${vendor.name}") },
                                onProceedToPayment = { navController.navigate("payment") }
                            )
                        }


                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = {
                                    loadCurrentUserProfile()
                                    isLoggedIn = true
                                    LoginEventState.showWelcomeMessage.value =
                                        true   // ← add this line
                                    navController.popBackStack("home", inclusive = false)
                                },
                                onRegisterClick = { navController.navigate("register") },
                                onForgotPasswordClick = { navController.navigate("forgot_password") }
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                onBackClick = { navController.popBackStack() },
                                onNavigateToTerms = { navController.navigate("terms") },
                                onNavigateToPrivacy = { navController.navigate("privacy") }
                            )
                        }
                        composable("terms") {
                            TermsOfServiceScreen(onBackClick = { navController.popBackStack() })
                        }
                        composable("privacy") {
                            PrivacyPolicyScreen(onBackClick = { navController.popBackStack() })
                        }
                        composable("forgot_password") {
                            ForgotPasswordScreen(
                                onBackClick = { navController.popBackStack() },
                                onResetComplete = {
                                    isLoggedIn = false
                                    navController.popBackStack("home", inclusive = false)
                                }
                            )
                        }
                        composable("reset_password") {
                            ResetPasswordScreen(
                                onDone = {
                                    isLoggedIn = false
                                    navController.popBackStack("home", inclusive = false)
                                }
                            )
                        }

                        // MainActivity.kt additions inside NavHost
                        composable("edit_profile") { EditProfileScreen(onBackClick = { navController.popBackStack() }) }
                        composable("help_support") { HelpSupportScreen(onBackClick = { navController.popBackStack() }) }
                        composable("language") { LanguageScreen(onBackClick = { navController.popBackStack() }) }

                        composable("browseVendors") {
                            BrowseVendorsScreen(
                                vendors = sampleVendors,
                                onVendorClick = { vendor ->
                                    navController.navigate("vendorDetail/${vendor.name}")
                                },
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                        composable(
                            route = "vendorDetail/{vendorName}",
                            arguments = listOf(navArgument("vendorName") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val vendorName = backStackEntry.arguments?.getString("vendorName") ?: ""
                            val vendor = sampleVendors.find { it.name == vendorName }
                            if (vendor != null) {
                                VendorDetailScreen(
                                    vendor = vendor,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        }

                        composable("payment") {
                            PaymentScreen(
                                onBackClick = { navController.popBackStack() },
                                onPayNowClick = { /* TODO: next step — confirmation screen */ }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        FacebookAuthManager.callbackManager.onActivityResult(
            requestCode,
            resultCode,
            data
        )
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        intent?.data?.let {
            lifecycleScope.launch {
                try {
                    supabase.handleDeeplinks(intent)
                } catch (e: Exception) {
                    android.util.Log.e(
                        "DeepLinkTest",
                        "handleDeeplinks failed: ${e.message}",
                        e
                    )
                }
            }
        }
    }
}

