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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Error
import androidx.compose.foundation.clickable

@Composable
fun ForgotPasswordScreen(onBackClick: () -> Unit, onResetComplete: () -> Unit) {
    // Step tracking: 1 = enter email, 2 = enter code + new password
    var step by remember { mutableStateOf(1) }

    var email by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var resendCooldown by remember { mutableStateOf(0) }

    val scope = rememberCoroutineScope()
    val emailPattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    LaunchedEffect(resendCooldown) {
        if (resendCooldown > 0) {
            kotlinx.coroutines.delay(1000)
            resendCooldown--
        }
    }

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
                title = { Text("Password Updated") },
                text = { Text("Your password has been changed successfully. Please log in again.") },
                confirmButton = {
                    Button(
                        onClick = {
                            showSuccessDialog = false
                            scope.launch {
                                logoutUser()
                                onResetComplete()
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
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(24.dp).clickable {
                    if (step == 2) step = 1 else onBackClick()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (step == 1) {
                Text("Forgot Password", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(
                    text = "Enter your email and we'll send you a code to reset your password.",
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
                                .onSuccess { step = 2; resendCooldown = 30 }
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
                        Text("Send Code", fontSize = 16.sp)
                    }
                }
            } else {
                Text("Enter Code", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(
                    text = "We've sent a code to $email. Enter it below along with your new password.",
                    fontSize = 13.sp, color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Didn't get it? ", fontSize = 12.sp, color = Color.Gray)
                    if (resendCooldown > 0) {
                        Text("Resend in ${resendCooldown}s", fontSize = 12.sp, color = Color.Gray)
                    } else {
                        Text(
                            "Resend Code",
                            fontSize = 12.sp,
                            color = Color(0xFFB5722C),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                scope.launch {
                                    val result = sendPasswordResetEmail(email.trim())
                                    result.onSuccess { resendCooldown = 30 }
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Live validation, same pattern as Register page
                var codeTouched by remember { mutableStateOf(false) }
                var passwordTouched by remember { mutableStateOf(false) }
                var confirmTouched by remember { mutableStateOf(false) }

                val codeError = if (code.isBlank()) "Code is required." else if (code.trim().length != 8) "Code must be 8 digits." else null
                val passwordError = when {
                    newPassword.isBlank() -> "Password is required."
                    newPassword.length < 8 -> "At least 8 characters required."
                    !newPassword.any { it.isLetter() } || !newPassword.any { it.isDigit() } -> "Must contain letters and numbers."
                    else -> null
                }
                val confirmError = when {
                    confirmPassword.isBlank() -> "Please confirm your password."
                    newPassword != confirmPassword -> "Passwords do not match."
                    else -> null
                }

                val isStepValid = codeError == null && passwordError == null && confirmError == null

                Text(text = "Verification Code", fontSize = 13.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it; codeTouched = true },
                    placeholder = { Text("Enter 8-digit code") },
                    trailingIcon = {
                        if (codeTouched && codeError != null) {
                            Icon(Icons.Filled.Error, contentDescription = null, tint = Color.Red)
                        }
                    },
                    isError = codeTouched && codeError != null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        errorContainerColor = Color.White
                    ),
                    singleLine = true
                )
                if (codeTouched && codeError != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = codeError, fontSize = 11.sp, color = Color.Red)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(text = "New Password", fontSize = 13.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it; passwordTouched = true },
                    placeholder = { Text("Enter new password") },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = Color(0xFFB5722C)) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = passwordTouched && passwordError != null,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        errorContainerColor = Color.White
                    ),
                    singleLine = true
                )
                if (passwordTouched && passwordError != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = passwordError, fontSize = 11.sp, color = Color.Red)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(text = "Confirm New Password", fontSize = 13.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it; confirmTouched = true },
                    placeholder = { Text("Confirm new password") },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = Color(0xFFB5722C)) },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = confirmTouched && confirmError != null,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        errorContainerColor = Color.White
                    ),
                    singleLine = true
                )
                if (confirmTouched && confirmError != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = confirmError, fontSize = 11.sp, color = Color.Red)
                }

                Spacer(modifier = Modifier.height(16.dp))

                errorMessage?.let {
                    Text(text = it, color = Color.Red, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Button(
                    onClick = {
                        errorMessage = null
                        isLoading = true
                        scope.launch {
                            val verifyResult = verifyPasswordResetCode(email.trim(), code.trim())
                            if (verifyResult.isFailure) {
                                isLoading = false
                                errorMessage = verifyResult.exceptionOrNull()?.message
                                return@launch
                            }
                            val updateResult = updatePassword(newPassword)
                            isLoading = false
                            updateResult
                                .onSuccess { showSuccessDialog = true }
                                .onFailure { errorMessage = it.message }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB5722C),
                        disabledContainerColor = Color(0xFFE0D5C8)
                    ),
                    shape = RoundedCornerShape(28.dp),
                    enabled = isStepValid && !isLoading,
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                    } else {
                        Text("Reset Password", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}