package com.example.dcsg1_githubtwogetherapp

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CartDao {
    @Upsert
    suspend fun upsertCartItem(item: CartEntity)

    @Query("DELETE FROM cart_items WHERE userId = :userId AND vendorName = :vendorName")
    suspend fun deleteCartItem(userId: String, vendorName: String)

    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    suspend fun getCartItems(userId: String): List<CartEntity>
}