package com.example.dcsg1_githubtwogetherapp

import androidx.compose.ui.draw.clip

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle

// ---- Validation logic — messages are now passed in already-localized, since these
// aren't @Composable functions and can't call stringResource() themselves ----

private val namePattern = Regex("^[A-Za-z\\s]+$")
private val emailPattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
private val malaysiaPhonePattern = Regex("^01(1\\d{8}|[02-9]\\d{7})$")

private fun validateName(value: String, requiredMsg: String, shortMsg: String, lettersMsg: String): String? {
    if (value.isBlank()) return requiredMsg
    if (value.trim().length < 2) return shortMsg
    if (!namePattern.matches(value.trim())) return lettersMsg
    return null
}

private fun validateEmail(value: String, requiredMsg: String, invalidMsg: String): String? {
    if (value.isBlank()) return requiredMsg
    if (!emailPattern.matches(value.trim())) return invalidMsg
    return null
}

private fun validatePhone(value: String, requiredMsg: String, invalidMsg: String): String? {
    if (value.isBlank()) return requiredMsg
    val cleaned = value.replace(" ", "").replace("-", "")
    if (!malaysiaPhonePattern.matches(cleaned)) return invalidMsg
    return null
}

private fun validatePassword(value: String, requiredMsg: String, minCharsMsg: String, lettersNumbersMsg: String): String? {
    if (value.isBlank()) return requiredMsg
    if (value.length < 8) return minCharsMsg
    if (!value.any { it.isLetter() } || !value.any { it.isDigit() }) return lettersNumbersMsg
    return null
}

private fun validateConfirmPassword(password: String, confirm: String, requiredMsg: String, mismatchMsg: String): String? {
    if (confirm.isBlank()) return requiredMsg
    if (password != confirm) return mismatchMsg
    return null
}

