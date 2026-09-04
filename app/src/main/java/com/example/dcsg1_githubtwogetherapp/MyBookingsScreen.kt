package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.layout.statusBarsPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBookingsScreen(onBackClick: () -> Unit, onBookingClick: (BookingEntity) -> Unit) {
    val context = LocalContext.current
    var bookings by remember { mutableStateOf<List<BookingEntity>>(emptyList()) }
    var weddingDateMillis by remember { mutableStateOf<Long?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val userId = UserSession.currentUser.value?.id
        if (userId != null) {
            bookings = BookingRepository.loadBookings(context, userId)
            val (date, _) = WeddingRepository.loadWedding(context, userId)
            weddingDateMillis = date
        }
        isLoading = false
    }

    val isWeddingPast = weddingDateMillis != null && weddingDateMillis!! < System.currentTimeMillis()
    val upcomingBookings = if (isWeddingPast) emptyList() else bookings
    val pastBookings = if (isWeddingPast) bookings else emptyList()

    Scaffold(
        containerColor = Color(0xFFFDF8F3),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.my_bookings_title), fontWeight = FontWeight.Bold, color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFDF8F3))
            )
        }
    ) { innerPadding ->
        when {
            isLoading -> {
                Box(modifier = Modifier.padding(innerPadding).fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFB5722C))
                }
            }
            bookings.isEmpty() -> {
                Column(
                    modifier = Modifier.padding(innerPadding).fillMaxSize().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Filled.Work, contentDescription = null, tint = Color(0xFFB5722C), modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(stringResource(R.string.no_bookings_yet), fontSize = 15.sp, color = Color.Gray)
                    Text(stringResource(R.string.bookings_will_show_here), fontSize = 12.sp, color = Color.Gray)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding).fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (upcomingBookings.isNotEmpty()) {
                        item {
                            Text(stringResource(R.string.upcoming_label), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB5722C))
                        }
                        itemsIndexed(upcomingBookings) { _, booking ->
                            BookingRow(booking, onClick = { onBookingClick(booking) })
                        }
                    }
                    if (pastBookings.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(stringResource(R.string.completed_label), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        }
                        itemsIndexed(pastBookings) { _, booking ->
                            BookingRow(booking, onClick = { onBookingClick(booking) }, isPast = true)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookingRow(booking: BookingEntity, onClick: () -> Unit, isPast: Boolean = false) {
    val vendor = sampleVendors.find { it.name == booking.vendorName }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(if (isPast) Color(0xFFF5F0EA) else Color.White)
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier.size(56.dp).clip(RoundedCornerShape(10.dp)).background(Color(0xFFFDECD8))
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

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(booking.vendorName, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (isPast) Color.Gray else Color.Black)
            Text(booking.category, fontSize = 12.sp, color = Color.Gray)
            val currentLocale = androidx.compose.ui.platform.LocalConfiguration.current.locales[0]
            val bookedOnLabel = stringResource(R.string.booked_on)
            Text(
                remember(booking.bookedAt, currentLocale) {
                    bookedOnLabel.format(SimpleDateFormat("dd MMM yyyy", currentLocale).format(Date(booking.bookedAt)))
                },
                fontSize = 11.sp, color = Color.Gray
            )
        }

        Text(booking.price, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (isPast) Color.Gray else Color(0xFFB5722C))
    }
}