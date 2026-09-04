package com.example.dcsg1_githubtwogetherapp

import kotlinx.serialization.Serializable
import kotlin.math.abs

@Serializable
data class Review(
    val id: Int,
    val reviewerName: String,
    val rating: Int,
    val comment: String
)

/**
 * Malaysian reviewer names
 */
private val reviewerNamePool = listOf(
    "Aisha Rahman",
    "Daniel Tan",
    "Mei Ling Chong",
    "Farid Hassan",
    "Nurul Izzah",
    "Wei Jian Lim",
    "Kavitha A/P Raj",
    "Amirul Hakim",
    "Siti Nurhaliza",
    "Jason Wong",
    "Priya Sundaram",
    "Muhammad Hafiz",
    "Chloe Yap",
    "Rajesh Kumar",
    "Aina Sofea",
    "Kenneth Lee",
    "Nabila Yusof",
    "Amanda Chin",
    "Suresh Nair",
    "Firdaus Zainal",
    "Grace Ooi",
    "Haziq Danial",
    "Deepa Krishnan",
    "Bryan Teoh",
    "Farah Adilah",
    "Vincent Ho",
    "Anitha Selvam",
    "Iskandar Zulkarnain"
)

/**
 * Generate a Java-style hash code for the vendor name.
 * This makes different vendors generate different review combinations.
 */
private fun java_hashcode(s: String): Int {
    var h = 0

    for (c in s) {
        h = 31 * h + c.code
    }

    return h
}

/**
 * Generate exactly 20 reviews for each vendor.
 */
