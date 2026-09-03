package com.example.dcsg1_githubtwogetherapp

import android.content.Context
import android.content.Intent

fun shareCardLink(context: Context, designId: String) {
    val link = "https://yourname.github.io/wedding-details?id=$designId"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "You're invited! View the details here: $link")
    }
    context.startActivity(Intent.createChooser(intent, "Share invitation"))
}