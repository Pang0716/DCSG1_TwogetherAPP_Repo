package com.example.dcsg1_githubtwogetherapp

import kotlin.math.abs

/**
 * Same pattern as generatePackages()/generatePhotos()/generateReviews() - branches by
 * vendor.category. On top of that, each category now has a few different phrasing
 * variants instead of one fixed sentence, so vendors in the same category don't all
 * show word-for-word identical About text.
 *
 * The variant is picked using a hash of vendor.name rather than random() so it's
 * deterministic - the same vendor always shows the same description every time the
 * screen is opened, it doesn't change on every recomposition/reload.
 */
fun generateAboutDescription(vendor: Vendor): String {
    val templates: List<String> = when (vendor.category) {
        "Venue" -> listOf(
            "A luxurious wedding venue in the heart of ${vendor.locationArea}. We provide elegant settings, halal catering and customizable packages to make your big day unforgettable.",
            "An elegant event space located in ${vendor.locationArea}, offering spacious ballrooms and full event support to bring your wedding vision to life.",
            "A premier wedding destination in ${vendor.locationArea}, known for its refined interiors and dedicated event team ensuring a seamless celebration."
        )
        "Photographer" -> listOf(
            "A dedicated wedding photography team based in ${vendor.locationArea}, capturing every candid and posed moment in a timeless, elegant style.",
            "An experienced photography studio in ${vendor.locationArea}, specialising in storytelling through beautifully composed wedding imagery.",
            "A creative photography team based in ${vendor.locationArea}, blending documentary and fine-art styles to preserve your special day."
        )
        "Makeup" -> listOf(
            "A professional bridal makeup studio in ${vendor.locationArea}, specialising in flawless, long-lasting looks tailored to your wedding theme.",
            "An experienced bridal beauty team in ${vendor.locationArea}, offering personalised makeup and hairstyling for brides and their entourage.",
            "A trusted makeup studio in ${vendor.locationArea}, known for natural, radiant bridal looks that last through every celebration."
        )
        "Live Band" -> listOf(
            "A live wedding band based in ${vendor.locationArea}, bringing energy and elegance to your reception with a repertoire tailored to your celebration.",
            "A versatile live music ensemble in ${vendor.locationArea}, performing everything from romantic ballads to upbeat dance hits for your big day.",
            "An experienced wedding band in ${vendor.locationArea}, delivering polished live performances that keep guests on the dance floor all night."
        )
        "Emcee" -> listOf(
            "Experienced wedding emcees based in ${vendor.locationArea}, keeping your programme running smoothly while engaging your guests throughout the event.",
            "A professional hosting team in ${vendor.locationArea}, blending warmth and humour to guide your wedding programme from start to finish.",
            "Skilled wedding emcees in ${vendor.locationArea}, known for confident bilingual hosting and seamless event pacing."
        )
        "Attire" -> listOf(
            "A bridal and wedding attire specialist in ${vendor.locationArea}, offering rentals and tailoring for the perfect look on your big day.",
            "A boutique wedding attire studio in ${vendor.locationArea}, providing custom fittings and a curated selection of bridal and groom wear.",
            "An established attire rental house in ${vendor.locationArea}, dedicated to helping couples find their perfect wedding day look."
        )
        "Deco" -> listOf(
            "A wedding decoration and styling team in ${vendor.locationArea}, transforming venues into unforgettable settings tailored to your theme.",
            "A creative event styling studio in ${vendor.locationArea}, specialising in floral design and bespoke decor for every wedding theme.",
            "An experienced wedding deco team in ${vendor.locationArea}, known for elegant, detail-driven setups that elevate any venue."
        )
        else -> listOf(
            "A trusted wedding vendor in ${vendor.locationArea}, dedicated to making your big day unforgettable."
        )
    }
    val variantIndex = abs(vendor.name.hashCode()) % templates.size
    return templates[variantIndex]
}