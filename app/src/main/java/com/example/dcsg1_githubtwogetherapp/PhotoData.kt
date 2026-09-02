package com.example.dcsg1_githubtwogetherapp

data class Photo(
    val id: Int,
    val url: String
)

/**
 * Same pattern as generatePackages() in PackageData.kt:
 * assigns different sample photos based on vendor.category.
 * Once real vendor gallery data exists, swap this function to read from the real
 * data source instead - PhotosTab doesn't need to change.
 */
fun generatePhotos(vendor: Vendor): List<Photo> {
    val urls = when (vendor.category) {
        "Venue" -> listOf(
            "https://images.pexels.com/photos/265947/pexels-photo-265947.jpeg",
            "https://images.pexels.com/photos/1444442/pexels-photo-1444442.jpeg",
            "https://images.pexels.com/photos/169194/pexels-photo-169194.jpeg",
            "https://images.pexels.com/photos/169193/pexels-photo-169193.jpeg"
        )
        "Photographer" -> listOf(
            "https://images.pexels.com/photos/265856/pexels-photo-265856.jpeg",
            "https://images.pexels.com/photos/1444443/pexels-photo-1444443.jpeg",
            "https://images.pexels.com/photos/169198/pexels-photo-169198.jpeg"
        )
        "Makeup" -> listOf(
            "https://images.pexels.com/photos/2065200/pexels-photo-2065200.jpeg",
            "https://images.pexels.com/photos/3065171/pexels-photo-3065171.jpeg",
            "https://images.pexels.com/photos/3985338/pexels-photo-3985338.jpeg"
        )
        else -> listOfNotNull(vendor.imageUrl)
    }
    return urls.mapIndexed { index, url -> Photo(id = index, url = url) }
}