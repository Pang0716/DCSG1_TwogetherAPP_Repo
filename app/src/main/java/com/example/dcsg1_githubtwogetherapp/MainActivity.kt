package com.example.dcsg1_githubtwogetherapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
        enableEdgeToEdge()
        handleDeepLink(intent)
        setContent {
            DCSG1_GithubTwogetherAPPTheme {
                var showSplash by remember { mutableStateOf(true) }
                var isLoggedIn by remember { mutableStateOf(false) }
                var sessionChecked by remember { mutableStateOf(false) }
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

                                // If we're past the initial load and currently on the login screen,
                                // this means a fresh login just completed (e.g. Facebook OAuth) — navigate home
                                if (wasAlreadyChecked && navController.currentDestination?.route == "login") {
                                    navController.popBackStack("home", inclusive = false)
                                }
                            }
                            is SessionStatus.NotAuthenticated -> {
                                isLoggedIn = false
                                sessionChecked = true
                            }
                            else -> { /* still loading */ }
                        }
                    }
                }

                if (showSplash || !sessionChecked) {
                    SplashScreen(onTimeout = { showSplash = false })
                } else {
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                isLoggedIn = isLoggedIn,
                                onNavigateToLogin = { navController.navigate("login") },
                                onLogout = {
                                    scope.launch {
                                        logoutUser()
                                        isLoggedIn = false
                                    }
                                }
                            )
                        }
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = {
                                    loadCurrentUserProfile()
                                    isLoggedIn = true
                                    navController.popBackStack("home", inclusive = false)
                                },
                                onRegisterClick = { navController.navigate("register") }
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                onBackClick = { navController.popBackStack() }
                            )
                        }

                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        FacebookAuthManager.callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        intent?.data?.let { uri ->
            android.util.Log.d("DeepLinkTest", "Received URI: $uri")
            lifecycleScope.launch {
                try {
                    supabase.handleDeeplinks(intent)
                    android.util.Log.d("DeepLinkTest", "handleDeeplinks completed successfully")
                } catch (e: Exception) {
                    android.util.Log.e("DeepLinkTest", "handleDeeplinks failed: ${e.message}", e)
                }
            }
        }
    }
}

