package com.example.dcsg1_githubtwogetherapp

import android.content.Context
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookingRow(
    @SerialName("user_id") val userId: String,
    @SerialName("vendor_name") val vendorName: String,
    val category: String,
    val price: String,
    @SerialName("payment_method") val paymentMethod: String
)

object BookingRepository {

    suspend fun saveBooking(
        context: Context,
        userId: String,
        vendorName: String,
        category: String,
        price: String,
        paymentMethod: String
    ) {
        try {
            supabase.postgrest["bookings"].insert(
                BookingRow(userId, vendorName, category, price, paymentMethod)
            )
        } catch (e: Exception) { /* offline — Room copy below still saves */ }

        AppDatabase.getInstance(context).bookingDao().insertBooking(
            BookingEntity(
                userId = userId,
                vendorName = vendorName,
                category = category,
                price = price,
                paymentMethod = paymentMethod,
                bookedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun loadBookings(context: Context, userId: String): List<BookingEntity> {
        return AppDatabase.getInstance(context).bookingDao().getBookings(userId)
    }
}