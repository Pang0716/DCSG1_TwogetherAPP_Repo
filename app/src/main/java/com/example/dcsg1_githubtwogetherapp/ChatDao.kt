package com.example.dcsg1_githubtwogetherapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChatDao {
    @Insert
    suspend fun insertMessage(message: ChatEntity)

    @Query("SELECT * FROM chat_messages_cache WHERE myUserId = :myUserId AND otherPartyId = :otherPartyId AND vendorName = :vendorName ORDER BY createdAt ASC")
    suspend fun getMessages(myUserId: String, otherPartyId: String, vendorName: String): List<ChatEntity>

    @Query("SELECT * FROM chat_messages_cache WHERE myUserId = :myUserId ORDER BY createdAt ASC")
    suspend fun getAllMessagesForUser(myUserId: String): List<ChatEntity>
}