package com.example.dcsg1_githubtwogetherapp

import java.util.Locale
import kotlin.math.abs

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
 *
 * Each category also has 2 different package-naming styles ("name variants").
 * Which one a given vendor gets is picked from a hash of vendor.name, so the same
 * vendor always shows the same names on every open (deterministic), but different
 * vendors in the same category won't all show identical package names.
 */
fun generatePackages(vendor: Vendor): List<PackageOption> {
    val basePrice = vendor.priceFrom.filter { it.isDigit() }.toIntOrNull() ?: 1000
    val variant = abs(vendor.name.hashCode()) % 2

    return when (vendor.category) {
        "Venue" -> venuePackages(basePrice, variant)
        "Photographer" -> photographerPackages(basePrice, variant)
        "Makeup" -> makeupPackages(basePrice, variant)
        "Live Band" -> liveBandPackages(basePrice, variant)
        "Emcee" -> emceePackages(basePrice, variant)
        "Attire" -> attirePackages(basePrice, variant)
        "Deco" -> decoPackages(basePrice, variant)
        else -> venuePackages(basePrice, variant)
    }
}

private val venueNames = listOf(
    listOf("Elegance Package", "Signature Package", "Grand Celebration Package"),
    listOf("Classic Package", "Premium Package", "Royal Package")
)

private fun venuePackages(basePrice: Int, variant: Int): List<PackageOption> {
    val n = venueNames[variant]
    return listOf(
        PackageOption(
            name = n[0],
            price = formatPrice(basePrice),
            capacity = "100 - 300 pax",
            imageUrl = "https://images.pexels.com/photos/265947/pexels-photo-265947.jpeg",
            tags = listOf("Halal catering", "Elegant ballroom", "Custom packages"),
            isPopular = true
        ),
        PackageOption(
            name = n[1],
            price = formatPrice((basePrice * 1.3).toInt()),
            capacity = "300 - 500 pax",
            imageUrl = "https://images.pexels.com/photos/1444442/pexels-photo-1444442.jpeg",
            tags = listOf("Halal catering", "Premium décor", "Custom packages")
        ),
        PackageOption(
            name = n[2],
            price = formatPrice((basePrice * 1.7).toInt()),
            capacity = "500 - 800 pax",
            imageUrl = "https://images.pexels.com/photos/169194/pexels-photo-169194.jpeg",
            tags = listOf("Halal catering", "Luxury ballroom", "Custom packages")
        )
    )
}

private val photographerNames = listOf(
    listOf("Basic Shoot Package", "Premium Shoot Package", "Deluxe Full-Day Package"),
    listOf("Essential Package", "Signature Shoot Package", "All-Day Coverage Package")
)

private fun photographerPackages(basePrice: Int, variant: Int): List<PackageOption> {
    val n = photographerNames[variant]
    return listOf(
        PackageOption(
            name = n[0],
            price = formatPrice(basePrice),
            capacity = "4 hours coverage",
            imageUrl = "https://images.pexels.com/photos/265856/pexels-photo-265856.jpeg",
            tags = listOf("1 photographer", "200+ edited photos", "Online gallery")
        ),
        PackageOption(
            name = n[1],
            price = formatPrice((basePrice * 1.4).toInt()),
            capacity = "8 hours coverage",
            imageUrl = "https://images.pexels.com/photos/1444443/pexels-photo-1444443.jpeg",
            tags = listOf("2 photographers", "500+ edited photos", "Photo album"),
            isPopular = true
        ),
        PackageOption(
            name = n[2],
            price = formatPrice((basePrice * 1.9).toInt()),
            capacity = "Full-day coverage",
            imageUrl = "https://images.pexels.com/photos/169198/pexels-photo-169198.jpeg",
            tags = listOf("2 photographers + assistant", "Same-day highlights", "Premium album")
        )
    )
}

private val makeupNames = listOf(
    listOf("Bridal Look Package", "Full Glam Package", "VIP Bridal Package"),
    listOf("Natural Glow Package", "Signature Bridal Package", "Ultimate Beauty Package")
)

private fun makeupPackages(basePrice: Int, variant: Int): List<PackageOption> {
    val n = makeupNames[variant]
    return listOf(
        PackageOption(
            name = n[0],
            price = formatPrice(basePrice),
            capacity = "1 trial + wedding day",
            imageUrl = "https://images.pexels.com/photos/2065200/pexels-photo-2065200.jpeg",
            tags = listOf("Airbrush makeup", "Hairstyling", "False lashes")
        ),
        PackageOption(
            name = n[1],
            price = formatPrice((basePrice * 1.35).toInt()),
            capacity = "2 trials + wedding day",
            imageUrl = "https://images.pexels.com/photos/3065171/pexels-photo-3065171.jpeg",
            tags = listOf("Airbrush makeup", "Touch-up kit", "Hair accessories"),
            isPopular = true
        ),
        PackageOption(
            name = n[2],
            price = formatPrice((basePrice * 1.8).toInt()),
            capacity = "Unlimited trials",
            imageUrl = "https://images.pexels.com/photos/3985338/pexels-photo-3985338.jpeg",
            tags = listOf("On-site touch-up", "Family makeup included", "Premium products")
        )
    )
}

private val liveBandNames = listOf(
    listOf("Duo Acoustic Package", "Trio Band Package", "Full Band Package"),
    listOf("Intimate Set Package", "Classic Band Package", "Grand Performance Package")
)

