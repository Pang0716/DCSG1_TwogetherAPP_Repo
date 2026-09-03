package com.example.dcsg1_githubtwogetherapp

import android.content.Context

object UserRepository {
    suspend fun saveUser(context: Context, profile: UserProfile) {
        AppDatabase.getInstance(context).userDao().upsertUser(
            UserEntity(
                profile.id, profile.email, profile.fullName, profile.avatarUrl,
                profile.phoneNumber, profile.gender, profile.dateOfBirth
            )
        )
    }

    suspend fun loadLastUser(context: Context): UserProfile? {
        val entity = AppDatabase.getInstance(context).userDao().getLastUser() ?: return null
        return UserProfile(
            entity.id, entity.email, entity.fullName, entity.avatarUrl,
            entity.phoneNumber, entity.gender, entity.dateOfBirth
        )
    }
}