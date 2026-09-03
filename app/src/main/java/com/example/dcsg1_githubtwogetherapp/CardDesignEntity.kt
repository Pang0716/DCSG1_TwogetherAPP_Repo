package com.example.dcsg1_githubtwogetherapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_designs")
data class CardDesignEntity(
    @PrimaryKey val id: String,
    val coupleNames: String,
    val eventDate: String,
    val venue: String,
    val style: String,
    val fontStyle: String,
    val photoUri: String?,
    val createdAt: Long
)