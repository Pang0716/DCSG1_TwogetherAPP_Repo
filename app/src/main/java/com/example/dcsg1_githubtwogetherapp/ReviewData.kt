package com.example.dcsg1_githubtwogetherapp

import kotlinx.serialization.Serializable

// @Serializable, same as Note in Practical 8 - this annotation lets Json.encodeToString /
// Json.decodeFromString know how to turn this data class into JSON text and back
@Serializable
data class Review(
    val id: Int,
    val reviewerName: String,
    val rating: Int,
    val comment: String
)

/**
 * Sample data generator, same pattern as generatePackages() / generatePhotos().
 * Once real review data exists, swap this function out - ReviewRow doesn't need to change.
 */
fun generateReviews(vendor: Vendor): List<Review> = listOf(
    Review(
        id = 1,
        reviewerName = "Aisha Rahman",
        rating = 5,
        comment = "Amazing service, everything went smoothly on our big day! Highly recommend ${vendor.name}."
    ),
    Review(
        id = 2,
        reviewerName = "Daniel Tan",
        rating = 4,
        comment = "Great experience overall. Minor delays on the day, but the team was very helpful."
    ),
    Review(
        id = 3,
        reviewerName = "Mei Ling Chong",
        rating = 5,
        comment = "Worth every ringgit. The staff were professional from start to finish."
    ),
    Review(
        id = 4,
        reviewerName = "Farid Hassan",
        rating = 4,
        comment = "Responsive and easy to communicate with throughout the whole planning process."
    )
)