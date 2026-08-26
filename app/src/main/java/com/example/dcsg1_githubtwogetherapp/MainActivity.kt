package com.example.dcsg1_githubtwogetherapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dcsg1_githubtwogetherapp.ui.theme.DCSG1_GithubTwogetherAPPTheme
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DCSG1_GithubTwogetherAPPTheme {
                var showSplash by remember { mutableStateOf(true) }
                var isLoggedIn by remember { mutableStateOf(false) }
                var sessionChecked by remember { mutableStateOf(false) }
                val scope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    supabase.auth.sessionStatus.collect { status ->
                        when (status) {
                            is SessionStatus.Authenticated -> {
                                loadCurrentUserProfile()
                                isLoggedIn = true
                                sessionChecked = true
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
                    val navController = rememberNavController()

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
                                onBackClick = { navController.popBackStack() },
                                onRegisterClick = { name, email, phone, password ->
                                    isLoggedIn = true
                                    navController.popBackStack("home", inclusive = false)
                                }
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
}