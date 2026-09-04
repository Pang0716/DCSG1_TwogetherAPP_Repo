package com.example.dcsg1_githubtwogetherapp

import android.content.Context
import kotlin.math.abs

fun generateAboutDescription(vendor: Vendor, context: Context): String {
    val templates: List<Int> = when (vendor.category) {
        "Venue" -> listOf(R.string.about_venue_1, R.string.about_venue_2, R.string.about_venue_3)
        "Photographer" -> listOf(R.string.about_photographer_1, R.string.about_photographer_2, R.string.about_photographer_3)
        "Makeup" -> listOf(R.string.about_makeup_1, R.string.about_makeup_2, R.string.about_makeup_3)
        "Live Band" -> listOf(R.string.about_liveband_1, R.string.about_liveband_2, R.string.about_liveband_3)
        "Emcee" -> listOf(R.string.about_emcee_1, R.string.about_emcee_2, R.string.about_emcee_3)
        "Attire" -> listOf(R.string.about_attire_1, R.string.about_attire_2, R.string.about_attire_3)
        "Deco" -> listOf(R.string.about_deco_1, R.string.about_deco_2, R.string.about_deco_3)
        else -> listOf(R.string.about_fallback)
    }
    val variantIndex = abs(vendor.name.hashCode()) % templates.size
    return context.getString(templates[variantIndex], vendor.locationArea)
}