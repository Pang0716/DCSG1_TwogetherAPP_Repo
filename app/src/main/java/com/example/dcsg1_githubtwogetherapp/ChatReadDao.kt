package com.example.dcsg1_githubtwogetherapp

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ChatReadDao {
    @Upsert
    suspend fun markRead(entry: ChatReadEntity)

    @Query("SELECT lastReadAt FROM chat_read_status WHERE myUserId = :myUserId AND otherPartyId = :otherPartyId AND vendorName = :vendorName")
    suspend fun getLastReadAt(myUserId: String, otherPartyId: String, vendorName: String): Long?
}