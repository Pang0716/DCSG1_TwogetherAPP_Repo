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
import androidx.compose.ui.res.stringResource
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

    val emailInvalidMsg = stringResource(R.string.email_invalid)
    val codeRequiredMsg = stringResource(R.string.code_required)
    val codeMustBe8DigitsMsg = stringResource(R.string.code_must_be_8_digits)
    val passwordRequiredMsg = stringResource(R.string.password_required)
    val passwordMinCharsMsg = stringResource(R.string.password_min_chars)
    val passwordLettersNumbersMsg = stringResource(R.string.password_letters_numbers)
    val confirmRequiredMsg = stringResource(R.string.confirm_password_required)
    val confirmMismatchMsg = stringResource(R.string.passwords_do_not_match)

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
                title = { Text(stringResource(R.string.password_updated_title)) },
                text = { Text(stringResource(R.string.password_updated_message)) },
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
                    ) { Text(stringResource(R.string.go_to_login)) }
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
                Text(stringResource(R.string.forgot_password_title), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(
                    text = stringResource(R.string.forgot_password_subtitle),
                    fontSize = 13.sp, color = Color.Gray
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(text = stringResource(R.string.email_label), fontSize = 13.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text(stringResource(R.string.email_placeholder)) },
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
                            errorMessage = emailInvalidMsg
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
                        Text(stringResource(R.string.send_code), fontSize = 16.sp)
                    }
                }
            } else {
                Text(stringResource(R.string.enter_code_title), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(
                    text = stringResource(R.string.enter_code_subtitle, email),
                    fontSize = 13.sp, color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.didnt_get_it), fontSize = 12.sp, color = Color.Gray)
                    if (resendCooldown > 0) {
                        Text(stringResource(R.string.resend_in_seconds, resendCooldown), fontSize = 12.sp, color = Color.Gray)
                    } else {
                        Text(
                            stringResource(R.string.resend_code),
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

                var codeTouched by remember { mutableStateOf(false) }
                var passwordTouched by remember { mutableStateOf(false) }
                var confirmTouched by remember { mutableStateOf(false) }

                val codeError = if (code.isBlank()) codeRequiredMsg else if (code.trim().length != 8) codeMustBe8DigitsMsg else null
                val passwordError = when {
                    newPassword.isBlank() -> passwordRequiredMsg
                    newPassword.length < 8 -> passwordMinCharsMsg
                    !newPassword.any { it.isLetter() } || !newPassword.any { it.isDigit() } -> passwordLettersNumbersMsg
                    else -> null
                }
                val confirmError = when {
                    confirmPassword.isBlank() -> confirmRequiredMsg
                    newPassword != confirmPassword -> confirmMismatchMsg
                    else -> null
                }

                val isStepValid = codeError == null && passwordError == null && confirmError == null

                Text(text = stringResource(R.string.verification_code_label), fontSize = 13.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it; codeTouched = true },
                    placeholder = { Text(stringResource(R.string.verification_code_placeholder)) },
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

                Text(text = stringResource(R.string.new_password_label), fontSize = 13.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it; passwordTouched = true },
                    placeholder = { Text(stringResource(R.string.new_password_placeholder)) },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = Color(0xFFB5722C)) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = stringResource(R.string.toggle_password_visibility)
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

                Text(text = stringResource(R.string.confirm_new_password_label), fontSize = 13.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it; confirmTouched = true },
                    placeholder = { Text(stringResource(R.string.confirm_new_password_placeholder)) },
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
                        Text(stringResource(R.string.reset_password_btn), fontSize = 16.sp)
                    }
                }
            }
        }
    }
}