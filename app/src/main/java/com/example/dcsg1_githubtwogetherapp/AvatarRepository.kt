package com.example.dcsg1_githubtwogetherapp

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import io.github.jan.supabase.storage.storage
import java.io.ByteArrayOutputStream

object AvatarRepository {

    suspend fun uploadFromUri(context: Context, userId: String, uri: Uri): String {
        val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            ?: throw Exception("Could not read image")
        return upload(userId, bytes)
    }

    suspend fun uploadFromBitmap(userId: String, bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream)
        return upload(userId, stream.toByteArray())
    }

    private suspend fun upload(userId: String, bytes: ByteArray): String {
        val path = "$userId/avatar.jpg"
        supabase.storage["avatars"].upload(path, bytes) { upsert = true }
        return supabase.storage["avatars"].publicUrl(path) + "?t=${System.currentTimeMillis()}"
        // the ?t= cache-buster ensures the new photo shows immediately, not a stale cached one
    }
}