fun generateReviews(vendor: Vendor): List<Review> {

    val seed = abs(java_hashcode(vendor.name))

    val reviews = mutableListOf<Review>()

    for (i in 0 until 20) {

        // Select reviewer
        val nameIndex =
            (seed + i * 7) % reviewerNamePool.size

        val reviewerName =
            reviewerNamePool[nameIndex]

        // Generate rating based on vendor rating
        val rating = when {

            vendor.rating >= 4.8 -> {
                when ((seed + i) % 10) {
                    0, 1 -> 4
                    else -> 5
                }
            }

            vendor.rating >= 4.6 -> {
                when ((seed + i) % 10) {
                    0, 1, 2 -> 4
                    else -> 5
                }
            }

            vendor.rating >= 4.3 -> {
                when ((seed + i) % 10) {
                    0, 1, 2, 3 -> 4
                    else -> 5
                }
            }

            else -> {
                when ((seed + i) % 10) {
                    0, 1, 2 -> 3
                    else -> 4
                }
            }
        }

        val comment = generateVendorComment(
            vendor = vendor,
            rating = rating,
            index = i
        )

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

/**
 * Generate comments according to vendor category.
 */
private fun generateVendorComment(
    vendor: Vendor,
    rating: Int,
    index: Int
): String {

    return when (vendor.category) {

        // =========================
        // VENUE
        // =========================

        "Venue" -> {

            val comments = when (rating) {

                5 -> listOf(
                    "Beautiful venue and everything was well organised. The ballroom looked amazing!",
                    "The venue was perfect for our wedding. The staff were helpful and professional.",
                    "Loved the atmosphere and decoration of the venue. Our guests enjoyed it very much.",
                    "The ballroom was spacious and elegant. Everything went smoothly on our wedding day.",
                    "Excellent venue with great service. The food and overall setup were impressive.",
                    "The location was convenient and the wedding hall looked beautiful in photos.",
                    "Very happy with our choice. The staff helped us throughout the planning process.",
                    "The venue exceeded our expectations. Our wedding dinner turned out beautifully.",
                    "Beautiful setting and friendly staff. Highly recommended for couples planning a wedding.",
                    "Everything was ready on time and the venue looked exactly like we imagined."
                )

                4 -> listOf(
                    "Overall a great venue. There were a few small delays but everything was handled well.",
                    "Nice venue with good facilities. Communication could have been slightly faster.",
                    "The ballroom was beautiful and the staff were helpful. Overall a good experience.",
                    "Good choice for a wedding. A few small things could have been coordinated better.",
                    "We were happy with the venue and the service provided on the day."
                )

                else -> listOf(
                    "The venue was decent but there were some areas that could be improved.",
                    "Overall okay, although communication during planning could have been better.",
                    "The venue met our basic expectations but there were a few small issues."
                )
            }

            comments[index % comments.size]
        }

        // =========================
        // PHOTOGRAPHER
        // =========================

        "Photographer" -> {

            val comments = when (rating) {

                5 -> listOf(
                    "The photos captured all our important moments beautifully.",
                    "Loved the candid shots! The photographer made us feel very comfortable.",
                    "The editing was beautiful and the photos looked natural and elegant.",
                    "Very professional photographer. We received so many beautiful wedding photos.",
                    "The photographer was friendly and knew exactly how to capture the right moments.",
                    "Our wedding album turned out amazing. We are very happy with the results.",
                    "The photos looked cinematic and the colours were beautiful.",
                    "Great service from start to finish. The sneak peek photos were lovely.",
                    "They captured so many small moments that we didn't even notice during the wedding.",
                    "Highly recommended for couples who want natural and memorable wedding photos."
                )

                4 -> listOf(
                    "The photos were beautiful overall. A few shots could have been better.",
                    "Good photographer with a friendly attitude. Communication was mostly smooth.",
                    "We liked the final photos and the editing style was quite nice.",
                    "Overall a good experience. Delivery took slightly longer than expected.",
                    "The photographer did a good job capturing the main moments of our wedding."
                )

                else -> listOf(
                    "The photos were okay but we expected a little more variety.",
                    "Overall decent, although some of the editing could be improved.",
                    "The service was acceptable but communication could have been clearer."
                )
            }

            comments[index % comments.size]
        }

        // =========================
        // MAKEUP
        // =========================

        "Makeup" -> {

            val comments = when (rating) {

                5 -> listOf(
                    "My makeup lasted throughout the whole wedding and looked beautiful in photos.",
                    "Loved the makeup and hairstyle! I felt confident the entire day.",
                    "The makeup artist understood exactly the look I wanted.",
                    "Very professional and friendly. The final makeup looked natural and elegant.",
                    "The makeup stayed fresh even after many hours of wedding activities.",
                    "Loved the attention to detail, especially the eye makeup and hairstyle.",
                    "The trial session helped us choose the perfect look for the wedding.",
                    "My makeup looked amazing both in person and in photos.",
                    "The artist was punctual and prepared everything before the ceremony.",
                    "Would definitely recommend this makeup artist to other brides."
                )

                4 -> listOf(
                    "The makeup looked good overall, although I would have preferred a slightly different style.",
                    "Friendly makeup artist and good service. The makeup lasted quite well.",
                    "Overall happy with the result. Communication could have been a little faster.",
                    "The hairstyle was beautiful and the makeup was suitable for the event.",
                    "Good experience and reasonable service for the price."
                )

                else -> listOf(
                    "The makeup was okay but the final look was slightly different from what I expected.",
                    "Decent service but there is some room for improvement.",
                    "The overall result was acceptable, although some details could have been better."
                )
            }

            comments[index % comments.size]
        }

        // =========================
        // LIVE BAND
        // =========================

        "Live Band" -> {

            val comments = when (rating) {

                5 -> listOf(
                    "The band was fantastic! Our guests loved the live performance.",
                    "Great song selection and the performance really made the reception more lively.",
                    "The musicians were talented and very professional throughout the event.",
                    "Everyone enjoyed the music and the sound quality was excellent.",
                    "The band created such a fun atmosphere during our wedding dinner.",
                    "Loved how they interacted with the guests and kept everyone entertained.",
                    "The song choices were perfect for our wedding. Highly recommended!",
                    "Professional team with great energy. Our guests kept talking about them.",
                    "The live performance made our wedding reception unforgettable.",
                    "Amazing performance and good sound system. Would definitely book them again."
                )

                4 -> listOf(
                    "Good performance overall and the guests enjoyed the music.",
                    "The band was professional and had a good selection of songs.",
                    "Really enjoyable performance. There were a few small timing issues.",
                    "Good energy and friendly musicians. Overall we were satisfied.",
                    "The sound was good and most of the song choices suited our event."
                )

                else -> listOf(
                    "The performance was okay but the song selection could have been better.",
                    "Decent performance, although the sound could be improved.",
                    "Overall acceptable but there were a few issues during the performance."
                )
            }

            comments[index % comments.size]
        }

        // =========================
        // EMCEE
        // =========================

        "Emcee" -> {

            val comments = when (rating) {

                5 -> listOf(
                    "Our emcee kept the whole event lively and well organised.",
                    "Very confident and professional host. Our guests enjoyed the games.",
                    "The bilingual hosting was very helpful for our guests.",
                    "The emcee handled the schedule smoothly and kept everything on time.",
                    "Great personality and very easy to work with during our wedding.",
                    "The hosting style was energetic without being too overwhelming.",
                    "Our guests loved the interaction and fun activities.",
                    "The emcee prepared a good script and understood our wedding flow.",
                    "Very professional from rehearsal until the actual event.",
                    "Would definitely recommend this emcee for a wedding reception."
                )

                4 -> listOf(
                    "Good hosting overall. There were a few small timing issues.",
                    "Friendly and professional emcee. The event went smoothly.",
                    "The hosting was enjoyable and our guests responded well.",
                    "Overall satisfied with the service and communication.",
                    "Good energy and clear communication throughout the event."
                )

                else -> listOf(
                    "The hosting was okay but could have been more engaging.",
                    "Decent service but the event flow could have been handled better.",
                    "Overall acceptable, although communication could be improved."
                )
            }

            comments[index % comments.size]
        }

        // =========================
        // ATTIRE
        // =========================

        "Attire" -> {

            val comments = when (rating) {

                5 -> listOf(
                    "The wedding outfit was beautiful and fitted perfectly.",
                    "Loved the design and the fitting process was very smooth.",
                    "The staff helped us choose outfits that suited us really well.",
                    "Beautiful traditional and modern designs. Very happy with our choice.",
                    "The alterations were done perfectly and the outfit was comfortable.",
                    "Great selection of wedding outfits and very friendly staff.",
                    "The details and embroidery were beautiful in person.",
                    "The fitting sessions were convenient and the staff were patient.",
                    "Our outfits looked amazing in the wedding photos.",
                    "Excellent service from fitting to the final collection."
                )

                4 -> listOf(
                    "Good selection of outfits and the fitting was mostly smooth.",
                    "The outfit looked beautiful although a few adjustments were needed.",
                    "Friendly staff and good designs. Overall a pleasant experience.",
                    "Happy with the final outfit. Communication could have been faster.",
                    "Good quality outfits and reasonable service overall."
                )

                else -> listOf(
                    "The outfit was okay but there were some fitting issues.",
                    "Decent selection but I expected more choices.",
                    "Overall acceptable, although the alteration process could be improved."
                )
            }

            comments[index % comments.size]
        }

        // =========================
        // DECO
        // =========================

        "Deco" -> {

            val comments = when (rating) {

                5 -> listOf(
                    "The decorations transformed the venue beautifully!",
                    "Loved the floral arrangements and the overall wedding theme.",
                    "The backdrop looked amazing in our wedding photos.",
                    "Everything matched our chosen theme perfectly.",
                    "The deco team was creative and very professional.",
                    "The setup was completed on time and looked beautiful.",
                    "Our guests loved the decorations and kept complimenting them.",
                    "The floral arch was exactly what we imagined for our ceremony.",
                    "Beautiful styling with great attention to detail.",
                    "Very happy with the final setup. It made our wedding venue look magical."
                )

                4 -> listOf(
                    "The decorations looked good overall. A few details could have been improved.",
                    "Nice styling and friendly team. Setup went quite smoothly.",
                    "The final decoration was beautiful although communication had a few delays.",
                    "Good value and the theme was followed well.",
                    "Overall happy with the decoration and service."
                )

                else -> listOf(
                    "The decoration was decent but some details did not match our expectations.",
                    "Overall okay, although the setup could have been more polished.",
                    "The styling was acceptable but communication could have been better."
                )
            }

            comments[index % comments.size]
        }

        else -> {
            "Good experience overall. The team was helpful and professional."
        }
    }
}