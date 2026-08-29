package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(onBackClick: () -> Unit) {
    val user = UserSession.currentUser.value
    var fullName by remember { mutableStateOf(user?.fullName ?: "") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccess by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Spacer(modifier = Modifier.height(20.dp))
        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(24.dp).clickable { onBackClick() })
        Spacer(modifier = Modifier.height(16.dp))
        Text("Edit Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Spacer(modifier = Modifier.height(24.dp))

        Text("Full Name", fontSize = 13.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text("Email", fontSize = 13.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(6.dp))
        Text(user?.email ?: "", fontSize = 14.sp, color = Color.Gray)
        Text("Email cannot be changed here.", fontSize = 11.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(20.dp))

        errorMessage?.let {
            Text(it, color = Color.Red, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(12.dp))
        }
        if (showSuccess) {
            Text("Profile updated successfully!", color = Color(0xFF2E7D32), fontSize = 12.sp)
            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
            onClick = {
                if (fullName.isBlank()) {
                    errorMessage = "Name cannot be empty."
                    return@Button
                }
                errorMessage = null
                isLoading = true
                scope.launch {
                    val result = updateUserFullName(fullName.trim())
                    isLoading = false
                    result
                        .onSuccess { loadCurrentUserProfile(); showSuccess = true }
                        .onFailure { errorMessage = it.message }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            if (isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            else Text("Save Changes")
        }
    }
}