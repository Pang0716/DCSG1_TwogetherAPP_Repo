package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDetailScreen(booking: BookingEntity, onBackClick: () -> Unit) {
    val vendor = sampleVendors.find { it.name == booking.vendorName }

    Scaffold(
        containerColor = Color(0xFFFDF8F3),
        topBar = {
            TopAppBar(
                title = { Text("Booking Details", fontWeight = FontWeight.Bold, color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFDF8F3))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFFDECD8))
            ) {
                when {
                    vendor?.imageResId != null -> Image(
                        painter = painterResource(id = vendor.imageResId), contentDescription = vendor.name,
                        contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()
                    )
                    vendor?.imageUrl != null -> AsyncImage(
                        model = vendor.imageUrl, contentDescription = vendor.name,
                        contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()
                    )
                    else -> Icon(Icons.Filled.Image, contentDescription = null, tint = Color(0xFFB5722C), modifier = Modifier.align(Alignment.Center))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = Color(0xFF3F7D4F), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Booking Confirmed", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3F7D4F))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(18.dp)
            ) {
                Text("Receipt", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(14.dp))

                ReceiptRow("Vendor", booking.vendorName)
                ReceiptRow("Category", booking.category)
                ReceiptRow("Amount Paid", booking.price, valueColor = Color(0xFFB5722C), bold = true)
                ReceiptRow("Payment Method", booking.paymentMethod)
                ReceiptRow(
                    "Booked On",
                    SimpleDateFormat("dd MMM yyyy, h:mm a", Locale.getDefault()).format(Date(booking.bookedAt))
                )
                ReceiptRow("Receipt No.", "TWG-${booking.localId.toString().padStart(6, '0')}")
            }
        }
    }
}

@Composable
fun ReceiptRow(label: String, value: String, valueColor: Color = Color.Black, bold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 13.sp, color = Color.Gray)
        Text(
            value, fontSize = 13.sp, color = valueColor,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.End
        )
    }
    HorizontalDivider(color = Color(0xFFF0E4D8))
}