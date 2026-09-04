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

        val shareText = context.getString(R.string.wedding_invite_share_text)
        val shareTitle = context.getString(R.string.share_wedding_invitation)

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"

            putExtra(
                Intent.EXTRA_STREAM,
                imageUri
            )

            putExtra(
                Intent.EXTRA_TEXT,
                shareText
            )

            addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            clipData = ClipData.newRawUri(
                shareTitle,
                imageUri
            )
        }

        context.startActivity(
            Intent.createChooser(
                shareIntent,
                shareTitle
            )
        )

    } catch (e: Exception) {

        Toast.makeText(
            context,
            context.getString(R.string.share_failed, e.message ?: ""),
            Toast.LENGTH_LONG
        ).show()

        e.printStackTrace()
    }
}