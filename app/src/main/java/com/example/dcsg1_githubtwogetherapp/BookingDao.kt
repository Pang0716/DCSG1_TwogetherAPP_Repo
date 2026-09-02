package com.example.dcsg1_githubtwogetherapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookingDao {
    @Insert
    suspend fun insertBooking(booking: BookingEntity)

    @Query("SELECT * FROM bookings WHERE userId = :userId ORDER BY bookedAt DESC")
    suspend fun getBookings(userId: String): List<BookingEntity>
}