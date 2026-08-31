package com.example.dcsg1_githubtwogetherapp

import androidx.compose.runtime.mutableStateOf

object BudgetSession {
    val totalBudget = mutableStateOf(0.0)
    val usedBudget = mutableStateOf(0.0)

    val remainingBudget: Double
        get() = totalBudget.value - usedBudget.value

    val percentageUsed: Int
        get() = if (totalBudget.value <= 0) 0 else ((usedBudget.value / totalBudget.value) * 100).toInt()
}