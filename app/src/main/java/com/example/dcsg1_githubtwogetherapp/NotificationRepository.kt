package com.example.dcsg1_githubtwogetherapp

import android.content.Context

object NotificationRepository {
    suspend fun add(context: Context, userId: String, title: String, message: String) {
        AppDatabase.getInstance(context).notificationDao().insert(
            NotificationEntity(userId = userId, title = title, message = message, createdAt = System.currentTimeMillis())
        )
    }

    suspend fun loadAll(context: Context, userId: String): List<NotificationEntity> {
        return AppDatabase.getInstance(context).notificationDao().getAll(userId)
    }

    suspend fun markAllRead(context: Context, userId: String) {
        AppDatabase.getInstance(context).notificationDao().markAllRead(userId)
    }

    suspend fun hasUnread(context: Context, userId: String): Boolean {
        return AppDatabase.getInstance(context).notificationDao().unreadCount(userId) > 0
    }
}