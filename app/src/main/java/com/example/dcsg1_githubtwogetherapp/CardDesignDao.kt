package com.example.dcsg1_githubtwogetherapp


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardDesignDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDesign(design: CardDesignEntity)

    @Query("SELECT * FROM card_designs ORDER BY createdAt DESC")
    suspend fun getAllDesigns(): List<CardDesignEntity>
}