package com.example.dcsg1_githubtwogetherapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.WorkManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.ExistingWorkPolicy
import java.util.concurrent.TimeUnit

class WeddingReminderWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val message = inputData.getString("message") ?: "Your wedding day is coming up!"
        showNotification(applicationContext, message)

        val userId = UserSession.currentUser.value?.id
        if (userId != null) {
            NotificationRepository.add(applicationContext, userId, "Wedding Reminder", message)
        }
        return Result.success()
    }

    companion object {
        private const val CHANNEL_ID = "wedding_reminders"

        fun showNotification(context: Context, message: String) {
            val channel = NotificationChannel(CHANNEL_ID, "Wedding Reminders", NotificationManager.IMPORTANCE_HIGH)
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Twogether Wedding")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

            if (Build.VERSION.SDK_INT < 33 ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            ) {
                manager.notify(1001, notification)
            }
        }

        /** Cancels any existing reminders and schedules fresh ones based on the given wedding date. */
        fun scheduleReminders(context: Context, weddingDateMillis: Long?) {
            val workManager = WorkManager.getInstance(context)
            workManager.cancelUniqueWork("wedding_reminder_day_before")
            workManager.cancelUniqueWork("wedding_reminder_on_day")

            if (weddingDateMillis == null) return

            val now = System.currentTimeMillis()
            val dayBeforeDelay = (weddingDateMillis - 24 * 60 * 60 * 1000) - now
            val onDayDelay = weddingDateMillis - now

            if (dayBeforeDelay > 0) {
                val request = OneTimeWorkRequestBuilder<WeddingReminderWorker>()
                    .setInitialDelay(dayBeforeDelay, TimeUnit.MILLISECONDS)
                    .setInputData(androidx.work.workDataOf("message" to "Your wedding is tomorrow! Everything is almost ready 💍"))
                    .build()
                workManager.enqueueUniqueWork("wedding_reminder_day_before", ExistingWorkPolicy.REPLACE, request)
            }

            if (onDayDelay > 0) {
                val request = OneTimeWorkRequestBuilder<WeddingReminderWorker>()
                    .setInitialDelay(onDayDelay, TimeUnit.MILLISECONDS)
                    .setInputData(androidx.work.workDataOf("message" to "Today is your big day! Congratulations 🎉"))
                    .build()
                workManager.enqueueUniqueWork("wedding_reminder_on_day", ExistingWorkPolicy.REPLACE, request)
            }
        }
    }
}