package com.example.dcsg1_githubtwogetherapp

import androidx.room.Entity

@Entity(tableName = "chat_read_status", primaryKeys = ["myUserId", "otherPartyId", "vendorName"])
data class ChatReadEntity(
    val myUserId: String,
    val otherPartyId: String,
    val vendorName: String,
    val lastReadAt: Long
)