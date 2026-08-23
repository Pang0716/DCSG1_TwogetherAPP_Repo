package com.example.dcsg1_githubtwogetherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dcsg1_githubtwogetherapp.ui.theme.DCSG1_GithubTwogetherAPPTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DCSG1_GithubTwogetherAPPTheme {
                var showSplash by remember { mutableStateOf(true) }
                var isLoggedIn by remember { mutableStateOf(false) }

                if (showSplash) {
                    SplashScreen(onTimeout = { showSplash = false })
                } else {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                isLoggedIn = isLoggedIn,
                                onNavigateToLogin = { navController.navigate("login") }
                            )
                        }
                        composable("login") {
                            LoginScreen(
                                onLoginClick = { email, password ->
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
                                    // TODO: real Supabase account creation later
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
}

