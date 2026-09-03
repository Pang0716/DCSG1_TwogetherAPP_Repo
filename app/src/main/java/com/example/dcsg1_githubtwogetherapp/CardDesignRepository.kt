package com.example.dcsg1_githubtwogetherapp

import io.github.jan.supabase.postgrest.postgrest

suspend fun saveCardDesign(design: CardDesign, dao: CardDesignDao) {
    // 1. Save locally first - instant, works offline
    dao.insertDesign(
        CardDesignEntity(
            id = design.id,
            coupleNames = design.coupleNames,
            eventDate = design.eventDate,
            venue = design.venue,
            style = design.style,
            fontStyle = design.fontStyle,
            photoUri = design.photoUri,
            createdAt = design.createdAt
        )
    )

    // 2. Then try to sync to Supabase - uses the same 'supabase' client
    //    already set up in your project (the one used for supabase.auth)
    try {
        supabase.postgrest.from("card_designs").insert(design)
    } catch (e: Exception) {
        // no internet or sync failed - local copy is already safe, so ignore
    }
}

