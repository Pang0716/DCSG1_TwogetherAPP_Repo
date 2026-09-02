package com.example.dcsg1_githubtwogetherapp

import android.content.Context
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BudgetRow(
    @SerialName("user_id") val userId: String,
    @SerialName("total_budget") val totalBudget: Double
)

object BudgetRepository {

    suspend fun saveBudget(context: Context, userId: String, amount: Double) {
        // 1. Save to Supabase (source of truth)
        try {
            supabase.postgrest["budgets"].upsert(BudgetRow(userId = userId, totalBudget = amount))
        } catch (e: Exception) {
            // offline or failed — that's OK, Room copy below still saves
        }

        // 2. Always cache locally too
        AppDatabase.getInstance(context).budgetDao()
            .upsertBudget(BudgetEntity(userId = userId, totalBudget = amount))
    }

    suspend fun loadBudget(context: Context, userId: String): Double {
        // 1. Try Supabase first (most up to date)
        try {
            val remote = supabase.postgrest["budgets"]
                .select { filter { eq("user_id", userId) } }
                .decodeSingleOrNull<BudgetRow>()

            if (remote != null) {
                // keep local cache fresh
                AppDatabase.getInstance(context).budgetDao()
                    .upsertBudget(BudgetEntity(userId = userId, totalBudget = remote.totalBudget))
                return remote.totalBudget
            }
        } catch (e: Exception) {
            // offline — fall through to local cache below
        }

        // 2. Fallback: local Room cache
        val local = AppDatabase.getInstance(context).budgetDao().getBudget(userId)
        return local?.totalBudget ?: 0.0
    }
}