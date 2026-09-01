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
    val highlights: String = ""
)


val sampleVendors = PG