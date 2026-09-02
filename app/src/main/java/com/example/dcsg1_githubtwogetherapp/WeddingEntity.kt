package com.example.dcsg1_githubtwogetherapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

class GuestListConverter {
    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString("||")

    @TypeConverter
    fun toList(data: String): List<String> = if (data.isBlank()) emptyList() else data.split("||")
}

@Entity(tableName = "wedding_info")
@TypeConverters(GuestListConverter::class)
data class WeddingEntity(
    @PrimaryKey val userId: String,
    val weddingDateMillis: Long?,
    val guestList: List<String>
)