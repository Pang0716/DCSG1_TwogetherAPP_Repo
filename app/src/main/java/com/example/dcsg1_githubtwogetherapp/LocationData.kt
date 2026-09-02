package com.example.dcsg1_githubtwogetherapp

data class WeddingState(
    val stateName: String,
    val areas: List<String>
)
val malaysiaWeddingLocations = listOf(
    WeddingState("Penang", listOf("George Town", "Komtar", "Bayan Lepas", "Butterworth")),
    WeddingState("Kuala Lumpur", listOf("Bukit Bintang", "KLCC", "Bangsar", "Mont Kiara")),
    WeddingState("Selangor", listOf("Petaling Jaya", "Shah Alam", "Subang Jaya", "Puchong")),
    WeddingState("Johor", listOf("Johor Bahru", "Iskandar Puteri", "Batu Pahat")),
    WeddingState("Perak", listOf("Ipoh", "Taiping")),
    WeddingState("Melaka", listOf("Melaka City", "Ayer Keroh")),
    WeddingState("Negeri Sembilan", listOf("Seremban", "Port Dickson")),
    WeddingState("Pahang", listOf("Kuantan", "Genting Highlands")),
    WeddingState("Kedah", listOf("Alor Setar", "Langkawi")),
    WeddingState("Terengganu", listOf("Kuala Terengganu")),
    WeddingState("Kelantan", listOf("Kota Bharu")),
    WeddingState("Perlis", listOf("Kangar"))
)