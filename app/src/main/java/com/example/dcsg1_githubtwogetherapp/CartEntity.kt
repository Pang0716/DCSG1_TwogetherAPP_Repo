package com.example.dcsg1_githubtwogetherapp

import androidx.room.Entity

@Entity(tableName = "cart_items", primaryKeys = ["userId", "vendorName"])
data class CartEntity(
    val userId: String,
    val vendorName: String,
    val packageName: String,
    val isChecked: Boolean
)