package com.example.dcsg1_githubtwogetherapp

val PG = listOf(
    // Venue
    Vendor(
        "The Light Hotel Penang", "Venue", 4.9, 128, "RM45,900", null,
        R.drawable.the_light_hotel_png, "George Town", "Penang",
        capacity = "100 - 800 pax",
        highlights = "Elegant ballroom, halal catering, custom packages",
        photoResIds = listOf(
            R.drawable.thelighthotelroom,
            R.drawable.thelighthotellobby,
            R.drawable.thelighthotelballroom,
            R.drawable.thelighthotelswimming
        )
    ),
    Vendor(
        "Eastern & Oriental Hotel", "Venue", 4.8, 176, "RM52,000", null,
        R.drawable.eastern_orientalhotel_png, "George Town", "Penang",
        capacity = "150 - 1000 pax",
        highlights = "Sea view ballroom, heritage architecture, in-house catering"
    ),
    Vendor(
        "Berjaya Penang Hotel", "Venue", 4.5, 64, "RM22,000", null,
        R.drawable.berjayapenang, "George Town", "Penang",
        capacity = "100 - 500 pax",
        highlights = "City-centre location, versatile function halls, competitive package rates"
    ),
    Vendor(
        "The Prestige Hotel Penang", "Venue", 4.7, 92, "RM30,000", null,
        R.drawable.thepresitagehotel, "George Town", "Penang",
        capacity = "80 - 400 pax",
        highlights = "Modern boutique hotel, rooftop pool views, contemporary ballroom design"
    ),
    Vendor(
        "Macalister Mansion", "Venue", 4.6, 71, "RM48,000", null,
        R.drawable.macalistermansion, "George Town", "Penang",
        capacity = "40 - 150 pax",
        highlights = "Heritage colonial mansion, intimate garden courtyard, boutique luxury styling"
    ),
    Vendor(
        "The Millen Penang, Autograph Collection", "Venue", 4.8, 178, "RM58,000", null,
        R.drawable.themillen, "George Town", "Penang",
        capacity = "150 - 800 pax",
        highlights = "Luxury 5-star ballroom, panoramic sea view windows, premium fine-dining catering"
    ),
    Vendor(
        "PARKROYAL Penang Resort", "Venue", 4.6, 84, "RM42,000", null,
        R.drawable.parkroyal, "Batu Ferringhi", "Penang",
        capacity = "100 - 600 pax",
        highlights = "Beachfront resort setting, tropical garden ceremony, sunset reception views"
    ),

    // Photographer
    Vendor(
        "Timeless Photography", "Photographer", 4.9, 230, "RM1,200", null,
        R.drawable.timeless_photography_logo_png, "George Town", "Penang",
        capacity = "Full day coverage",
        highlights = "Candid shots, drone footage, same-day sneak peek",
        photoResIds = listOf(
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3
        ),

    ),
    Vendor(
        "Aperture Studios", "Photographer", 4.7, 142, "RM1,500", null,
        R.drawable.aperture_studio_logo_png, "Bayan Lepas", "Penang",
        capacity = "Half / full day packages",
        highlights = "Cinematic editing, studio pre-wedding shoots, printed album",
        photoResIds = listOf(
            R.drawable.photo4,
            R.drawable.photo5,
            R.drawable.photo6
        ),
    ),
    Vendor(
        "Lumina Photography", "Photographer", 4.8, 165, "RM1,350", null,
        R.drawable.luminaphotography, "Bukit Mertajam", "Penang",
        capacity = "Full day coverage",
        highlights = "Natural light specialist, film-style editing, engagement shoot included"
    ),
    Vendor(
        "Frame & Story Studio", "Photographer", 4.7, 120, "RM1,600", null,
        R.drawable.storyphotograph, "George Town", "Penang",
        capacity = "Full day coverage",
        highlights = "Documentary style, same-week gallery delivery, second shooter included"
    ),
    Vendor(
        "Golden Hour Photography", "Photographer", 4.9, 201, "RM2,000", null,
        R.drawable.goldenhour, "Tanjung Bungah", "Penang",
        capacity = "Full day + pre-wedding",
        highlights = "Cinematic color grading, drone footage, printed photobook"
    ),

    //  Makeup
    Vendor(
        "Michelle Bridal Makeup", "Makeup", 4.8, 96, "RM800", null,
        R.drawable.michelle_bridal_makeup_png, "Komtar", "Penang",
        capacity = "1 - 2 looks per booking",
        highlights = "Airbrush makeup, hairstyling included, trial session available"
    ),
    Vendor(
        "Glow Beauty Studio", "Makeup", 4.6, 58, "RM650", null,
        R.drawable.glow_beauty_studio_png, "George Town", "Penang",
        capacity = "1 look per booking",
        highlights = "Natural glam look, long-lasting makeup, on-site service"
    ),
    Vendor(
        "Bella Rosa Makeup Studio", "Makeup", 4.9, 112, "RM900", null,
        R.drawable.bellarose, "Tanjung Bungah", "Penang",
        capacity = "1 - 3 looks per booking",
        highlights = "HD makeup, traditional & modern styles, touch-up kit included"
    ),
    Vendor(
        "Glamour Bridal Beauty", "Makeup", 4.7, 88, "RM750", null,
        R.drawable.glamourbridalmakeup, "George Town", "Penang",
        capacity = "1 - 2 looks per booking",
        highlights = "Airbrush available, false lash application, on-location service"
    ),
    Vendor(
        "Elegance Makeup Artistry", "Makeup", 4.8, 134, "RM1,100", null,
        R.drawable.elegancemakeup, "Bayan Lepas", "Penang",
        capacity = "1 - 3 looks per booking",
        highlights = "Bridal trial included, hairstyling combo, waterproof makeup"
    ),

    // Live Band
    Vendor(
        "Melody Live Band", "Live Band", 4.8, 65, "RM2,500", null,
        R.drawable.melody_liveband_png, "George Town", "Penang",
        capacity = "4 - 6 members",
        highlights = "Customisable song list, live sound system included, 2-hour set"
    ),
    Vendor(
        "Harmony Strings Quartet", "Live Band", 4.7, 40, "RM1,800", null,
        R.drawable.harmony_strings_quartet_png, "Tanjung Bungah", "Penang",
        capacity = "4 members",
        highlights = "Classical & pop covers, ceremony & reception sets, elegant attire"
    ),
    Vendor(
        "The Groove Collective", "Live Band", 4.6, 52, "RM2,200", null,
        R.drawable.thegroovecollective, "Butterworth", "Penang",
        capacity = "5 - 7 members",
        highlights = "Wide song repertoire, MC-band combo package, sound & lighting included"
    ),
    Vendor(
        "Silver Note Ensemble", "Live Band", 4.7, 68, "RM2,800", null,
        R.drawable.silvernoteensemble, "George Town", "Penang",
        capacity = "6 members",
        highlights = "Jazz & acoustic sets, dinner background music, formal attire"
    ),

    //  Emcee
    Vendor(
        "Ace Emcee", "Emcee", 4.9, 78, "RM800", null,
        R.drawable.ace_emcee_png, "George Town", "Penang",
        capacity = "Bilingual hosting",
        highlights = "Energetic hosting style, custom script, games & icebreakers"
    ),
    Vendor(
        "Voice of Joy Emcee", "Emcee", 4.6, 34, "RM650", null,
        R.drawable.voiceofjoy_emcee_png, "Komtar", "Penang",
        capacity = "Bilingual hosting",
        highlights = "Warm hosting tone, family-friendly script, flexible timing"
    ),
    Vendor(
        "Golden Voice Emcee", "Emcee", 4.8, 95, "RM950", null,
        R.drawable.goldenvoice, "Bayan Lepas", "Penang",
        capacity = "Trilingual hosting",
        highlights = "English/Malay/Mandarin, formal & fun tone options, rehearsal included"
    ),

    // Attire
    Vendor(
        "Classic Bridal Wear", "Attire", 4.8, 54, "RM1,500", null,
        R.drawable.classic_bridal_wear_png, "George Town", "Penang",
        capacity = "Bride & groom outfits",
        highlights = "Custom tailoring, in-house alterations, fitting sessions included"
    ),
    Vendor(
        "Elegant Threads Boutique", "Attire", 4.7, 89, "RM1,200", null,
        R.drawable.elegant_threads_boutique_png, "Bayan Lepas", "Penang",
        capacity = "Bride & groom outfits",
        highlights = "Modern & traditional styles, rental & purchase options, accessories included"
    ),
    Vendor(
        "Heritage Bridal House", "Attire", 4.9, 143, "RM1,800", null,
        R.drawable.heritagebridal, "George Town", "Penang",
        capacity = "Bride & groom outfits",
        highlights = "Handmade beading, kebaya & baju melayu options, free fitting sessions"
    ),
    Vendor(
        "Ivory & Lace Bridal", "Attire", 4.7, 76, "RM1,400", null,
        R.drawable.ivory_lacebridal, "Komtar", "Penang",
        capacity = "Bride & groom outfits",
        highlights = "Western gown collection, alterations included, accessories rental"
    ),

    //  Deco
    Vendor(
        "Bloom & Vine Decor", "Deco", 4.8, 96, "RM3,500", null,
        R.drawable.bloom_vinedeco, "George Town", "Penang",
        capacity = "Full event styling",
        highlights = "Floral arch specialist, romantic garden theme, setup & teardown included"
    ),
    Vendor(
        "Elegant Touch Styling", "Deco", 4.6, 58, "RM2,800", null,
        R.drawable.eleganttouchstyling, "Bayan Lepas", "Penang",
        capacity = "Full event styling",
        highlights = "Modern minimalist decor, LED lighting design, backdrop customization"
    )
)