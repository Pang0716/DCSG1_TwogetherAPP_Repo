package com.example.dcsg1_githubtwogetherapp

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.exceptions.RestException
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.providers.Google
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import io.github.jan.supabase.auth.auth

suspend fun registerUser(email: String, password: String, fullName: String): Result<Unit> {
    return try {
        supabase.auth.signUpWith(Email) {
            this.email = email
            this.password = password
            data = kotlinx.serialization.json.buildJsonObject {
                put("full_name", kotlinx.serialization.json.JsonPrimitive(fullName))
            }
        }
        Result.success(Unit)
    } catch (e: RestException) {
        Result.failure(Exception(e.message ?: "Registration failed"))
    } catch (e: Exception) {
        Result.failure(e)
    }
}

suspend fun loginUser(email: String, password: String): Result<Unit> {
    return try {
        supabase.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        Result.success(Unit)
    } catch (e: RestException) {
        Result.failure(Exception(e.message ?: "Login failed"))
    } catch (e: Exception) {
        Result.failure(e)
    }
}

suspend fun logoutUser() {
    supabase.auth.signOut()
}

fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("253148667674-f27s6huk2dq9p0d685t6oq2rpnhfkgqq.apps.googleusercontent.com")
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}

suspend fun signInWithGoogleToken(idToken: String): Result<Unit> {
    return try {
        supabase.auth.signInWith(IDToken) {
            this.idToken = idToken
            provider = Google
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

fun loadCurrentUserProfile() {
    val user = supabase.auth.currentUserOrNull()
    if (user != null) {
        UserSession.currentUser.value = UserProfile(
            id = user.id,
            email = user.email,
            fullName = user.userMetadata?.get("full_name")?.toString()?.trim('"'),
            avatarUrl = user.userMetadata?.get("avatar_url")?.toString()?.trim('"')
        )
    }
}