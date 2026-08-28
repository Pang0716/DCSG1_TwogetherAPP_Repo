package com.example.dcsg1_githubtwogetherapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.EventSeat
import androidx.compose.material.icons.outlined.LocalParking
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.ui.graphics.vector.ImageVector

data class Amenity(val label: String, val icon: ImageVector)

val defaultAmenities = listOf(
    Amenity("Ballroom", Icons.Outlined.EventSeat),
    Amenity("Halal Catering", Icons.Outlined.Restaurant),
    Amenity("Bridal Room", Icons.Outlined.Checkroom),
    Amenity("Parking", Icons.Outlined.LocalParking),
    Amenity("AV System", Icons.Outlined.Tv),
    Amenity("Wi-Fi", Icons.Outlined.Wifi)
)
