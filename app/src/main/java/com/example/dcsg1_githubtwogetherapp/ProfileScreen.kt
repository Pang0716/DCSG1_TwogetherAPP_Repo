package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun ProfileScreen(onLogout: () -> Unit) {
    val user = UserSession.currentUser.value

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
            if (user?.avatarUrl != null) {
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

            Text(
                text = user?.fullName ?: "Guest",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = user?.email ?: "",
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { /* TODO: edit profile later */ }
            ) {
                Icon(Icons.Filled.Edit, contentDescription = null, tint = Color(0xFFB5722C), modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Edit", fontSize = 12.sp, color = Color(0xFFB5722C))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ProfileMenuItem(icon = Icons.Filled.Work, label = "My Bookings") { }
        ProfileMenuItem(icon = Icons.Filled.FavoriteBorder, label = "Saved Vendors") { }
        ProfileMenuItem(icon = Icons.Filled.HelpOutline, label = "Help & Support") { }
        ProfileMenuItem(icon = Icons.Filled.Language, label = "Language") { }
        ProfileMenuItem(icon = Icons.Filled.Logout, label = "Logout") { onLogout() }
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