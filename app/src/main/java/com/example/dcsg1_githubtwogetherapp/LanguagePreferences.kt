package com.example.dcsg1_githubtwogetherapp

import android.content.Context

object LanguagePreferences {
    private const val PREFS_NAME = "twogether_prefs"
    private const val KEY_LANGUAGE = "selected_language_code"

    fun saveLanguage(context: Context, languageCode: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
    }

    fun getSavedLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANGUAGE, "en") ?: "en"
    }
}