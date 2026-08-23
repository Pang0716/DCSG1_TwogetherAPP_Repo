package com.example.dcsg1_githubtwogetherapp

data class Vendor(
    val name: String,
    val category: String,
    val rating: Double,
    val reviewCount: Int,
    val priceFrom: String,
    val imageUrl: String?,        // will hold a real photo link from Supabase later
    val locationArea: String,     // e.g. "George Town"
    val locationState: String     // e.g. "Penang"
)

// Temporary sample data — will be replaced by a real Supabase database query later
val sampleVendors = listOf(
    Vendor("The Light Hotel Penang", "Venue", 4.9, 128, "RM45,900", null, "George Town", "Penang"),
    Vendor("Timeless Photography", "Photographer", 4.9, 230, "RM1,200", null, "George Town", "Penang"),
    Vendor("Michelle Bridal Makeup", "Makeup", 4.8, 96, "RM800", null, "Komtar", "Penang")
)