package com.example.dcsg1_githubtwogetherapp

import android.content.Context

object FavoriteRepository {

    suspend fun loadFavoriteNames(context: Context, userId: String): Set<String> {
        return try {
            val remote = fetchAllFavorites(userId).map { it.vendorname }
            val dao = AppDatabase.getInstance(context).favoriteDao()
            dao.clearAll(userId)
            remote.forEach { dao.upsertFavorite(FavoriteEntity(userId, it)) }
            remote.toSet()
        } catch (e: Exception) {
            AppDatabase.getInstance(context).favoriteDao().getFavoriteNames(userId).toSet()
        }
    }

    suspend fun cacheAdd(context: Context, userId: String, vendorName: String) {
        AppDatabase.getInstance(context).favoriteDao().upsertFavorite(FavoriteEntity(userId, vendorName))
    }

    suspend fun cacheRemove(context: Context, userId: String, vendorName: String) {
        AppDatabase.getInstance(context).favoriteDao().deleteFavorite(userId, vendorName)
    }
}