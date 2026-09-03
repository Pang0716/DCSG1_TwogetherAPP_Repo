package com.example.dcsg1_githubtwogetherapp

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Link
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// ===== Reviews =====
// Reviews removed for now, will add back later

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
fun VendorDetailTopBar(
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onCopyLinkClick: () -> Unit,
    isFavorited: Boolean,
    onFavoriteClick: () -> Unit
) {
    var showShareMenu by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFDF8F3))
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .padding(top = 30.dp)
    ) {
        Text(
            "Vendor Details",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center)
        )
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                IconButton(
                    onClick = { showShareMenu = true },
                    modifier = Modifier.size(38.dp)
                ) {
                    Icon(
                        Icons.Filled.Share,
                        contentDescription = "Share",
                        modifier = Modifier.size(20.dp)
                    )
                }
                DropdownMenu(
                    expanded = showShareMenu,
                    onDismissRequest = { showShareMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Share via...") },
                        onClick = {
                            showShareMenu = false
                            onShareClick()
                        },
                        leadingIcon = { Icon(Icons.Filled.Share, contentDescription = null) }
                    )
                    DropdownMenuItem(
                        text = { Text("Copy link") },
                        onClick = {
                            showShareMenu = false
                            onCopyLinkClick()
                        },
                        leadingIcon = { Icon(Icons.Filled.Link, contentDescription = null) }
                    )
                }
            }
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.size(38.dp)
            ) {
                Icon(
                    if (isFavorited) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorited) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorited) Color(0xFFE24B4A) else Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VendorDetailScreen(
    vendor: Vendor,
    onBackClick: () -> Unit,
    isLoggedIn: Boolean,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("About") }
    val tabs = listOf("About", "Packages", "Photos", "Reviews")
    val packages = remember { generatePackages(vendor) }
    val photos = remember { generatePhotos(vendor) }
    val reviews = remember { mutableStateListOf<Review>() }
    var selectedPhoto by remember { mutableStateOf<Photo?>(null) }
    var reviewsLoadFailed by remember { mutableStateOf(false) }
    var reviewSubmitError by remember { mutableStateOf<String?>(null) }
    var showPackageSelection by remember { mutableStateOf(false) }
    var favoriteRecord by remember { mutableStateOf<SupabaseFavorite?>(null) }
    var showLoginDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Loaded along with favorite status: if no logged-in user id (not logged in), skip and favoriteRecord stays null
    LaunchedEffect(vendor.name) {
        val userId = UserSession.currentUser.value?.id
        if (userId != null) {
            try {
                favoriteRecord = withContext(Dispatchers.IO) { fetchFavorite(userId, vendor.name) }
            } catch (e: Exception) {
                android.util.Log.e("VendorDetailScreen", "fetchFavorite failed for ${vendor.name}", e)
                favoriteRecord = null
            }
        }
    }

    // Same structure as fetchUsers() in Practical 9: fetch reviews from the database when the
    // screen first appears (or the vendor changes).
    // try/catch prevents the whole screen from crashing on no network/query failure - falls
    // back to local sample data on failure.
    LaunchedEffect(vendor.name) {
        try {
            val fetched = withContext(Dispatchers.IO) { fetchReviews(vendor.name) }
            reviews.clear()
            reviews.addAll(fetched)
            reviewsLoadFailed = false
        } catch (e: Exception) {
            android.util.Log.e("VendorDetailScreen", "fetchReviews failed for ${vendor.name}", e)
            reviews.clear()
            reviews.addAll(generateReviews(vendor))
            reviewsLoadFailed = true
        }
    }

    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        containerColor = Color(0xFFFDF8F3),
        topBar = {
            VendorDetailTopBar(
                onBackClick = onBackClick,
                onShareClick = {
                    // Points to the deployed vendor.html preview page for this specific
                    // vendor. Doesn't auto-open the app even if it's installed (that needs
                    // Android App Links domain verification, out of scope for now) -
                    // anyone who clicks it just sees the web preview of this one vendor.
                    val vendorLink = "https://magenta-cat-6febc8.netlify.app/vendor.html?name=${android.net.Uri.encode(vendor.name)}"
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Check out ${vendor.name} on Twogether! From ${vendor.priceFrom}.\n$vendorLink"
                        )
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                },
                onCopyLinkClick = {
                    val vendorLink = "https://magenta-cat-6febc8.netlify.app/vendor.html?name=${android.net.Uri.encode(vendor.name)}"
                    val clipboardManager = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                    val clipData = android.content.ClipData.newPlainText("Vendor link", vendorLink)
                    clipboardManager.setPrimaryClip(clipData)
                    android.widget.Toast.makeText(context, "Link copied to clipboard", android.widget.Toast.LENGTH_SHORT).show()
                },
                isFavorited = favoriteRecord != null,
                onFavoriteClick = {
                    if (!isLoggedIn) {
                        showLoginDialog = true
                    } else {
                        val userId = UserSession.currentUser.value?.id
                        if (userId != null) {
                            val current = favoriteRecord
                            scope.launch {
                                try {
                                    if (current != null) {
                                        // Already favorited -> unfavorite
                                        withContext(Dispatchers.IO) { removeFavorite(current.id) }
                                        favoriteRecord = null
                                    } else {
                                        // Not favorited -> favorite it, then query again to get the database-generated id
                                        withContext(Dispatchers.IO) { addFavorite(userId, vendor.name) }
                                        favoriteRecord = withContext(Dispatchers.IO) { fetchFavorite(userId, vendor.name) }
                                    }
                                } catch (e: Exception) {
                                    android.util.Log.e("VendorDetailScreen", "favorite toggle failed for ${vendor.name}", e)
                                    // Network failure - leave the state unchanged, heart icon stays as is
                                }
                            }
                        }
                    }
                }
            )
        },
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
                    onClick = {
                        if (isLoggedIn) {
                            showPackageSelection = true
                        } else {
                            showLoginDialog = true
                        }
                    },
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
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFDF8F3))
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp)
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

                    // This was originally a Brush.verticalGradient overlay; Brush wasn't covered
                    // in the course, so it's replaced with a flat semi-transparent overlay -
                    // less refined-looking but functionally the same (keeps the bottom text readable)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart)
                            .height(90.dp)
                            .background(Color.Black.copy(alpha = 0.45f))
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
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
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
                    Spacer(Modifier.height(10.dp))
                }
            }

            when (selectedTab) {
                "About" -> {
                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
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
                                generateAboutDescription(vendor),
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable {
                                        // Only search by area + state (real place names), not the
                                        // vendor name itself — vendor names in this dataset are
                                        // placeholder/demo data and searching a made-up business
                                        // name could return "not found" or an unrelated real place
                                        val query = "${vendor.locationArea}, ${vendor.locationState}"
                                        val mapsIntent = Intent(
                                            Intent.ACTION_VIEW,
                                            android.net.Uri.parse(
                                                "https://www.google.com/maps/search/?api=1&query=${android.net.Uri.encode(query)}"
                                            )
                                        )
                                        context.startActivity(mapsIntent)
                                    }
                                ) {
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

                            AmenitiesSection(amenities = generateAmenities(vendor))
                        }
                    }
                }

                "Packages" -> {
                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Text(
                                "Choose the perfect package",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "All packages include venue rental and standard setup.",
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                            Spacer(Modifier.height(14.dp))
                        }
                    }
                    items(packages) { pkg ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            PackageCard(pkg = pkg)
                        }
                    }
                }

                "Photos" -> {
                    if (photos.isEmpty()) {
                        item {
                            Text(
                                "No photos yet",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else {
                        items(photos.chunked(2)) { rowPhotos ->
                            PhotoThumbnailRow(
                                photos = rowPhotos,
                                onPhotoClick = { selectedPhoto = it },
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                "Reviews" -> {
                    if (reviewsLoadFailed) {
                        item {
                            Text(
                                "Couldn't load the latest reviews — showing sample data. Check your connection and reopen this page.",
                                fontSize = 12.sp,
                                color = Color(0xFFB5722C),
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                    item {
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            AddReviewForm(
                                // Fallback name if not logged in, to avoid showing null
                                reviewerName = UserSession.currentUser.value?.fullName ?: "Guest",
                                submitError = reviewSubmitError,
                                onSubmit = { rating, comment ->
                                    if (!isLoggedIn) {
                                        showLoginDialog = true
                                    } else {
                                        val reviewerName = UserSession.currentUser.value?.fullName ?: "Guest"
                                        reviewSubmitError = null
                                        scope.launch {
                                            try {
                                                // Same as addUser() in Practical 9:
                                                // after inserting, the database sends back the row with its real id,
                                                // use that directly as the new review to show, no need to compute nextId ourselves
                                                val inserted = withContext(Dispatchers.IO) {
                                                    insertReview(
                                                        vendorName = vendor.name,
                                                        reviewerName = reviewerName,
                                                        rating = rating,
                                                        comment = comment
                                                    )
                                                }
                                                reviews.add(inserted)
                                            } catch (e: Exception) {
                                                // Submit failed (e.g. no network) - show it instead of failing silently
                                                reviewSubmitError = "Couldn't submit your review. Please check your connection and try again."
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                    if (reviews.isEmpty()) {
                        item {
                            Text(
                                "No reviews yet",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else {
                        items(reviews, key = { it.id }) { review ->
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                ReviewRow(review = review)
                            }
                        }
                    }
                }

                else -> {
                    item {
                        Text(
                            "$selectedTab content coming soon",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }

    selectedPhoto?.let { photo ->
        PhotoViewerDialog(photo = photo, onDismiss = { selectedPhoto = null })
    }

    if (showLoginDialog) {
        LoginRequiredDialog(
            onDismiss = { showLoginDialog = false },
            onLoginClick = {
                showLoginDialog = false
                onNavigateToLogin()
            }
        )
    }

    if (showPackageSelection) {
        ModalBottomSheet(
            onDismissRequest = { showPackageSelection = false },
            sheetState = sheetState
        ) {
            SelectPackageSheetContent(
                vendor = vendor,
                packages = packages,
                onContinueClick = { selectedPackage ->
                    if (!isLoggedIn) {
                        showLoginDialog = true
                        showPackageSelection = false
                    } else {
                        CartSession.addVendor(vendor, selectedPackage)
                        // This part (CartSession.addVendor) only updates the in-memory list -
                        // it doesn't survive a reload from CartRepository (e.g. HomeScreen's
                        // LaunchedEffect(isLoggedIn) calling CartRepository.loadCart). Need to
                        // actually persist it too, same saveCartItem() the rest of the cart
                        // flow uses (Supabase + local Room, handled inside that function).
                        val userId = UserSession.currentUser.value?.id
                        if (userId != null) {
                            scope.launch {
                                withContext(Dispatchers.IO) {
                                    CartRepository.saveCartItem(
                                        context = context,
                                        userId = userId,
                                        vendorName = vendor.name,
                                        packageName = selectedPackage.name,
                                        isChecked = true
                                    )
                                }
                            }
                        }
                        // Toast isn't from the Practicals but it's about as standard as Android
                        // gets for a quick one-off confirmation, same idea as the
                        // Intent.ACTION_SEND share sheet added earlier
                        android.widget.Toast.makeText(
                            context,
                            "${selectedPackage.name} added to cart",
                            android.widget.Toast.LENGTH_SHORT
                        ).show()
                        showPackageSelection = false
                    }
                }
            )
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

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(amenities) { amenity ->
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
            onBackClick = {},
            isLoggedIn = false,
            onNavigateToLogin = {}
        )
    }
}