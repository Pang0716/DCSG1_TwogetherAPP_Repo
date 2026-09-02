package com.example.dcsg1_githubtwogetherapp

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

/** Kept as the Venue list and as a fallback for any category not covered below. */
val defaultAmenities = listOf(
    Amenity("Ballroom", Icons.Outlined.EventSeat),
    Amenity("Halal Catering", Icons.Outlined.Restaurant),
    Amenity("Bridal Room", Icons.Outlined.Checkroom),
    Amenity("Parking", Icons.Outlined.LocalParking),
    Amenity("AV System", Icons.Outlined.Tv),
    Amenity("Wi-Fi", Icons.Outlined.Wifi)
)

/**
 * Same idea as generatePackages()/generatePhotos()/generateReviews() - branch by
 * vendor.category instead of showing venue-only amenities (ballroom, bridal room, etc.)
 * for every vendor regardless of type.
 */
fun generateAmenities(vendor: Vendor): List<Amenity> {
    return when (vendor.category) {
        "Venue" -> defaultAmenities
        "Photographer" -> listOf(
            Amenity("Digital Gallery", Icons.Outlined.PhotoLibrary),
            Amenity("Photo Album", Icons.Outlined.EventSeat),
            Amenity("Studio Access", Icons.Outlined.Face),
            Amenity("Same-day Preview", Icons.Outlined.EventAvailable)
        )
        "Makeup" -> listOf(
            Amenity("Airbrush Makeup", Icons.Outlined.Face),
            Amenity("Hair Styling", Icons.Outlined.ContentCut),
            Amenity("Trial Session", Icons.Outlined.EventAvailable),
            Amenity("Touch-up Kit", Icons.Outlined.Checkroom)
        )
        "Live Band" -> listOf(
            Amenity("Sound System", Icons.Outlined.QueueMusic),
            Amenity("Custom Setlist", Icons.Outlined.QueueMusic),
            Amenity("Stage Lighting", Icons.Outlined.Lightbulb),
            Amenity("MC Coordination", Icons.Outlined.Mic)
        )
        "Emcee" -> listOf(
            Amenity("Bilingual Hosting", Icons.Outlined.Translate),
            Amenity("Script Writing", Icons.Outlined.EventAvailable),
            Amenity("Rehearsal Included", Icons.Outlined.EventAvailable),
            Amenity("Games & Activities", Icons.Outlined.SportsEsports)
        )
        "Attire" -> listOf(
            Amenity("Fitting Sessions", Icons.Outlined.Checkroom),
            Amenity("Alterations", Icons.Outlined.ContentCut),
            Amenity("Dry Cleaning", Icons.Outlined.LocalLaundryService),
            Amenity("Accessories", Icons.Outlined.Palette)
        )
        "Deco" -> listOf(
            Amenity("Floral Arrangement", Icons.Outlined.LocalFlorist),
            Amenity("Lighting Design", Icons.Outlined.Lightbulb),
            Amenity("Backdrop Setup", Icons.Outlined.PhotoLibrary),
            Amenity("Theme Design", Icons.Outlined.Palette)
        )
        else -> defaultAmenities
    }
}