package com.example.dcsg1_githubtwogetherapp

import android.content.Context

object BudgetAlertChecker {
    private var lastAlertedPercentage = -1

    suspend fun check(context: Context, userId: String) {
        val percentage = BudgetSession.percentageUsed
        if (BudgetSession.totalBudget.value <= 0) return

        val shouldAlert = percentage >= 100 || percentage >= 90
        if (shouldAlert && percentage != lastAlertedPercentage) {
            lastAlertedPercentage = percentage
            val message = if (percentage >= 100)
                "You've gone over your wedding budget! Currently at $percentage%."
            else
                "You've used $percentage% of your wedding budget."
            NotificationRepository.add(context, userId, "Budget Alert", message)
            WeddingReminderWorker.showNotification(context, message)
        }
    }
}