package com.example.dcsg1_githubtwogetherapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages_cache")
data class ChatEntity(
    @PrimaryKey(autoGenerate = true) val localId: Int = 0,
    val myUserId: String,
    val otherPartyId: String,
    val otherPartyName: String,
    val vendorName: String,
    val isMine: Boolean,
    val content: String,
    val createdAt: Long
)