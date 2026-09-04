package com.example.dcsg1_githubtwogetherapp

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

fun shareWeddingCard(
    context: Context,
    bitmap: Bitmap
) {
    try {

        val file = File(
            context.cacheDir,
            "wedding_card.png"
        )

        file.outputStream().use { outputStream ->
            bitmap.compress(
                Bitmap.CompressFormat.PNG,
                100,
                outputStream
            )
        }

        val imageUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"

            putExtra(
                Intent.EXTRA_STREAM,
                imageUri
            )

            putExtra(
                Intent.EXTRA_TEXT,
                "You're invited to our wedding! 💍"
            )

            addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            clipData = ClipData.newRawUri(
                "Wedding Invitation",
                imageUri
            )
        }

        context.startActivity(
            Intent.createChooser(
                shareIntent,
                "Share wedding invitation"
            )
        )

    } catch (e: Exception) {

        Toast.makeText(
            context,
            "Share failed: ${e.message}",
            Toast.LENGTH_LONG
        ).show()

        e.printStackTrace()
    }
}