package com.example.dcsg1_githubtwogetherapp

import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.Serializable

/**
 * Same pattern as SupabaseReview/SupabaseReviewInput in ReviewStorage.kt,
 * field names are all lowercase to match the Supabase table's column names.
 */
@Serializable
data class SupabaseFavorite(
    val id: Int,
    val userid: String,
    val vendorname: String
)

@Serializable
data class SupabaseFavoriteInput(
    val userid: String,
    val vendorname: String
)

/**
 * Checks whether this user has favorited this vendor.
 * Uses eq() twice (both userid and vendorname must match) - multiple eq() calls
 * inside filter { } default to "all conditions must be true" (AND), same function
 * as filter { eq(...) } in Practical 9, just called twice here.
 * Returns null if not favorited; non-null means favorited, and that record's id
 * is needed later for deletion.
 */
suspend fun fetchFavorite(userId: String, vendorName: String): SupabaseFavorite? {
    return supabase
        .from("favorites")
        .select {
            filter {
                eq("userid", userId)
                eq("vendorname", vendorName)
            }
        }
        .decodeList<SupabaseFavorite>()
        .firstOrNull()
}

/** Gets every vendor this user has favorited (for the "My Favorites" list screen), no vendor filter */
suspend fun fetchAllFavorites(userId: String): List<SupabaseFavorite> {
    return supabase
        .from("favorites")
        .select {
            filter {
                eq("userid", userId)
            }
        }
        .decodeList<SupabaseFavorite>()
}

/** Same insert style as addUser() in Practical 9 - no select() needed since we don't use the id right away */
suspend fun addFavorite(userId: String, vendorName: String) {
    supabase
        .from("favorites")
        .insert(SupabaseFavoriteInput(userid = userId, vendorname = vendorName))
}

/** Same delete style as deleteUser() in Practical 9 - deletes by id */
suspend fun removeFavorite(favoriteId: Int) {
    supabase
        .from("favorites")
        .delete {
            filter {
                eq("id", favoriteId)
            }
        }
}