private fun liveBandPackages(basePrice: Int, variant: Int): List<PackageOption> {
    val n = liveBandNames[variant]
    return listOf(
        PackageOption(
            name = n[0],
            price = formatPrice(basePrice),
            capacity = "2 sets, 45 min each",
            imageUrl = "https://images.pexels.com/photos/1105666/pexels-photo-1105666.jpeg",
            tags = listOf("Vocalist + guitarist", "Sound system included", "Custom song list")
        ),
        PackageOption(
            name = n[1],
            price = formatPrice((basePrice * 1.4).toInt()),
            capacity = "3 sets, 45 min each",
            imageUrl = "https://images.pexels.com/photos/1387037/pexels-photo-1387037.jpeg",
            tags = listOf("3-piece band", "Full sound system", "MC coordination"),
            isPopular = true
        ),
        PackageOption(
            name = n[2],
            price = formatPrice((basePrice * 2.0).toInt()),
            capacity = "Full night, 4 sets",
            imageUrl = "https://images.pexels.com/photos/1699161/pexels-photo-1699161.jpeg",
            tags = listOf("5-piece band", "Stage lighting", "Custom repertoire")
        )
    )
}

private val emceeNames = listOf(
    listOf("Solo Emcee Package", "Bilingual Emcee Package", "Premium Hosting Package"),
    listOf("Standard Hosting Package", "Dual Language Package", "Full Programme Package")
)

private fun emceePackages(basePrice: Int, variant: Int): List<PackageOption> {
    val n = emceeNames[variant]
    return listOf(
        PackageOption(
            name = n[0],
            price = formatPrice(basePrice),
            capacity = "3 hours hosting",
            imageUrl = "https://images.pexels.com/photos/2608517/pexels-photo-2608517.jpeg",
            tags = listOf("Single language", "Script preparation", "On-site coordination")
        ),
        PackageOption(
            name = n[1],
            price = formatPrice((basePrice * 1.3).toInt()),
            capacity = "5 hours hosting",
            imageUrl = "https://images.pexels.com/photos/2608519/pexels-photo-2608519.jpeg",
            tags = listOf("Bilingual hosting", "Games and activities", "Script preparation"),
            isPopular = true
        ),
        PackageOption(
            name = n[2],
            price = formatPrice((basePrice * 1.6).toInt()),
            capacity = "Full event coverage",
            imageUrl = "https://images.pexels.com/photos/2608520/pexels-photo-2608520.jpeg",
            tags = listOf("Trilingual hosting", "Rehearsal included", "Custom program design")
        )
    )
}

private val attireNames = listOf(
    listOf("Rental Basic Package", "Rental Premium Package", "Full Bridal Set Package"),
    listOf("Starter Package", "Boutique Package", "Complete Ensemble Package")
)

private fun attirePackages(basePrice: Int, variant: Int): List<PackageOption> {
    val n = attireNames[variant]
    return listOf(
        PackageOption(
            name = n[0],
            price = formatPrice(basePrice),
            capacity = "1 outfit rental",
            imageUrl = "https://images.pexels.com/photos/1191710/pexels-photo-1191710.jpeg",
            tags = listOf("1 fitting session", "Basic alterations", "3-day rental")
        ),
        PackageOption(
            name = n[1],
            price = formatPrice((basePrice * 1.3).toInt()),
            capacity = "2 outfit rentals",
            imageUrl = "https://images.pexels.com/photos/1444441/pexels-photo-1444441.jpeg",
            tags = listOf("2 fitting sessions", "Custom alterations", "Accessories included"),
            isPopular = true
        ),
        PackageOption(
            name = n[2],
            price = formatPrice((basePrice * 1.7).toInt()),
            capacity = "3+ outfit rentals",
            imageUrl = "https://images.pexels.com/photos/265920/pexels-photo-265920.jpeg",
            tags = listOf("Unlimited fittings", "Custom tailoring", "Full accessories set")
        )
    )
}

private val decoNames = listOf(
    listOf("Simple Deco Package", "Themed Deco Package", "Luxury Deco Package"),
    listOf("Essential Styling Package", "Signature Deco Package", "Grand Transformation Package")
)

private fun decoPackages(basePrice: Int, variant: Int): List<PackageOption> {
    val n = decoNames[variant]
    return listOf(
        PackageOption(
            name = n[0],
            price = formatPrice(basePrice),
            capacity = "Up to 100 pax venue",
            imageUrl = "https://images.pexels.com/photos/1444442/pexels-photo-1444442.jpeg",
            tags = listOf("Backdrop setup", "Basic floral", "Table centerpieces")
        ),
        PackageOption(
            name = n[1],
            price = formatPrice((basePrice * 1.4).toInt()),
            capacity = "Up to 300 pax venue",
            imageUrl = "https://images.pexels.com/photos/265947/pexels-photo-265947.jpeg",
            tags = listOf("Custom theme design", "Floral arch", "Ambient lighting"),
            isPopular = true
        ),
        PackageOption(
            name = n[2],
            price = formatPrice((basePrice * 1.9).toInt()),
            capacity = "Up to 800 pax venue",
            imageUrl = "https://images.pexels.com/photos/169194/pexels-photo-169194.jpeg",
            tags = listOf("Full venue transformation", "Premium florals", "LED lighting design")
        )
    )
}