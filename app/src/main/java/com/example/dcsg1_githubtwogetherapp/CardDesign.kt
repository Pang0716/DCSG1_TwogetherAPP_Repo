package com.example.dcsg1_githubtwogetherapp

import kotlinx.serialization.Serializable

@Serializable
data class CardDesign(
    val id: String = java.util.UUID.randomUUID().toString(),
    val coupleNames: String,
    val eventDate: String,
    val venue: String,
    val style: String,
    val fontStyle: String,
    val photoUri: String?,
    val createdAt: Long = System.currentTimeMillis()
)

