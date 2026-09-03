package com.example.dcsg1_githubtwogetherapp

data class Photo(
    val id: Int,
    val url: String? = null,
    val resId: Int? = null
)

fun generatePhotos(vendor: Vendor): List<Photo> {
    if (vendor.photoResIds.isNotEmpty()) {
        return vendor.photoResIds.mapIndexed { index, resId -> Photo(id = index, resId = resId) }
    }
    return vendor.photoUrls.mapIndexed { index, url -> Photo(id = index, url = url) }
}