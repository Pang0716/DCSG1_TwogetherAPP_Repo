package com.example.dcsg1_githubtwogetherapp

import kotlinx.serialization.Serializable
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@Serializable
data class Review(
    val id: Int,
    val reviewerName: String,
    val rating: Int,
    val comment: String
)

/** A shared pool of reviewer names, mixing common Malaysian names (Malay/Chinese/Indian). */
private val reviewerNamePool = listOf(
    "Aisha Rahman", "Daniel Tan", "Mei Ling Chong", "Farid Hassan",
    "Nurul Izzah", "Wei Jian Lim", "Kavitha A/P Raj", "Amirul Hakim",
    "Siti Nurhaliza", "Jason Wong", "Priya Sundaram", "Muhammad Hafiz",
    "Chloe Yap", "Rajesh Kumar", "Aina Sofea", "Kenneth Lee",
    "Nabila Yusof", "Amanda Chin", "Suresh Nair", "Firdaus Zainal",
    "Grace Ooi", "Haziq Danial", "Deepa Krishnan", "Bryan Teoh",
    "Farah Adilah", "Vincent Ho", "Anitha Selvam", "Iskandar Zulkarnain"
)

/** A pool of review comments at different rating levels, generic enough to fit any vendor. */
private val fiveStarComments = listOf(
    "Amazing service, everything went smoothly on our big day! Highly recommend %s.",
    "Worth every ringgit. The staff were professional from start to finish.",
    "Couldn't have asked for a better experience. %s exceeded our expectations.",
    "Everything was exactly as promised, if not better. Thank you so much!",
    "Absolutely flawless from booking to the actual event. Will recommend to friends.",
    "The team at %s really went above and beyond for us."
)
private val fourStarComments = listOf(
    "Great experience overall. Minor delays on the day, but the team was very helpful.",
    "Responsive and easy to communicate with throughout the whole planning process.",
    "Really solid choice for our wedding. A couple of small hiccups but nothing major.",
    "Good value and friendly staff. Would consider %s again for other events.",
    "Pretty happy with the outcome, just wish communication was a bit faster."
)
private val threeStarComments = listOf(
    "Decent overall but there's room for improvement in communication.",
    "It was okay - met our basic expectations but nothing extraordinary.",
    "Average experience. Some things could have been better coordinated."
)

private fun java_hashcode(s: String): Int {
    var h = 0
    for (c in s) {
        h = 31 * h + c.code
    }
    return h
}

fun generateReviews(vendor: Vendor): List<Review> {
    val seed = abs(java_hashcode(vendor.name))
    val reviewCount = min(6, max(2, vendor.reviewCount / 40 + 2))

    val reviews = mutableListOf<Review>()
    for (i in 0 until reviewCount) {
        val nameIndex = (seed + i * 7) % reviewerNamePool.size
        val reviewerName = reviewerNamePool[nameIndex]

        // Mostly reflect the vendor's overall rating, with a bit of natural variation
        val rating = when {
            vendor.rating >= 4.7 -> if ((seed + i) % 5 == 0) 4 else 5
            vendor.rating >= 4.3 -> if ((seed + i) % 3 == 0) 5 else 4
            else -> if ((seed + i) % 2 == 0) 4 else 3
        }

        val commentPool = when (rating) {
            5 -> fiveStarComments
            4 -> fourStarComments
            else -> threeStarComments
        }
        val commentTemplate = commentPool[(seed + i * 3) % commentPool.size]
        val comment = if (commentTemplate.contains("%s")) {
            commentTemplate.format(vendor.name)
        } else {
            commentTemplate
        }

        reviews.add(
            Review(
                id = i + 1,
                reviewerName = reviewerName,
                rating = rating,
                comment = comment
            )
        )
    }
    return reviews
}