package com.example.dcsg1_githubtwogetherapp

data class Vendor(
    val name: String,
    val category: String,
    val rating: Double,
    val reviewCount: Int,
    val priceFrom: String,
    val imageUrl: String?,        // will hold a real photo link from Supabase later
    val imageResId: Int? = null,
    val locationArea: String,     // e.g. "George Town"
    val locationState: String,    // e.g. "Penang"
    val capacity: String = "",
    val highlights: String = ""
)

// Temporary sample data — will be replaced by a real Supabase database query later
val sampleVendors = listOf(
    Vendor(
        "The Light Hotel Penang",
        "Venue",
        4.9, 128,
        "RM45,900",
        null,
        R.drawable.the_light_hotel_png,
        "George Town",
        "Penang",
        capacity = "100 - 800 pax",
        highlights = "Elegant ballroom, halal catering, custom packages"
    ),

    Vendor(
        "Eastern & Oriental Hotel",
        "Venue",
        4.8, 176,
        "RM52,000",
        null,
        R.drawable.eastern_orientalhotel_png,
        "George Town",
        "Penang",
        capacity = "150 - 1000 pax",
        highlights = "Sea view ballroom, heritage architecture, in-house catering"
    ),

    Vendor(
        "Timeless Photography",
        "Photographer",
        4.9, 230,
        "RM1,200",
        null,
        R.drawable.timeless_photography_logo_png,
        "George Town",
        "Penang",
        capacity = "Full day coverage",
        highlights = "Candid shots, drone footage, same-day sneak peek"
    ),

    Vendor(
        "Aperture Studios",
        "Photographer",
        4.7, 142,
        "RM1,500",
        null,
        R.drawable.aperture_studio_logo_png,
        "Bayan Lepas",
        "Penang",
        capacity = "Half / full day packages",
        highlights = "Cinematic editing, studio pre-wedding shoots, printed album"
    ),

    Vendor(
        "Michelle Bridal Makeup",
        "Makeup",
        4.8, 96,
        "RM800",
        null,
        R.drawable.michelle_bridal_makeup_png,
        "Komtar",
        "Penang",
        capacity = "1 - 2 looks per booking",
        highlights = "Airbrush makeup, hairstyling included, trial session available"
    ),

    Vendor(
        "Glow Beauty Studio",
        "Makeup",
        4.6, 58,
        "RM650",
        null,
        R.drawable.glow_beauty_studio_png,
        "George Town",
        "Penang",
        capacity = "1 look per booking",
        highlights = "Natural glam look, long-lasting makeup, on-site service"
    ),

    Vendor(
        "Melody Live Band",
        "Live Band",
        4.8, 65,
        "RM2,500",
        null,
        R.drawable.melody_liveband_png,
        "George Town",
        "Penang",
        capacity = "4 - 6 members",
        highlights = "Customisable song list, live sound system included, 2-hour set"
    ),

    Vendor(
        "Harmony Strings Quartet",
        "Live Band",
        4.7, 40,
        "RM1,800",
        null,
        R.drawable.harmony_strings_quartet_png,
        "Tanjung Bungah",
        "Penang",
        capacity = "4 members",
        highlights = "Classical & pop covers, ceremony & reception sets, elegant attire"
    ),

    Vendor(
        "Ace Emcee",
        "Emcee",
        4.9, 78,
        "RM800",
        null,
        R.drawable.ace_emcee_png,
        "George Town",
        "Penang",
        capacity = "Bilingual hosting",
        highlights = "Energetic hosting style, custom script, games & icebreakers"
    ),

    Vendor(
        "Voice of Joy Emcee",
        "Emcee",
        4.6, 34,
        "RM650",
        null,
        R.drawable.voiceofjoy_emcee_png,
        "Komtar",
        "Penang",
        capacity = "Bilingual hosting",
        highlights = "Warm hosting tone, family-friendly script, flexible timing"
    ),

    Vendor(
        "Classic Bridal Wear",
        "Attire",
        4.8, 54,
        "RM1,500",
        null,
        R.drawable.classic_bridal_wear_png,
        "George Town",
        "Penang",
        capacity = "Bride & groom outfits",
        highlights = "Custom tailoring, in-house alterations, fitting sessions included"
    ),

    Vendor(
        "Elegant Threads Boutique",
        "Attire",
        4.7, 89,
        "RM1,200",
        null,
        R.drawable.elegant_threads_boutique_png,
        "Bayan Lepas",
        "Penang",
        capacity = "Bride & groom outfits",
        highlights = "Modern & traditional styles, rental & purchase options, accessories included"
    )
)