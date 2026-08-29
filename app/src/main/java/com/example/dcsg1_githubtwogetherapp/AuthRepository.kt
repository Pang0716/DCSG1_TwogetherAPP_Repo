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
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import io.github.jan.supabase.auth.providers.Facebook
import io.github.jan.supabase.auth.user.UserUpdateBuilder
import io.github.jan.supabase.auth.OtpType

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
        val friendlyMessage = when {
            e.message?.contains("already registered", ignoreCase = true) == true ->
                "This email is already registered. Try logging in instead."
            e.message?.contains("Password should be", ignoreCase = true) == true ->
                "Password is too weak. Use at least 6 characters."
            else -> "Registration failed. Please try again."
        }
        Result.failure(Exception(friendlyMessage))
    } catch (e: Exception) {
        Result.failure(Exception("Something went wrong. Please check your internet connection and try again."))
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
        val friendlyMessage = when {
            e.message?.contains("Invalid login credentials", ignoreCase = true) == true ->
                "Incorrect email or password. Please try again."
            e.message?.contains("Email not confirmed", ignoreCase = true) == true ->
                "Please verify your email before logging in."
            else -> "Login failed. Please try again."
        }
        Result.failure(Exception(friendlyMessage))
    } catch (e: Exception) {
        Result.failure(Exception("Something went wrong. Please check your internet connection and try again."))
    }
}

suspend fun logoutUser() {
    supabase.auth.signOut()
    UserSession.currentUser.value = null
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

suspend fun signInWithFacebookOAuth() {
    supabase.auth.signInWith(Facebook, redirectUrl = "twogether://login-callback")
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

suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
    return try {
        supabase.auth.resetPasswordForEmail(
            email = email,
            redirectUrl = "twogether://reset-password"
        )
        Result.success(Unit)
    } catch (e: RestException) {
        Result.failure(Exception("Failed to send reset email. Please check the address and try again."))
    } catch (e: Exception) {
        Result.failure(Exception("Something went wrong. Please check your internet connection and try again."))
    }
}

suspend fun updatePassword(newPassword: String): Result<Unit> {
    return try {
        supabase.auth.updateUser {
            password = newPassword
        }
        Result.success(Unit)
    } catch (e: RestException) {
        Result.failure(Exception("Failed to update password. Please try again."))
    } catch (e: Exception) {
        Result.failure(Exception("Something went wrong. Please try again."))
    }
}

suspend fun verifyPasswordResetCode(email: String, code: String): Result<Unit> {
    return try {
        supabase.auth.verifyEmailOtp(
            type = OtpType.Email.RECOVERY,
            email = email,
            token = code
        )
        Result.success(Unit)
    } catch (e: RestException) {
        Result.failure(Exception("Invalid or expired code. Please try again."))
    } catch (e: Exception) {
        Result.failure(Exception("Something went wrong. Please try again."))
    }
}

suspend fun updateUserFullName(newName: String): Result<Unit> {
    return try {
        supabase.auth.updateUser {
            data = kotlinx.serialization.json.buildJsonObject {
                put("full_name", kotlinx.serialization.json.JsonPrimitive(newName))
            }
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Failed to update profile. Please try again."))
    }
}