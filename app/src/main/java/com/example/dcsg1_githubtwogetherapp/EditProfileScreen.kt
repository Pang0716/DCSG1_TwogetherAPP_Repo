package com.example.dcsg1_githubtwogetherapp

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.ui.platform.LocalContext

private fun daysInMonth(month: Int, year: Int): Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) 29 else 28
        else -> 31
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(onBackClick: () -> Unit, onForgotPasswordClick: () -> Unit) {
    val user = UserSession.currentUser.value
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var showPhotoOptions by remember { mutableStateOf(false) }
    var isUploadingPhoto by remember { mutableStateOf(false) }
    var photoError by remember { mutableStateOf<String?>(null) }

    val photoErrorMessage = stringResource(R.string.photo_update_error)

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            val userId = user?.id
            if (userId != null) {
                isUploadingPhoto = true
                scope.launch {
                    try {
                        val url = AvatarRepository.uploadFromUri(context, userId, uri)
                        updateAvatarUrl(url)
                        loadCurrentUserProfile()
                        photoError = null
                    } catch (e: Exception) {
                        photoError = photoErrorMessage
                    }
                    isUploadingPhoto = false
                }
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        if (bitmap != null) {
            val userId = user?.id
            if (userId != null) {
                isUploadingPhoto = true
                scope.launch {
                    try {
                        val url = AvatarRepository.uploadFromBitmap(userId, bitmap)
                        updateAvatarUrl(url)
                        loadCurrentUserProfile()
                        photoError = null
                    } catch (e: Exception) {
                        photoError = photoErrorMessage
                    }
                    isUploadingPhoto = false
                }
            }
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) cameraLauncher.launch(null)
    }

    // ---- Personal info state ----
    var fullName by remember { mutableStateOf(user?.fullName ?: "") }
    var phoneNumber by remember { mutableStateOf(user?.phoneNumber ?: "") }
    var gender by remember { mutableStateOf(user?.gender ?: "") }
    var dateOfBirth by remember { mutableStateOf(user?.dateOfBirth ?: "") }
    var isSavingInfo by remember { mutableStateOf(false) }
    var infoError by remember { mutableStateOf<String?>(null) }
    var infoSuccess by remember { mutableStateOf(false) }
    var genderMenuExpanded by remember { mutableStateOf(false) }

    val fullNameRequiredMsg = stringResource(R.string.full_name_required)
    val fullNameLettersOnlyMsg = stringResource(R.string.full_name_letters_only)
    val phoneInvalidMsg = stringResource(R.string.phone_invalid)
    val dobFormatErrorMsg = stringResource(R.string.dob_format_error)
    val dobInvalidMonthMsg = stringResource(R.string.dob_invalid_month)
    val dobInvalidYearMsg = stringResource(R.string.dob_invalid_year)
    val dobInvalidDayMsg = stringResource(R.string.dob_invalid_day)

    val nameError = if (fullName.isBlank()) fullNameRequiredMsg
    else if (!Regex("^[A-Za-z\\s]+$").matches(fullName.trim())) fullNameLettersOnlyMsg
    else null

    val phoneError = if (phoneNumber.isNotBlank()) {
        val cleaned = phoneNumber.replace(" ", "").replace("-", "")
        if (!Regex("^01(1\\d{8}|[02-9]\\d{7})$").matches(cleaned)) phoneInvalidMsg else null
    } else null

    val dobError = if (dateOfBirth.isNotBlank()) {
        val parts = dateOfBirth.split("/")
        if (parts.size != 3) {
            dobFormatErrorMsg
        } else {
            val day = parts[0].toIntOrNull()
            val month = parts[1].toIntOrNull()
            val year = parts[2].toIntOrNull()
            when {
                day == null || month == null || year == null -> dobFormatErrorMsg
                month !in 1..12 -> dobInvalidMonthMsg
                year < 1900 || year > java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) -> dobInvalidYearMsg
                day !in 1..daysInMonth(month, year) -> dobInvalidDayMsg
                else -> null
            }
        }
    } else null

    // ---- Password state ----
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isSavingPassword by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var passwordSuccess by remember { mutableStateOf(false) }

    val fixErrorsMsg = stringResource(R.string.fix_errors_before_saving)
    val currentPasswordRequiredMsg = stringResource(R.string.current_password_required)
    val newPasswordMinLengthMsg = stringResource(R.string.new_password_min_length)
    val passwordsDoNotMatchMsg = stringResource(R.string.passwords_do_not_match)
    val couldNotVerifyEmailMsg = stringResource(R.string.could_not_verify_email)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF8F3))
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Icon(
            Icons.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(24.dp).clickable { onBackClick() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.edit_profile_title), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Spacer(modifier = Modifier.height(20.dp))

        // ---- Avatar ----
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Box {
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

                if (isUploadingPhoto) {
                    Box(
                        modifier = Modifier.size(90.dp).clip(CircleShape).background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(28.dp))
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFB5722C))
                        .clickable { showPhotoOptions = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Change photo", tint = Color.White, modifier = Modifier.size(14.dp))
                }
            }
        }

        photoError?.let {
            Spacer(modifier = Modifier.height(6.dp))
            Text(it, color = Color.Red, fontSize = 11.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ---- Personal Info card ----
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(18.dp)
        ) {
            Text(stringResource(R.string.personal_info), fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(14.dp))

            Text(stringResource(R.string.full_name_label), fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                isError = nameError != null,
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (nameError != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(nameError, fontSize = 11.sp, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(stringResource(R.string.email_label), fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(user?.email ?: "", fontSize = 14.sp, color = Color.DarkGray)
            Text(stringResource(R.string.email_cannot_change), fontSize = 11.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(14.dp))

            Text(stringResource(R.string.phone_number_label), fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholder = { Text(stringResource(R.string.phone_number_placeholder)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (!focusState.isFocused) {
                            phoneNumber = formatMalaysianPhone(phoneNumber)
                        }
                    }
            )
            if (phoneError != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(phoneError, fontSize = 11.sp, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(stringResource(R.string.gender_label), fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            val genderOptions = listOf(
                stringResource(R.string.gender_male),
                stringResource(R.string.gender_female),
                stringResource(R.string.gender_prefer_not)
            )
            ExposedDropdownMenuBox(
                expanded = genderMenuExpanded,
                onExpandedChange = { genderMenuExpanded = it }
            ) {
                OutlinedTextField(
                    value = gender.ifBlank { stringResource(R.string.select_gender) },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderMenuExpanded) },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable)
                )
                ExposedDropdownMenu(
                    expanded = genderMenuExpanded,
                    onDismissRequest = { genderMenuExpanded = false }
                ) {
                    genderOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = { gender = option; genderMenuExpanded = false }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(stringResource(R.string.date_of_birth_label), fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = dateOfBirth,
                onValueChange = { dateOfBirth = it },
                placeholder = { Text(stringResource(R.string.dob_format)) },
                isError = dobError != null,
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (dobError != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(dobError, fontSize = 11.sp, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(16.dp))

            infoError?.let {
                Text(it, color = Color.Red, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (infoSuccess) {
                Text(stringResource(R.string.profile_updated), color = Color(0xFF2E7D32), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    if (nameError != null || phoneError != null || dobError != null) {
                        infoError = fixErrorsMsg
                        return@Button
                    }
                    infoError = null
                    infoSuccess = false
                    isSavingInfo = true
                    scope.launch {
                        val result = updateUserProfile(fullName.trim(), phoneNumber.trim(), gender, dateOfBirth.trim())
                        isSavingInfo = false
                        result
                            .onSuccess {
                                loadCurrentUserProfile()
                                infoSuccess = true
                            }
                            .onFailure { infoError = it.message }
                    }
                },
                enabled = !isSavingInfo,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth().height(46.dp)
            ) {
                if (isSavingInfo) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(18.dp))
                else Text(stringResource(R.string.save_changes))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ---- Security card ----
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Lock, contentDescription = null, tint = Color(0xFFB5722C), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(stringResource(R.string.change_password), fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.current_password_label), fontSize = 12.sp, color = Color.Gray)
                Text(
                    stringResource(R.string.forgot_password),
                    fontSize = 12.sp,
                    color = Color(0xFFB5722C),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onForgotPasswordClick() }
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = currentPassword,
                onValueChange = { currentPassword = it },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(stringResource(R.string.new_password_label), fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle visibility"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(stringResource(R.string.confirm_new_password_label), fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            passwordError?.let {
                Text(it, color = Color.Red, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (passwordSuccess) {
                Text(stringResource(R.string.password_changed_success), color = Color(0xFF2E7D32), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    when {
                        currentPassword.isBlank() -> passwordError = currentPasswordRequiredMsg
                        newPassword.length < 6 -> passwordError = newPasswordMinLengthMsg
                        newPassword != confirmPassword -> passwordError = passwordsDoNotMatchMsg
                        else -> {
                            passwordError = null
                            passwordSuccess = false
                            isSavingPassword = true
                            scope.launch {
                                val email = user?.email
                                if (email == null) {
                                    isSavingPassword = false
                                    passwordError = couldNotVerifyEmailMsg
                                    return@launch
                                }
                                val reauth = reauthenticate(email, currentPassword)
                                if (reauth.isFailure) {
                                    isSavingPassword = false
                                    passwordError = reauth.exceptionOrNull()?.message
                                    return@launch
                                }
                                val result = updatePassword(newPassword)
                                isSavingPassword = false
                                result
                                    .onSuccess {
                                        passwordSuccess = true
                                        currentPassword = ""
                                        newPassword = ""
                                        confirmPassword = ""
                                    }
                                    .onFailure { passwordError = it.message }
                            }
                        }
                    }
                },
                enabled = !isSavingPassword,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth().height(46.dp)
            ) {
                if (isSavingPassword) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(18.dp))
                else Text(stringResource(R.string.update_password))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    if (showPhotoOptions) {
        AlertDialog(
            onDismissRequest = { showPhotoOptions = false },
            title = { Text(stringResource(R.string.update_profile_photo)) },
            text = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showPhotoOptions = false
                                cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                            }
                            .padding(vertical = 12.dp)
                    ) {
                        Icon(Icons.Filled.CameraAlt, contentDescription = null, tint = Color(0xFFB5722C))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(stringResource(R.string.take_photo))
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showPhotoOptions = false
                                galleryLauncher.launch("image/*")
                            }
                            .padding(vertical = 12.dp)
                    ) {
                        Icon(Icons.Filled.PhotoLibrary, contentDescription = null, tint = Color(0xFFB5722C))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(stringResource(R.string.choose_from_gallery))
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showPhotoOptions = false }) { Text(stringResource(R.string.cancel)) }
            }
        )
    }
}