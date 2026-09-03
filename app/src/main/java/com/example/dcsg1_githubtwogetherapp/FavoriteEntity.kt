package com.example.dcsg1_githubtwogetherapp

import androidx.room.Entity

@Entity(tableName = "cached_favorites", primaryKeys = ["userId", "vendorName"])
data class FavoriteEntity(
    val userId: String,
    val vendorName: String
)