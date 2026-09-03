package com.example.dcsg1_githubtwogetherapp

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://tqexlhzvlgcvtpiffzeb.supabase.co",
    supabaseKey = "sb_publishable_hqGPlhF4zTuiSK6JN2xQYA_bC5RYQR4"
) {
    install(Auth) {
        host = "login-callback"
        scheme = "twogether"
    }
    install(Postgrest)
    install(io.github.jan.supabase.realtime.Realtime)
}