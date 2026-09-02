package com.example.dcsg1_githubtwogetherapp

import androidx.room.Dao
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Upsert

@Dao
@TypeConverters(GuestListConverter::class)
interface WeddingDao {
    @Upsert
    suspend fun upsertWedding(wedding: WeddingEntity)

    @Query("SELECT * FROM wedding_info WHERE userId = :userId LIMIT 1")
    suspend fun getWedding(userId: String): WeddingEntity?
}