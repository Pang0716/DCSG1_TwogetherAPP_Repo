package com.example.dcsg1_githubtwogetherapp

import androidx.compose.runtime.mutableStateOf

data class UserProfile(
    val id: String,
    val email: String?,
    val fullName: String?,
    val avatarUrl: String?,
    val phoneNumber: String? = null,
    val gender: String? = null,
    val dateOfBirth: String? = null
)

object UserSession {
    val currentUser = mutableStateOf<UserProfile?>(null)
}