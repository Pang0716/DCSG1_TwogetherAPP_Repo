package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage

@Composable
fun VendorInfoCard(
    capacity: String,
    priceRange: String,
    highlights: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFDF8F3))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        InfoColumn(
            icon = Icons.Outlined.Person,
            label = "Capacity",
            value = capacity,
            modifier = Modifier.weight(1f)
        )
        InfoColumn(
            icon = Icons.Outlined.Sell,
            label = "Price Range",
            value = priceRange,
            modifier = Modifier.weight(1f)
        )
        InfoColumn(
            icon = Icons.Outlined.WorkspacePremium,
            label = "Highlights",
            value = highlights,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun InfoColumn(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFB5722C),
            modifier = Modifier.height(24.dp).width(24.dp)
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 13.sp,
            color = Color.Gray,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun VendorDetailTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.clickable { onBackClick() }
        )
        Text("Vendor Details", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(Icons.Filled.Share, contentDescription = "Share")
            Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorite")
        }
    }
}

@Composable
fun VendorDetailScreen(
    vendor: Vendor,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("About") }
    val tabs = listOf("About", "Packages", "Photos", "Reviews")

    Scaffold(
        containerColor = Color(0xFFFDF8F3),
        topBar = { VendorDetailTopBar(onBackClick = onBackClick) },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFDF8F3))
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                ) {
                    Icon(
                        Icons.Outlined.ChatBubbleOutline,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text("Chat", fontSize = 16.sp)
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C))
                ) {
                    Icon(
                        Icons.Outlined.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text("Save to Cart", fontSize = 16.sp)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFDF8F3))
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(horizontal = 16.dp)
            ) {
                if (vendor.imageResId != null) {
                    Image(
                        painter = painterResource(id = vendor.imageResId),
                        contentDescription = vendor.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(20.dp))
                    )
                } else if (vendor.imageUrl != null) {
                    AsyncImage(
                        model = vendor.imageUrl,
                        contentDescription = vendor.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(16.dp))
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFF2F2F2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Image, contentDescription = null, tint = Color.Gray)
                    }
                }

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(Icons.Filled.ChevronLeft, contentDescription = "Back")
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorite")
                }

                Text(
                    text = "1/20",
                    fontSize = 11.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                        .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    vendor.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFF5A623),
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "${vendor.rating} (${vendor.reviewCount} reviews)",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(Modifier.width(2.dp))
                    Text(
                        "${vendor.locationArea}, ${vendor.locationState}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text("From ${vendor.priceFrom}", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)

                Spacer(Modifier.height(18.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    tabs.forEach { tab ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedTab = tab },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = tab,
                                fontSize = 15.sp,
                                fontWeight = if (tab == selectedTab) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (tab == selectedTab) Color(0xFFB5722C) else Color.Gray,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                            Spacer(Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .height(2.dp)
                                    .background(if (tab == selectedTab) Color(0xFFB5722C) else Color.Transparent)
                            )
                        }
                    }
                }
                HorizontalDivider(color = Color(0xFFE8DFD3))

                Spacer(Modifier.height(18.dp))

                //VendorInfoCard + About
                if (selectedTab == "About") {
                    VendorInfoCard(
                        capacity = vendor.capacity,
                        priceRange = vendor.priceFrom,
                        highlights = vendor.highlights
                    )

                    Spacer(Modifier.height(22.dp))

                    Text(
                        "About ${vendor.name}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "A luxurious wedding venue in the heart of Penang. We provide elegant settings, halal catering and customizable packages to make your big day unforgettable.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 22.sp
                    )
                } else {
                    Text(
                        "$selectedTab content coming soon",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                //Location
                Spacer(Modifier.height(22.dp))
                Text("Location", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Icon(
                            Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = Color(0xFFB5722C),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Column {
                            Text(
                                vendor.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "${vendor.locationArea}, ${vendor.locationState}",
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("View Map", fontSize = 13.sp, color = Color(0xFFB5722C))
                        Icon(
                            Icons.Filled.ChevronRight,
                            contentDescription = null,
                            tint = Color(0xFFB5722C),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(Modifier.height(18.dp))

                AmenitiesSection(amenities = defaultAmenities)
            }
        }
    }
}

@Composable
fun AmenitiesSection(amenities: List<Amenity>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Outlined.HomeWork,
                contentDescription = null,
                tint = Color(0xFFB5722C),
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text("Amenities", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            amenities.forEach { amenity ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = amenity.icon,
                        contentDescription = amenity.label,
                        tint = Color(0xFFB5722C),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = amenity.label,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VendorInfoCardPreview() {
    MaterialTheme {
        VendorInfoCard(
            capacity = "100 - 800 pax",
            priceRange = "From RM8,800",
            highlights = "Elegant ballroom, halal catering, custom packages"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VendorDetailScreenPreview() {
    MaterialTheme {
        VendorDetailScreen(
            vendor = sampleVendors[0],
            onBackClick = {}
        )
    }
}