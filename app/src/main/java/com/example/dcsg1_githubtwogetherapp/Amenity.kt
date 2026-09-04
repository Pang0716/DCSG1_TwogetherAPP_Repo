package com.example.dcsg1_githubtwogetherapp

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.ContentCut
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.EventSeat
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material.icons.outlined.LocalLaundryService
import androidx.compose.material.icons.outlined.LocalParking
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.QueueMusic
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.ui.graphics.vector.ImageVector

data class Amenity(val label: String, val icon: ImageVector)

fun generateAmenities(vendor: Vendor, context: Context): List<Amenity> {
    fun s(resId: Int) = context.getString(resId)

    val defaultAmenities = listOf(
        Amenity(s(R.string.am_ballroom), Icons.Outlined.EventSeat),
        Amenity(s(R.string.am_halal_catering), Icons.Outlined.Restaurant),
        Amenity(s(R.string.am_bridal_room), Icons.Outlined.Checkroom),
        Amenity(s(R.string.am_parking), Icons.Outlined.LocalParking),
        Amenity(s(R.string.am_av_system), Icons.Outlined.Tv),
        Amenity(s(R.string.am_wifi), Icons.Outlined.Wifi)
    )

    return when (vendor.category) {
        "Venue" -> defaultAmenities
        "Photographer" -> listOf(
            Amenity(s(R.string.am_digital_gallery), Icons.Outlined.PhotoLibrary),
            Amenity(s(R.string.am_photo_album), Icons.Outlined.EventSeat),
            Amenity(s(R.string.am_studio_access), Icons.Outlined.Face),
            Amenity(s(R.string.am_same_day_preview), Icons.Outlined.EventAvailable)
        )
        "Makeup" -> listOf(
            Amenity(s(R.string.am_airbrush_makeup), Icons.Outlined.Face),
            Amenity(s(R.string.am_hair_styling), Icons.Outlined.ContentCut),
            Amenity(s(R.string.am_trial_session), Icons.Outlined.EventAvailable),
            Amenity(s(R.string.am_touchup_kit), Icons.Outlined.Checkroom)
        )
        "Live Band" -> listOf(
            Amenity(s(R.string.am_sound_system), Icons.Outlined.QueueMusic),
            Amenity(s(R.string.am_custom_setlist), Icons.Outlined.QueueMusic),
            Amenity(s(R.string.am_stage_lighting), Icons.Outlined.Lightbulb),
            Amenity(s(R.string.am_mc_coordination), Icons.Outlined.Mic)
        )
        "Emcee" -> listOf(
            Amenity(s(R.string.am_bilingual_hosting), Icons.Outlined.Translate),
            Amenity(s(R.string.am_script_writing), Icons.Outlined.EventAvailable),
            Amenity(s(R.string.am_rehearsal_included), Icons.Outlined.EventAvailable),
            Amenity(s(R.string.am_games_activities), Icons.Outlined.SportsEsports)
        )
        "Attire" -> listOf(
            Amenity(s(R.string.am_fitting_sessions), Icons.Outlined.Checkroom),
            Amenity(s(R.string.am_alterations), Icons.Outlined.ContentCut),
            Amenity(s(R.string.am_dry_cleaning), Icons.Outlined.LocalLaundryService),
            Amenity(s(R.string.am_accessories), Icons.Outlined.Palette)
        )
        "Deco" -> listOf(
            Amenity(s(R.string.am_floral_arrangement), Icons.Outlined.LocalFlorist),
            Amenity(s(R.string.am_lighting_design), Icons.Outlined.Lightbulb),
            Amenity(s(R.string.am_backdrop_setup), Icons.Outlined.PhotoLibrary),
            Amenity(s(R.string.am_theme_design), Icons.Outlined.Palette)
        )
        else -> defaultAmenities
    }
}