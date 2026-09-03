package com.example.dcsg1_githubtwogetherapp

import androidx.compose.runtime.mutableStateOf

object VendorFilterSession {
    val selectedState = mutableStateOf("Penang")
    val selectedCategory = mutableStateOf("All")
    val query = mutableStateOf("")
}