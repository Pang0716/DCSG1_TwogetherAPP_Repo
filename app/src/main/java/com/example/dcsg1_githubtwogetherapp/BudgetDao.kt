package com.example.dcsg1_githubtwogetherapp

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface BudgetDao {
    @Upsert
    suspend fun upsertBudget(budget: BudgetEntity)

    @Query("SELECT * FROM budget WHERE userId = :userId LIMIT 1")
    suspend fun getBudget(userId: String): BudgetEntity?
}