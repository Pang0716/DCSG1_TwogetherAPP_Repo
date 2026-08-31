package com.example.dcsg1_githubtwogetherapp

import androidx.compose.runtime.mutableStateOf

object WeddingSession {
    val weddingDateMillis = mutableStateOf<Long?>(null)
    val guestList = mutableStateOf<List<String>>(emptyList())
}