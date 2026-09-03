package com.example.dcsg1_githubtwogetherapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        BudgetEntity::class,
        WeddingEntity::class,
        BookingEntity::class,
        CartEntity::class,
        UserEntity::class,
        FavoriteEntity::class,
        CardDesignEntity::class
    ],
    version = 9
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
    abstract fun weddingDao(): WeddingDao
    abstract fun bookingDao(): BookingDao
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun cardDesignDao(): CardDesignDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "twogether_db"
                )
                    .fallbackToDestructiveMigration(true)
                    .build().also { INSTANCE = it }
            }
        }
    }
}