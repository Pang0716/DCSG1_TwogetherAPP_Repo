package com.example.dcsg1_githubtwogetherapp

/**
 * Same pattern as generatePackages()/generatePhotos()/generateReviews() - the About
 * description was previously a hardcoded venue-only sentence for every single vendor
 * regardless of category. This branches it by vendor.category instead.
 */
fun generateAboutDescription(vendor: Vendor): String {
    return when (vendor.category) {
        "Venue" -> "A luxurious wedding venue in the heart of ${vendor.locationArea}. " +
                "We provide elegant settings, halal catering and customizable packages to make your big day unforgettable."
        "Photographer" -> "A dedicated wedding photography team based in ${vendor.locationArea}, " +
                "capturing every candid and posed moment in a timeless, elegant style."
        "Makeup" -> "A professional bridal makeup studio in ${vendor.locationArea}, " +
                "specialising in flawless, long-lasting looks tailored to your wedding theme."
        "Live Band" -> "A live wedding band based in ${vendor.locationArea}, " +
                "bringing energy and elegance to your reception with a repertoire tailored to your celebration."
        "Emcee" -> "Experienced wedding emcees based in ${vendor.locationArea}, " +
                "keeping your programme running smoothly while engaging your guests throughout the event."
        "Attire" -> "A bridal and wedding attire specialist in ${vendor.locationArea}, " +
                "offering rentals and tailoring for the perfect look on your big day."
        "Deco" -> "A wedding decoration and styling team in ${vendor.locationArea}, " +
                "transforming venues into unforgettable settings tailored to your theme."
        else -> "A trusted wedding vendor in ${vendor.locationArea}, dedicated to making your big day unforgettable."
    }
}