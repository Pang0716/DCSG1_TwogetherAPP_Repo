package com.example.dcsg1_githubtwogetherapp

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(user: UserEntity)

    @Query("SELECT * FROM cached_user LIMIT 1")
    suspend fun getLastUser(): UserEntity?
}