package com.example.dcsg1_githubtwogetherapp

import androidx.compose.runtime.mutableStateOf

data class UserProfile(
    val id: String,
    val email: String?,
    val fullName: String?,
    val avatarUrl: String?
)

object UserSession {
    val currentUser = mutableStateOf<UserProfile?>(null)
}