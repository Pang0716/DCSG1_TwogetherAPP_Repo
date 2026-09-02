package com.example.dcsg1_githubtwogetherapp

import android.content.Context
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeddingRow(
    @SerialName("user_id") val userId: String,
    @SerialName("wedding_date_millis") val weddingDateMillis: Long?,
    @SerialName("guest_list") val guestList: List<String>
)

object WeddingRepository {

    suspend fun saveWedding(context: Context, userId: String, dateMillis: Long?, guestList: List<String>) {
        try {
            supabase.postgrest["wedding_info"].upsert(
                WeddingRow(userId = userId, weddingDateMillis = dateMillis, guestList = guestList)
            )
        } catch (e: Exception) { /* offline — Room copy below still saves */ }

        AppDatabase.getInstance(context).weddingDao()
            .upsertWedding(WeddingEntity(userId = userId, weddingDateMillis = dateMillis, guestList = guestList))
    }

    suspend fun loadWedding(context: Context, userId: String): Pair<Long?, List<String>> {
        try {
            val remote = supabase.postgrest["wedding_info"]
                .select { filter { eq("user_id", userId) } }
                .decodeSingleOrNull<WeddingRow>()

            if (remote != null) {
                AppDatabase.getInstance(context).weddingDao()
                    .upsertWedding(WeddingEntity(userId, remote.weddingDateMillis, remote.guestList))
                return remote.weddingDateMillis to remote.guestList
            }
        } catch (e: Exception) { /* offline — fall through to Room */ }

        val local = AppDatabase.getInstance(context).weddingDao().getWedding(userId)
        return (local?.weddingDateMillis) to (local?.guestList ?: emptyList())
    }
}