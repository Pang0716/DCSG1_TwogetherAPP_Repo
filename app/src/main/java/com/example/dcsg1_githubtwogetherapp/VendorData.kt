package com.example.dcsg1_githubtwogetherapp

data class Vendor(
    val name: String,
    val category: String,
    val rating: Double,
    val reviewCount: Int,
    val priceFrom: String,
    val imageUrl: String?,
    val imageResId: Int? = null,
    val locationArea: String,
    val locationState: String,
    val capacity: String = "",
    val highlights: String = "",
    val photoResIds: List<Int> = emptyList(),
    val photoUrls: List<String> = emptyList(),
    val sampleReviews: List<Review> = emptyList()
)

val sampleVendors = PG +
        kualaLumpurVendors +
        selangorVendors +
        johorVendors +
        melakaVendors +
        perakVendors +
        negeriSembilanVendors +
        kedahVendors +
        pahangVendors +
        terengganuVendors +
        kelantanVendors +
        perlisVendors