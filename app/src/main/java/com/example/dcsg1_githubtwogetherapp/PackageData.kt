package com.example.dcsg1_githubtwogetherapp

import android.content.Context
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

fun generatePackages(vendor: Vendor, context: Context): List<PackageOption> {
    val basePrice = vendor.priceFrom.filter { it.isDigit() }.toIntOrNull() ?: 1000
    val variant = abs(vendor.name.hashCode()) % 2

    return when (vendor.category) {
        "Venue" -> venuePackages(basePrice, variant, context)
        "Photographer" -> photographerPackages(basePrice, variant, context)
        "Makeup" -> makeupPackages(basePrice, variant, context)
        "Live Band" -> liveBandPackages(basePrice, variant, context)
        "Emcee" -> emceePackages(basePrice, variant, context)
        "Attire" -> attirePackages(basePrice, variant, context)
        "Deco" -> decoPackages(basePrice, variant, context)
        else -> venuePackages(basePrice, variant, context)
    }
}

private fun venuePackages(basePrice: Int, variant: Int, ctx: Context): List<PackageOption> {
    val n = if (variant == 0) listOf(R.string.pkg_name_venue_0_0, R.string.pkg_name_venue_0_1, R.string.pkg_name_venue_0_2)
    else listOf(R.string.pkg_name_venue_1_0, R.string.pkg_name_venue_1_1, R.string.pkg_name_venue_1_2)
    return listOf(
        PackageOption(ctx.getString(n[0]), formatPrice(basePrice), ctx.getString(R.string.pkg_cap_venue_0),
            imageUrl = "https://images.pexels.com/photos/265947/pexels-photo-265947.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_venue_0_0), ctx.getString(R.string.pkg_tag_venue_0_1), ctx.getString(R.string.pkg_tag_venue_0_2)),
            isPopular = true),
        PackageOption(ctx.getString(n[1]), formatPrice((basePrice * 1.3).toInt()), ctx.getString(R.string.pkg_cap_venue_1),
            imageUrl = "https://images.pexels.com/photos/1444442/pexels-photo-1444442.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_venue_1_0), ctx.getString(R.string.pkg_tag_venue_1_1), ctx.getString(R.string.pkg_tag_venue_1_2))),
        PackageOption(ctx.getString(n[2]), formatPrice((basePrice * 1.7).toInt()), ctx.getString(R.string.pkg_cap_venue_2),
            imageUrl = "https://images.pexels.com/photos/169194/pexels-photo-169194.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_venue_2_0), ctx.getString(R.string.pkg_tag_venue_2_1), ctx.getString(R.string.pkg_tag_venue_2_2)))
    )
}

private fun photographerPackages(basePrice: Int, variant: Int, ctx: Context): List<PackageOption> {
    val n = if (variant == 0) listOf(R.string.pkg_name_photographer_0_0, R.string.pkg_name_photographer_0_1, R.string.pkg_name_photographer_0_2)
    else listOf(R.string.pkg_name_photographer_1_0, R.string.pkg_name_photographer_1_1, R.string.pkg_name_photographer_1_2)
    return listOf(
        PackageOption(ctx.getString(n[0]), formatPrice(basePrice), ctx.getString(R.string.pkg_cap_photographer_0),
            imageUrl = "https://images.pexels.com/photos/265856/pexels-photo-265856.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_photographer_0_0), ctx.getString(R.string.pkg_tag_photographer_0_1), ctx.getString(R.string.pkg_tag_photographer_0_2))),
        PackageOption(ctx.getString(n[1]), formatPrice((basePrice * 1.4).toInt()), ctx.getString(R.string.pkg_cap_photographer_1),
            imageUrl = "https://images.pexels.com/photos/1444443/pexels-photo-1444443.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_photographer_1_0), ctx.getString(R.string.pkg_tag_photographer_1_1), ctx.getString(R.string.pkg_tag_photographer_1_2)),
            isPopular = true),
        PackageOption(ctx.getString(n[2]), formatPrice((basePrice * 1.9).toInt()), ctx.getString(R.string.pkg_cap_photographer_2),
            imageUrl = "https://images.pexels.com/photos/169198/pexels-photo-169198.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_photographer_2_0), ctx.getString(R.string.pkg_tag_photographer_2_1), ctx.getString(R.string.pkg_tag_photographer_2_2)))
    )
}

private fun makeupPackages(basePrice: Int, variant: Int, ctx: Context): List<PackageOption> {
    val n = if (variant == 0) listOf(R.string.pkg_name_makeup_0_0, R.string.pkg_name_makeup_0_1, R.string.pkg_name_makeup_0_2)
    else listOf(R.string.pkg_name_makeup_1_0, R.string.pkg_name_makeup_1_1, R.string.pkg_name_makeup_1_2)
    return listOf(
        PackageOption(ctx.getString(n[0]), formatPrice(basePrice), ctx.getString(R.string.pkg_cap_makeup_0),
            imageUrl = "https://images.pexels.com/photos/2065200/pexels-photo-2065200.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_makeup_0_0), ctx.getString(R.string.pkg_tag_makeup_0_1), ctx.getString(R.string.pkg_tag_makeup_0_2))),
        PackageOption(ctx.getString(n[1]), formatPrice((basePrice * 1.35).toInt()), ctx.getString(R.string.pkg_cap_makeup_1),
            imageUrl = "https://images.pexels.com/photos/3065171/pexels-photo-3065171.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_makeup_1_0), ctx.getString(R.string.pkg_tag_makeup_1_1), ctx.getString(R.string.pkg_tag_makeup_1_2)),
            isPopular = true),
        PackageOption(ctx.getString(n[2]), formatPrice((basePrice * 1.8).toInt()), ctx.getString(R.string.pkg_cap_makeup_2),
            imageUrl = "https://images.pexels.com/photos/3985338/pexels-photo-3985338.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_makeup_2_0), ctx.getString(R.string.pkg_tag_makeup_2_1), ctx.getString(R.string.pkg_tag_makeup_2_2)))
    )
}

private fun liveBandPackages(basePrice: Int, variant: Int, ctx: Context): List<PackageOption> {
    val n = if (variant == 0) listOf(R.string.pkg_name_liveband_0_0, R.string.pkg_name_liveband_0_1, R.string.pkg_name_liveband_0_2)
    else listOf(R.string.pkg_name_liveband_1_0, R.string.pkg_name_liveband_1_1, R.string.pkg_name_liveband_1_2)
    return listOf(
        PackageOption(ctx.getString(n[0]), formatPrice(basePrice), ctx.getString(R.string.pkg_cap_liveband_0),
            imageUrl = "https://images.pexels.com/photos/1105666/pexels-photo-1105666.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_liveband_0_0), ctx.getString(R.string.pkg_tag_liveband_0_1), ctx.getString(R.string.pkg_tag_liveband_0_2))),
        PackageOption(ctx.getString(n[1]), formatPrice((basePrice * 1.4).toInt()), ctx.getString(R.string.pkg_cap_liveband_1),
            imageUrl = "https://images.pexels.com/photos/1387037/pexels-photo-1387037.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_liveband_1_0), ctx.getString(R.string.pkg_tag_liveband_1_1), ctx.getString(R.string.pkg_tag_liveband_1_2)),
            isPopular = true),
        PackageOption(ctx.getString(n[2]), formatPrice((basePrice * 2.0).toInt()), ctx.getString(R.string.pkg_cap_liveband_2),
            imageUrl = "https://images.pexels.com/photos/1699161/pexels-photo-1699161.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_liveband_2_0), ctx.getString(R.string.pkg_tag_liveband_2_1), ctx.getString(R.string.pkg_tag_liveband_2_2)))
    )
}

private fun emceePackages(basePrice: Int, variant: Int, ctx: Context): List<PackageOption> {
    val n = if (variant == 0) listOf(R.string.pkg_name_emcee_0_0, R.string.pkg_name_emcee_0_1, R.string.pkg_name_emcee_0_2)
    else listOf(R.string.pkg_name_emcee_1_0, R.string.pkg_name_emcee_1_1, R.string.pkg_name_emcee_1_2)
    return listOf(
        PackageOption(ctx.getString(n[0]), formatPrice(basePrice), ctx.getString(R.string.pkg_cap_emcee_0),
            imageUrl = "https://images.pexels.com/photos/2608517/pexels-photo-2608517.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_emcee_0_0), ctx.getString(R.string.pkg_tag_emcee_0_1), ctx.getString(R.string.pkg_tag_emcee_0_2))),
        PackageOption(ctx.getString(n[1]), formatPrice((basePrice * 1.3).toInt()), ctx.getString(R.string.pkg_cap_emcee_1),
            imageUrl = "https://images.pexels.com/photos/2608519/pexels-photo-2608519.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_emcee_1_0), ctx.getString(R.string.pkg_tag_emcee_1_1), ctx.getString(R.string.pkg_tag_emcee_1_2)),
            isPopular = true),
        PackageOption(ctx.getString(n[2]), formatPrice((basePrice * 1.6).toInt()), ctx.getString(R.string.pkg_cap_emcee_2),
            imageUrl = "https://images.pexels.com/photos/2608520/pexels-photo-2608520.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_emcee_2_0), ctx.getString(R.string.pkg_tag_emcee_2_1), ctx.getString(R.string.pkg_tag_emcee_2_2)))
    )
}

private fun attirePackages(basePrice: Int, variant: Int, ctx: Context): List<PackageOption> {
    val n = if (variant == 0) listOf(R.string.pkg_name_attire_0_0, R.string.pkg_name_attire_0_1, R.string.pkg_name_attire_0_2)
    else listOf(R.string.pkg_name_attire_1_0, R.string.pkg_name_attire_1_1, R.string.pkg_name_attire_1_2)
    return listOf(
        PackageOption(ctx.getString(n[0]), formatPrice(basePrice), ctx.getString(R.string.pkg_cap_attire_0),
            imageUrl = "https://images.pexels.com/photos/1191710/pexels-photo-1191710.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_attire_0_0), ctx.getString(R.string.pkg_tag_attire_0_1), ctx.getString(R.string.pkg_tag_attire_0_2))),
        PackageOption(ctx.getString(n[1]), formatPrice((basePrice * 1.3).toInt()), ctx.getString(R.string.pkg_cap_attire_1),
            imageUrl = "https://images.pexels.com/photos/1444441/pexels-photo-1444441.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_attire_1_0), ctx.getString(R.string.pkg_tag_attire_1_1), ctx.getString(R.string.pkg_tag_attire_1_2)),
            isPopular = true),
        PackageOption(ctx.getString(n[2]), formatPrice((basePrice * 1.7).toInt()), ctx.getString(R.string.pkg_cap_attire_2),
            imageUrl = "https://images.pexels.com/photos/265920/pexels-photo-265920.jpeg",
            tags = listOf(ctx.getString(R.string.pkg_tag_attire_2_0), ctx.getString(R.string.pkg_tag_attire_2_1), ctx.getString(R.string.pkg_tag_attire_2_2)))
    )
}

private fun decoPackages(basePrice: Int, variant: Int, ctx: Context): List<PackageOption> {
    val n = if (variant == 0) listOf(R.string.pkg_name_deco_0_0, R.string.pkg_name_deco_0_1, R.string.pkg_name_deco_0_2)
    else listOf(R.string.pkg_name_deco_1_0, R.string.pkg_name_deco_1_1, R.string.pkg_name_deco_1_2)
    return listOf(
        PackageOption(ctx.getString(n[0]), formatPrice(basePrice), ctx.getString(R.string.pkg_cap_deco_0),
            imageResId = R.drawable.elegancepackage,
            tags = listOf(ctx.getString(R.string.pkg_tag_deco_0_0), ctx.getString(R.string.pkg_tag_deco_0_1), ctx.getString(R.string.pkg_tag_deco_0_2))),
        PackageOption(ctx.getString(n[1]), formatPrice((basePrice * 1.4).toInt()), ctx.getString(R.string.pkg_cap_deco_1),
            imageResId = R.drawable.signaturepackage,
            tags = listOf(ctx.getString(R.string.pkg_tag_deco_1_0), ctx.getString(R.string.pkg_tag_deco_1_1), ctx.getString(R.string.pkg_tag_deco_1_2)),
            isPopular = true),
        PackageOption(ctx.getString(n[2]), formatPrice((basePrice * 1.9).toInt()), ctx.getString(R.string.pkg_cap_deco_2),
            imageResId = R.drawable.grandcelebrationpackage,
            tags = listOf(ctx.getString(R.string.pkg_tag_deco_2_0), ctx.getString(R.string.pkg_tag_deco_2_1), ctx.getString(R.string.pkg_tag_deco_2_2)))
    )
}