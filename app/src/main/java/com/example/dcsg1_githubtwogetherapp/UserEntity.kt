package com.example.dcsg1_githubtwogetherapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_user")
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String?,
    val fullName: String?,
    val avatarUrl: String?,
    val phoneNumber: String?,
    val gender: String?,
    val dateOfBirth: String?
)