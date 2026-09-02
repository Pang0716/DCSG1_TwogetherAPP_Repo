package com.example.dcsg1_githubtwogetherapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val localId: Int = 0,
    val userId: String,
    val vendorName: String,
    val category: String,
    val price: String,
    val paymentMethod: String,
    val bookedAt: Long
)