package com.example.dcsg1_githubtwogetherapp

import java.util.Locale

data class PackageOption(
    val name: String,
    val price: String,
    val capacity: String,
    val imageUrl: String? = null,
    val imageResId: Int? = null,
    val tags: List<String>,
    val isPopular: Boolean = false
)

private fun formatPrice(amount: Int): String {
    return "RM" + String.format(Locale.US, "%,d", amount)
}

/**
 * Routes to the matching package template based on vendor.category.
 * category must exactly match the strings in BrowseVendorsScreen's categories list
 * (Venue / Photographer / Makeup / Live Band / Emcee / Attire / Deco).
 * A typo or case mismatch will just fall through to the else branch.
 */
fun generatePackages(vendor: Vendor): List<PackageOption> {
    val basePrice = vendor.priceFrom.filter { it.isDigit() }.toIntOrNull() ?: 1000

    return when (vendor.category) {
        "Venue" -> venuePackages(basePrice)
        "Photographer" -> photographerPackages(basePrice)
        "Makeup" -> makeupPackages(basePrice)
        "Live Band" -> liveBandPackages(basePrice)
        "Emcee" -> emceePackages(basePrice)
        "Attire" -> attirePackages(basePrice)
        "Deco" -> decoPackages(basePrice)
        else -> venuePackages(basePrice)
    }
}

private fun venuePackages(basePrice: Int): List<PackageOption> = listOf(
    PackageOption(
        name = "Elegance Package",
        price = formatPrice(basePrice),
        capacity = "100 - 300 pax",
        imageResId = R.drawable.elegancepackage,
        tags = listOf("Halal catering", "Elegant ballroom", "Custom packages"),
        isPopular = true
    ),
    PackageOption(
        name = "Signature Package",
        price = formatPrice((basePrice * 1.3).toInt()),
        capacity = "300 - 500 pax",
        imageResId = R.drawable.signaturepackage,
        tags = listOf("Halal catering", "Premium décor", "Custom packages")
    ),
    PackageOption(
        name = "Grand Celebration Package",
        price = formatPrice((basePrice * 1.7).toInt()),
        capacity = "500 - 800 pax",
        imageResId = R.drawable.grandcelebrationpackage,
        tags = listOf("Halal catering", "Luxury ballroom", "Custom packages")
    )
)

private fun photographerPackages(basePrice: Int): List<PackageOption> = listOf(
    PackageOption(
        name = "Basic Shoot Package",
        price = formatPrice(basePrice),
        capacity = "4 hours coverage",
        imageUrl = "https://images.pexels.com/photos/265856/pexels-photo-265856.jpeg",
        tags = listOf("1 photographer", "200+ edited photos", "Online gallery")
    ),
    PackageOption(
        name = "Premium Shoot Package",
        price = formatPrice((basePrice * 1.4).toInt()),
        capacity = "8 hours coverage",
        imageUrl = "https://images.pexels.com/photos/1444443/pexels-photo-1444443.jpeg",
        tags = listOf("2 photographers", "500+ edited photos", "Photo album"),
        isPopular = true
    ),
    PackageOption(
        name = "Deluxe Full-Day Package",
        price = formatPrice((basePrice * 1.9).toInt()),
        capacity = "Full-day coverage",
        imageUrl = "https://images.pexels.com/photos/169198/pexels-photo-169198.jpeg",
        tags = listOf("2 photographers + assistant", "Same-day highlights", "Premium album")
    )
)

private fun makeupPackages(basePrice: Int): List<PackageOption> = listOf(
    PackageOption(
        name = "Bridal Look Package",
        price = formatPrice(basePrice),
        capacity = "1 trial + wedding day",
        imageUrl = "https://images.pexels.com/photos/2065200/pexels-photo-2065200.jpeg",
        tags = listOf("Airbrush makeup", "Hairstyling", "False lashes")
    ),
    PackageOption(
        name = "Full Glam Package",
        price = formatPrice((basePrice * 1.35).toInt()),
        capacity = "2 trials + wedding day",
        imageUrl = "https://images.pexels.com/photos/3065171/pexels-photo-3065171.jpeg",
        tags = listOf("Airbrush makeup", "Touch-up kit", "Hair accessories"),
        isPopular = true
    ),
    PackageOption(
        name = "VIP Bridal Package",
        price = formatPrice((basePrice * 1.8).toInt()),
        capacity = "Unlimited trials",
        imageUrl = "https://images.pexels.com/photos/3985338/pexels-photo-3985338.jpeg",
        tags = listOf("On-site touch-up", "Family makeup included", "Premium products")
    )
)

