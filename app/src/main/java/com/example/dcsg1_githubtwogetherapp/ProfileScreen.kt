package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProfileScreen(
    isLoggedIn: Boolean,
    onLogout: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onEditProfile: () -> Unit,
    onHelpSupport: () -> Unit,
    onLanguage: () -> Unit,
    onViewBookings: () -> Unit,
    onViewSavedVendors: () -> Unit
) {
    val user = UserSession.currentUser.value
    var showLogoutConfirm by remember { mutableStateOf(false) }

    if (showLogoutConfirm) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirm = false },
            title = { Text("Log Out") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                Button(
                    onClick = { showLogoutConfirm = false; onLogout() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C))
                ) { Text("Log Out") }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirm = false }) { Text("Cancel") }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        Text("Profile", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoggedIn && user?.avatarUrl != null) {
                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = "Profile photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(90.dp).clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier.size(90.dp).clip(CircleShape).background(Color(0xFFFDECD8)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Person, contentDescription = null, tint = Color(0xFFB5722C), modifier = Modifier.size(40.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (isLoggedIn) {
                Text(user?.fullName ?: "User", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(user?.email ?: "", fontSize = 13.sp, color = Color.Gray)
                if (!user?.phoneNumber.isNullOrBlank()) {
                    Text(user?.phoneNumber ?: "", fontSize = 13.sp, color = Color.Gray)
                }
                if (!user?.gender.isNullOrBlank() || !user?.dateOfBirth.isNullOrBlank()) {
                    Text(
                        listOfNotNull(user?.gender?.takeIf { it.isNotBlank() }, user?.dateOfBirth?.takeIf { it.isNotBlank() })
                            .joinToString(" • "),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onEditProfile() }
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = null, tint = Color(0xFFB5722C), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Edit", fontSize = 12.sp, color = Color(0xFFB5722C))
                }
            } else {
                Text("Guest", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text("Not logged in", fontSize = 13.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onNavigateToLogin,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Login / Register", fontSize = 13.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoggedIn) {
            ProfileMenuItem(icon = Icons.Filled.Work, label = "My Bookings") { onViewBookings() }
            ProfileMenuItem(icon = Icons.Filled.FavoriteBorder, label = "Saved Vendors") { onViewSavedVendors() }
        }
        ProfileMenuItem(icon = Icons.Filled.HelpOutline, label = "Help & Support") { onHelpSupport() }
        ProfileMenuItem(icon = Icons.Filled.Language, label = "Language") { onLanguage() }
        if (isLoggedIn) {
            ProfileMenuItem(icon = Icons.Filled.Logout, label = "Logout") { showLogoutConfirm = true }
        }
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp)
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFFB5722C), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(label, fontSize = 14.sp, color = Color.Black, modifier = Modifier.weight(1f))
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
    }
    HorizontalDivider(color = Color(0xFFF0F0F0))
}