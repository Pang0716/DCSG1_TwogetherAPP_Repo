package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(onDone: () -> Unit) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Password Updated") },
            text = { Text("Your password has been changed successfully. Please log in again.") },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        scope.launch {
                            logoutUser()
                            onDone()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C))
                ) { Text("Go to Login") }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text("Set New Password", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(
            text = "Please enter your new password below.",
            fontSize = 13.sp, color = Color.Gray
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(text = "New Password", fontSize = 13.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            placeholder = { Text("Enter new password") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = Color(0xFFB5722C)) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(text = "Confirm New Password", fontSize = 13.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = { Text("Confirm new password") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = Color(0xFFB5722C)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let {
            Text(text = it, color = Color.Red, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
            onClick = {
                if (newPassword.length < 8) {
                    errorMessage = "Password must be at least 8 characters."
                    return@Button
                }
                if (!newPassword.any { it.isLetter() } || !newPassword.any { it.isDigit() }) {
                    errorMessage = "Password must contain letters and numbers."
                    return@Button
                }
                if (newPassword != confirmPassword) {
                    errorMessage = "Passwords do not match."
                    return@Button
                }
                errorMessage = null
                isLoading = true
                scope.launch {
                    val result = updatePassword(newPassword)
                    isLoading = false
                    result
                        .onSuccess { showSuccessDialog = true }
                        .onFailure { errorMessage = it.message }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
            shape = RoundedCornerShape(28.dp),
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text("Update Password", fontSize = 16.sp)
            }
        }
    }
}