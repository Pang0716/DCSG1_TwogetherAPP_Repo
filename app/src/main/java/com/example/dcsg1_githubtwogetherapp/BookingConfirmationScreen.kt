package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookingConfirmationScreen(
    onViewMyBookings: () -> Unit,
    onBackToHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF8F3))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(72.dp).clip(CircleShape).background(Color(0xFFB5722C)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(34.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(stringResource(R.string.booking_confirmed_title), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB5722C))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(R.string.booking_confirmed_message),
            fontSize = 13.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onViewMyBookings,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(stringResource(R.string.view_my_bookings))
        }
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(onClick = onBackToHome) {
            Text(stringResource(R.string.back_to_home), color = Color(0xFFB5722C))
        }
    }
}