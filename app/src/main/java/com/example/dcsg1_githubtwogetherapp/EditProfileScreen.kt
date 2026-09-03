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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

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
fun EditProfileScreen(onBackClick: () -> Unit) {
    val user = UserSession.currentUser.value
    val scope = rememberCoroutineScope()

    // ---- Personal info state ----
    var fullName by remember { mutableStateOf(user?.fullName ?: "") }
    var phoneNumber by remember { mutableStateOf(user?.phoneNumber ?: "") }
    var gender by remember { mutableStateOf(user?.gender ?: "") }
    var dateOfBirth by remember { mutableStateOf(user?.dateOfBirth ?: "") }
    var isSavingInfo by remember { mutableStateOf(false) }
    var infoError by remember { mutableStateOf<String?>(null) }
    var infoSuccess by remember { mutableStateOf(false) }
    var genderMenuExpanded by remember { mutableStateOf(false) }
    val nameError = if (fullName.isBlank()) "Full name is required."
    else if (!Regex("^[A-Za-z\\s]+$").matches(fullName.trim())) "Only letters and spaces allowed."
    else null

    val phoneError = if (phoneNumber.isNotBlank()) {
        val cleaned = phoneNumber.replace(" ", "").replace("-", "")
        if (!Regex("^01(1\\d{8}|[02-9]\\d{7})$").matches(cleaned)) "Enter a valid Malaysian number." else null
    } else null

    val dobError = if (dateOfBirth.isNotBlank()) {
        val parts = dateOfBirth.split("/")
        if (parts.size != 3) {
            "Use DD/MM/YYYY format."
        } else {
            val day = parts[0].toIntOrNull()
            val month = parts[1].toIntOrNull()
            val year = parts[2].toIntOrNull()
            when {
                day == null || month == null || year == null -> "Use DD/MM/YYYY format."
                month !in 1..12 -> "Invalid month."
                year < 1900 || year > java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) -> "Invalid year."
                day !in 1..daysInMonth(month, year) -> "Invalid day for that month."
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
        Text("Edit Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Spacer(modifier = Modifier.height(20.dp))

        // ---- Avatar ----
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
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
            Text("Personal Info", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(14.dp))

            Text("Full Name", fontSize = 12.sp, color = Color.Gray)
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

            Text("Email", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(user?.email ?: "", fontSize = 14.sp, color = Color.DarkGray)
            Text("Email cannot be changed here.", fontSize = 11.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(14.dp))

            Text("Phone Number", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholder = { Text("e.g. 012-345 6789") },
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

            Text("Gender", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            ExposedDropdownMenuBox(
                expanded = genderMenuExpanded,
                onExpandedChange = { genderMenuExpanded = it }
            ) {
                OutlinedTextField(
                    value = gender.ifBlank { "Select gender" },
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
                    listOf("Male", "Female", "Prefer not to say").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = { gender = option; genderMenuExpanded = false }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text("Date of Birth", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = dateOfBirth,
                onValueChange = { dateOfBirth = it },
                placeholder = { Text("DD/MM/YYYY") },
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
                Text("Profile updated!", color = Color(0xFF2E7D32), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    if (nameError != null || phoneError != null || dobError != null) {
                        infoError = "Please fix the errors above before saving."
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
                else Text("Save Changes")
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
                Text("Change Password", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(14.dp))

            Text("Current Password", fontSize = 12.sp, color = Color.Gray)
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

            Text("New Password", fontSize = 12.sp, color = Color.Gray)
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

            Text("Confirm New Password", fontSize = 12.sp, color = Color.Gray)
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
                Text("Password changed successfully!", color = Color(0xFF2E7D32), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    when {
                        currentPassword.isBlank() -> passwordError = "Enter your current password."
                        newPassword.length < 6 -> passwordError = "New password must be at least 6 characters."
                        newPassword != confirmPassword -> passwordError = "Passwords do not match."
                        else -> {
                            passwordError = null
                            passwordSuccess = false
                            isSavingPassword = true
                            scope.launch {
                                val email = user?.email
                                if (email == null) {
                                    isSavingPassword = false
                                    passwordError = "Could not verify account email."
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
                else Text("Update Password")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}