@Composable
fun RegisterScreen(
    onBackClick: () -> Unit,
    onNavigateToTerms: () -> Unit,
    onNavigateToPrivacy: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var agreedToTerms by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    var nameTouched by remember { mutableStateOf(false) }
    var emailTouched by remember { mutableStateOf(false) }
    var phoneTouched by remember { mutableStateOf(false) }
    var passwordTouched by remember { mutableStateOf(false) }
    var confirmTouched by remember { mutableStateOf(false) }

    // Localized validation messages, computed once per recomposition
    val nameRequiredMsg = stringResource(R.string.full_name_required)
    val nameShortMsg = stringResource(R.string.full_name_short_error)
    val nameLettersMsg = stringResource(R.string.full_name_letters_only)
    val emailRequiredMsg = stringResource(R.string.email_required)
    val emailInvalidMsg = stringResource(R.string.email_invalid)
    val phoneRequiredMsg = stringResource(R.string.phone_required)
    val phoneInvalidMsg = stringResource(R.string.phone_invalid_full)
    val passwordRequiredMsg = stringResource(R.string.password_required)
    val passwordMinCharsMsg = stringResource(R.string.password_min_chars)
    val passwordLettersNumbersMsg = stringResource(R.string.password_letters_numbers)
    val confirmRequiredMsg = stringResource(R.string.confirm_password_required)
    val confirmMismatchMsg = stringResource(R.string.passwords_do_not_match)

    val nameError = validateName(fullName, nameRequiredMsg, nameShortMsg, nameLettersMsg)
    val emailError = validateEmail(email, emailRequiredMsg, emailInvalidMsg)
    val phoneError = validatePhone(phoneNumber, phoneRequiredMsg, phoneInvalidMsg)
    val passwordError = validatePassword(password, passwordRequiredMsg, passwordMinCharsMsg, passwordLettersNumbersMsg)
    val confirmError = validateConfirmPassword(password, confirmPassword, confirmRequiredMsg, confirmMismatchMsg)

    val isFormValid = nameError == null && emailError == null && phoneError == null &&
            passwordError == null && confirmError == null && agreedToTerms

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val googleSignInClient = remember { getGoogleSignInClient(context) }
    val view = LocalView.current
    val activity = view.context as? android.app.Activity

    val googleSigninFailedMsg = stringResource(R.string.google_signin_failed)
    val facebookSigninFailedMsg = stringResource(R.string.facebook_signin_failed)

    val googleLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(com.google.android.gms.common.api.ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                scope.launch {
                    isLoading = true
                    val loginResult = signInWithGoogleToken(idToken)
                    isLoading = false
                    loginResult
                        .onSuccess {
                            loadCurrentUserProfile()
                            onBackClick()
                        }
                        .onFailure { errorMessage = it.message }
                }
            }
        } catch (e: com.google.android.gms.common.api.ApiException) {
            if (e.statusCode != 12501) {
                errorMessage = googleSigninFailedMsg
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors = listOf(Color(0xFFFDF3ED), Color.White))
            )
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
                title = { Text(stringResource(R.string.check_your_email)) },
                text = { Text(stringResource(R.string.confirmation_link_sent, email)) },
                confirmButton = {
                    Button(
                        onClick = { showSuccessDialog = false; onBackClick() },
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
                modifier = Modifier.size(24.dp).clickable { onBackClick() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(stringResource(R.string.register_title), fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(
                text = stringResource(R.string.register_subtitle),
                fontSize = 13.sp, color = Color.Gray, lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            ValidatedField(
                label = stringResource(R.string.full_name_field),
                value = fullName,
                onValueChange = { fullName = it; nameTouched = true },
                placeholder = stringResource(R.string.full_name_label),
                icon = Icons.Filled.Person,
                error = if (nameTouched) nameError else null
            )

            Spacer(modifier = Modifier.height(14.dp))

            ValidatedField(
                label = stringResource(R.string.email_field),
                value = email,
                onValueChange = { email = it; emailTouched = true },
                placeholder = stringResource(R.string.email_placeholder),
                icon = Icons.Filled.Email,
                keyboardType = KeyboardType.Email,
                error = if (emailTouched) emailError else null
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.social_login_hint),
                fontSize = 11.sp, color = Color.Gray
            )

            Spacer(modifier = Modifier.height(14.dp))

            ValidatedField(
                label = stringResource(R.string.phone_field),
                value = phoneNumber,
                onValueChange = { phoneNumber = it; phoneTouched = true },
                placeholder = stringResource(R.string.phone_number_placeholder),
                icon = Icons.Filled.Phone,
                keyboardType = KeyboardType.Phone,
                error = if (phoneTouched) phoneError else null,
                onFocusLost = { phoneNumber = formatMalaysianPhone(phoneNumber) }
            )

            Spacer(modifier = Modifier.height(14.dp))

            PasswordField(
                label = stringResource(R.string.password_field),
                value = password,
                onValueChange = { password = it; passwordTouched = true },
                placeholder = stringResource(R.string.password_placeholder),
                visible = passwordVisible,
                onToggleVisibility = { passwordVisible = !passwordVisible },
                error = if (passwordTouched) passwordError else null
            )

            Spacer(modifier = Modifier.height(14.dp))

            PasswordField(
                label = stringResource(R.string.confirm_password_field),
                value = confirmPassword,
                onValueChange = { confirmPassword = it; confirmTouched = true },
                placeholder = stringResource(R.string.confirm_new_password_label),
                visible = confirmPasswordVisible,
                onToggleVisibility = { confirmPasswordVisible = !confirmPasswordVisible },
                error = if (confirmTouched) confirmError else null
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = agreedToTerms,
                    onCheckedChange = { agreedToTerms = it },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFFB5722C))
                )
                val part1 = stringResource(R.string.agree_to_terms_1)
                val termsText = stringResource(R.string.terms_of_service)
                val part2 = stringResource(R.string.agree_to_terms_2)
                val privacyText = stringResource(R.string.privacy_policy)
                val annotatedText = buildAnnotatedString {
                    append(part1)
                    pushStringAnnotation(tag = "TERMS", annotation = "terms")
                    withStyle(SpanStyle(color = Color(0xFFB5722C), fontWeight = FontWeight.Bold)) {
                        append(termsText)
                    }
                    pop()
                    append(part2)
                    pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
                    withStyle(SpanStyle(color = Color(0xFFB5722C), fontWeight = FontWeight.Bold)) {
                        append(privacyText)
                    }
                    pop()
                    append(" *")
                }

                ClickableText(
                    text = annotatedText,
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(tag = "TERMS", start = offset, end = offset)
                            .firstOrNull()?.let { onNavigateToTerms() }
                        annotatedText.getStringAnnotations(tag = "PRIVACY", start = offset, end = offset)
                            .firstOrNull()?.let { onNavigateToPrivacy() }
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            errorMessage?.let {
                Text(text = it, color = Color.Red, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = {
                    errorMessage = null
                    isLoading = true
                    scope.launch {
                        val result = registerUser(email, password, fullName, phoneNumber)
                        isLoading = false
                        result
                            .onSuccess { showSuccessDialog = true }
                            .onFailure { errorMessage = it.message }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB5722C),
                    disabledContainerColor = Color(0xFFE0D5C8)
                ),
                shape = RoundedCornerShape(28.dp),
                enabled = isFormValid && !isLoading,
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                } else {
                    Text(stringResource(R.string.create_account), fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                Text("  ${stringResource(R.string.or_continue_with)}  ", fontSize = 12.sp, color = Color.Gray)
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable {
                            googleSignInClient.signOut().addOnCompleteListener {
                                googleLauncher.launch(googleSignInClient.signInIntent)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("G", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDB4437))
                }

                Spacer(modifier = Modifier.width(20.dp))

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable {
                            if (!isLoading) {
                                isLoading = true
                                scope.launch {
                                    try {
                                        signInWithFacebookOAuth()
                                    } catch (e: Exception) {
                                        errorMessage = facebookSigninFailedMsg
                                    } finally {
                                        isLoading = false
                                    }
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("f", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1877F2))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ValidatedField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    error: String?,
    onFocusLost: (() -> Unit)? = null
) {
    Column {
        Text(text = label, fontSize = 13.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            leadingIcon = { Icon(icon, contentDescription = null, tint = Color(0xFFB5722C)) },
            trailingIcon = {
                if (error != null) {
                    Icon(Icons.Filled.Error, contentDescription = null, tint = Color.Red)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            isError = error != null,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) onFocusLost?.invoke()
                },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                errorContainerColor = Color.White
            ),
            singleLine = true
        )
        if (error != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = error, fontSize = 11.sp, color = Color.Red)
        }
    }
}

@Composable
fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    visible: Boolean,
    onToggleVisibility: () -> Unit,
    error: String?
) {
    Column {
        Text(text = label, fontSize = 13.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = Color(0xFFB5722C)) },
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = onToggleVisibility) {
                    Icon(
                        imageVector = if (visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(R.string.toggle_password_visibility)
                    )
                }
            },
            isError = error != null,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                errorContainerColor = Color.White
            ),
            singleLine = true
        )
        if (error != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = error, fontSize = 11.sp, color = Color.Red)
        }
    }
}