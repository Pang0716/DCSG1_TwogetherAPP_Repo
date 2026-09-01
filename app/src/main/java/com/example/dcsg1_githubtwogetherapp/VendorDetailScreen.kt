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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// ===== 套餐数据结构 =====
data class PackageOption(
    val name: String,
    val price: String,
    val items: List<String>
)

fun generatePackages(vendor: Vendor): List<PackageOption> {
    val basePrice = vendor.priceFrom.filter { it.isDigit() }.toIntOrNull() ?: 1000
    return listOf(
        PackageOption(
            name = "Basic",
            price = "RM$basePrice",
            items = listOf("Standard service", "Basic consultation", "1 revision")
        ),
        PackageOption(
            name = "Standard",
            price = "RM${(basePrice * 1.5).toInt()}",
            items = listOf("Extended service", "Priority consultation", "3 revisions", "Extra add-ons")
        ),
        PackageOption(
            name = "Premium",
            price = "RM${(basePrice * 2).toInt()}",
            items = listOf(
                "Full premium service",
                "Unlimited consultation",
                "Unlimited revisions",
                "All add-ons included",
                "Priority support"
            )
        )
    )
}

// ===== Reviews 数据结构 =====
data class SampleReview(val name: String, val rating: Int, val comment: String)

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
            .background(Color(0xFFFDF8F3))
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Text("Vendor Details", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(Icons.Filled.Share, contentDescription = "Share")
            Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorite")   // 加回这一行
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

    var showPackageDialog by remember { mutableStateOf(false) }
    var selectedPackage by remember { mutableStateOf<PackageOption?>(null) }
    val packages = remember(vendor) { generatePackages(vendor) }

    Scaffold(
        containerColor = Color(0xFFFDF8F3),
        topBar = { VendorDetailTopBar(onBackClick = onBackClick) },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFDF8F3))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
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
                    onClick = { showPackageDialog = true },
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
                    .padding(16.dp)
                    .height(240.dp)
                    .clip(RoundedCornerShape(24.dp))
            ) {
                if (vendor.imageResId != null) {
                    Image(
                        painter = painterResource(id = vendor.imageResId),
                        contentDescription = vendor.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else if (vendor.imageUrl != null) {
                    AsyncImage(
                        model = vendor.imageUrl,
                        contentDescription = vendor.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color(0xFFF2F2F2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Image, contentDescription = null, tint = Color.Gray)
                    }
                }

                // 底部渐变，让文字更清晰
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                                startY = 300f
                            )
                        )
                )

                Text(
                    text = "1/10",
                    fontSize = 11.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        vendor.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFF5A623),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "${vendor.rating} (${vendor.reviewCount} reviews)",
                            fontSize = 13.sp,
                            color = Color.White
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "• ${vendor.locationArea}, ${vendor.locationState}",
                            fontSize = 13.sp,
                            color = Color.White
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {

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
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(if (tab == selectedTab) Color(0xFFB5722C) else Color.Transparent)
                            )
                        }
                    }
                }
                HorizontalDivider(color = Color(0xFFE8DFD3))

                Spacer(Modifier.height(18.dp))

                when (selectedTab) {
                    "About" -> {
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
                                    Text(vendor.name, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
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

                    "Packages" -> {
                        packages.forEach { pkg ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color(0xFFFDF8F3))
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(pkg.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                    Text(
                                        pkg.price,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFB5722C)
                                    )
                                }
                                Spacer(Modifier.height(8.dp))
                                pkg.items.forEach { item ->
                                    Text(
                                        "• $item",
                                        fontSize = 13.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    "Photos" -> {
                        PhotosGrid()
                    }

                    "Reviews" -> {
                        ReviewsSection()
                    }
                }
            }
        }
    }

    // ===== 选套餐弹窗 =====
    if (showPackageDialog) {
        AlertDialog(
            onDismissRequest = { showPackageDialog = false },
            title = { Text("Select a Package") },
            text = {
                Column {
                    packages.forEach { pkg ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (selectedPackage == pkg),
                                    onClick = { selectedPackage = pkg },
                                    role = Role.RadioButton
                                )
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = (selectedPackage == pkg), onClick = null)
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text("${pkg.name} - ${pkg.price}", fontWeight = FontWeight.SemiBold)
                                Text(pkg.items.joinToString(", "), fontSize = 11.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPackageDialog = false
                        selectedPackage = null
                    },
                    enabled = selectedPackage != null
                ) {
                    Text("Add to Cart")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPackageDialog = false; selectedPackage = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun PhotosGrid() {
    val columns = 3
    val photoCount = 9
    Column {
        for (row in 0 until (photoCount / columns)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                for (col in 0 until columns) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF2F2F2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Image, contentDescription = null, tint = Color.Gray)
                    }
                }
            }
            Spacer(Modifier.height(6.dp))
        }
    }
}

@Composable
fun ReviewsSection() {
    val reviews = listOf(
        SampleReview("Aisha R.", 5, "Amazing service, everything was exactly as promised. Highly recommend!"),
        SampleReview("Wei Ming T.", 4, "Great experience overall, minor delays but staff were very responsive."),
        SampleReview("Nur Hafiza", 5, "Absolutely loved working with them for our big day. Will recommend to friends.")
    )
    Column {
        reviews.forEach { review ->
            Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(review.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Spacer(Modifier.width(8.dp))
                    repeat(review.rating) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFF5A623),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(review.comment, fontSize = 13.sp, color = Color.Gray)
            }
            HorizontalDivider(color = Color(0xFFE8DFD3))
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