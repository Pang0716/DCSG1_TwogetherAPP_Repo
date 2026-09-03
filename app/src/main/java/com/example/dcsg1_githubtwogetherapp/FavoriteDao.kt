package com.example.dcsg1_githubtwogetherapp

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FavoriteDao {
    @Upsert
    suspend fun upsertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM cached_favorites WHERE userId = :userId AND vendorName = :vendorName")
    suspend fun deleteFavorite(userId: String, vendorName: String)

    @Query("SELECT vendorName FROM cached_favorites WHERE userId = :userId")
    suspend fun getFavoriteNames(userId: String): List<String>

    @Query("DELETE FROM cached_favorites WHERE userId = :userId")
    suspend fun clearAll(userId: String)

    @Query("SELECT COUNT(*) FROM cached_favorites WHERE userId = :userId")
    suspend fun count(userId: String): Int
}