private fun liveBandPackages(basePrice: Int): List<PackageOption> = listOf(
    PackageOption(
        name = "Duo Acoustic Package",
        price = formatPrice(basePrice),
        capacity = "2 sets, 45 min each",
        imageUrl = "https://images.pexels.com/photos/1105666/pexels-photo-1105666.jpeg",
        tags = listOf("Vocalist + guitarist", "Sound system included", "Custom song list")
    ),
    PackageOption(
        name = "Trio Band Package",
        price = formatPrice((basePrice * 1.4).toInt()),
        capacity = "3 sets, 45 min each",
        imageUrl = "https://images.pexels.com/photos/1387037/pexels-photo-1387037.jpeg",
        tags = listOf("3-piece band", "Full sound system", "MC coordination"),
        isPopular = true
    ),
    PackageOption(
        name = "Full Band Package",
        price = formatPrice((basePrice * 2.0).toInt()),
        capacity = "Full night, 4 sets",
        imageUrl = "https://images.pexels.com/photos/1699161/pexels-photo-1699161.jpeg",
        tags = listOf("5-piece band", "Stage lighting", "Custom repertoire")
    )
)

private fun emceePackages(basePrice: Int): List<PackageOption> = listOf(
    PackageOption(
        name = "Solo Emcee Package",
        price = formatPrice(basePrice),
        capacity = "3 hours hosting",
        imageUrl = "https://images.pexels.com/photos/2608517/pexels-photo-2608517.jpeg",
        tags = listOf("Single language", "Script preparation", "On-site coordination")
    ),
    PackageOption(
        name = "Bilingual Emcee Package",
        price = formatPrice((basePrice * 1.3).toInt()),
        capacity = "5 hours hosting",
        imageUrl = "https://images.pexels.com/photos/2608519/pexels-photo-2608519.jpeg",
        tags = listOf("Bilingual hosting", "Games and activities", "Script preparation"),
        isPopular = true
    ),
    PackageOption(
        name = "Premium Hosting Package",
        price = formatPrice((basePrice * 1.6).toInt()),
        capacity = "Full event coverage",
        imageUrl = "https://images.pexels.com/photos/2608520/pexels-photo-2608520.jpeg",
        tags = listOf("Trilingual hosting", "Rehearsal included", "Custom program design")
    )
)

private fun attirePackages(basePrice: Int): List<PackageOption> = listOf(
    PackageOption(
        name = "Rental Basic Package",
        price = formatPrice(basePrice),
        capacity = "1 outfit rental",
        imageUrl = "https://images.pexels.com/photos/1191710/pexels-photo-1191710.jpeg",
        tags = listOf("1 fitting session", "Basic alterations", "3-day rental")
    ),
    PackageOption(
        name = "Rental Premium Package",
        price = formatPrice((basePrice * 1.3).toInt()),
        capacity = "2 outfit rentals",
        imageUrl = "https://images.pexels.com/photos/1444441/pexels-photo-1444441.jpeg",
        tags = listOf("2 fitting sessions", "Custom alterations", "Accessories included"),
        isPopular = true
    ),
    PackageOption(
        name = "Full Bridal Set Package",
        price = formatPrice((basePrice * 1.7).toInt()),
        capacity = "3+ outfit rentals",
        imageUrl = "https://images.pexels.com/photos/265920/pexels-photo-265920.jpeg",
        tags = listOf("Unlimited fittings", "Custom tailoring", "Full accessories set")
    )
)

private fun decoPackages(basePrice: Int): List<PackageOption> = listOf(
    PackageOption(
        name = "Simple Deco Package",
        price = formatPrice(basePrice),
        capacity = "Up to 100 pax venue",
        imageUrl = "https://images.pexels.com/photos/1444442/pexels-photo-1444442.jpeg",
        tags = listOf("Backdrop setup", "Basic floral", "Table centerpieces")
    ),
    PackageOption(
        name = "Themed Deco Package",
        price = formatPrice((basePrice * 1.4).toInt()),
        capacity = "Up to 300 pax venue",
        imageUrl = "https://images.pexels.com/photos/265947/pexels-photo-265947.jpeg",
        tags = listOf("Custom theme design", "Floral arch", "Ambient lighting"),
        isPopular = true
    ),
    PackageOption(
        name = "Luxury Deco Package",
        price = formatPrice((basePrice * 1.9).toInt()),
        capacity = "Up to 800 pax venue",
        imageUrl = "https://images.pexels.com/photos/169194/pexels-photo-169194.jpeg",
        tags = listOf("Full venue transformation", "Premium florals", "LED lighting design")
    )
)