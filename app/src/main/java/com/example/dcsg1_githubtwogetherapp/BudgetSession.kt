package com.example.dcsg1_githubtwogetherapp

import androidx.compose.runtime.mutableStateOf

object BudgetSession {
    val totalBudget = mutableStateOf(0.0)

    val usedBudget: Double
        get() = CartSession.totalCart

    val remainingBudget: Double
        get() = totalBudget.value - usedBudget

    val percentageUsed: Int
        get() = if (totalBudget.value <= 0) 0 else ((usedBudget / totalBudget.value) * 100).toInt()
}