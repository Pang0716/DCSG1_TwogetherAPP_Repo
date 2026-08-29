package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(onBackClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val emailPattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color(0xFFFDF3ED), Color.White)))
    ) {
        Image(
            painter = painterResource(id = R.drawable.flower_top_right),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.align(Alignment.TopEnd).offset(x = 30.dp, y = 20.dp).size(220.dp).alpha(0.9f)
        )
        Image(
            painter = painterResource(id = R.drawable.flower_bottom_left),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.align(Alignment.BottomStart).offset(x = (-30).dp, y = 20.dp).size(220.dp).alpha(0.9f)
        )

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Check Your Email") },
                text = { Text("We've sent a password reset link to $email. Please check your inbox.") },
                confirmButton = {
                    Button(
                        onClick = { showSuccessDialog = false; onBackClick() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C))
                    ) { Text("Back to Login") }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(24.dp).clickable { onBackClick() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Forgot Password", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(
                text = "Enter your email and we'll send you a link to reset your password.",
                fontSize = 13.sp, color = Color.Gray
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(text = "Email", fontSize = 13.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter your email") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null, tint = Color(0xFFB5722C)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            errorMessage?.let {
                Text(text = it, color = Color.Red, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = {
                    if (!emailPattern.matches(email.trim())) {
                        errorMessage = "Please enter a valid email address."
                        return@Button
                    }
                    errorMessage = null
                    isLoading = true
                    scope.launch {
                        val result = sendPasswordResetEmail(email.trim())
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
                    Text("Send Reset Link", fontSize = 16.sp)
                }
            }
        }
    }
}