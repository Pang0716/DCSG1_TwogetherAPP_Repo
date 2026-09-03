package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

@Composable
fun VendorCard(
    vendor: Vendor,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE8DFD3), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (vendor.imageResId != null) {
            Image(
                painter = painterResource(id = vendor.imageResId),
                contentDescription = vendor.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxHeight().width(90.dp).clip(RoundedCornerShape(12.dp))
            )
        } else if (vendor.imageUrl != null) {
            AsyncImage(
                model = vendor.imageUrl,
                contentDescription = vendor.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(110.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(110.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Image,
                    contentDescription = null,
                    tint = Color.Gray,
                )
            }
        }

        Spacer(Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = vendor.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = vendor.category,
                fontSize = 12.sp,
                color = Color(0xFFB5722C)
            )
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0xFFF5A623),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "${vendor.rating} (${vendor.reviewCount} reviews)",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Spacer(Modifier.height(2.dp))
            Text(
                text = "From ${vendor.priceFrom}",
                fontSize = 13.sp,
                color = Color.Black
            )
        }

        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = "View details",
            tint = Color.Black,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun FeaturedVendorCard(
    vendor: Vendor,
    isFavorited: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE8DFD3), RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
        ) {
            if (vendor.imageResId != null) {
                Image(
                    painter = painterResource(id = vendor.imageResId),
                    contentDescription = vendor.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
            } else if (vendor.imageUrl != null) {
                AsyncImage(
                    model = vendor.imageUrl,
                    contentDescription = vendor.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color(0xFFF2F2F2)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Image, contentDescription = null, tint = Color.Gray)
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .size(26.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(Color.White)
                    .clickable { onFavoriteClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isFavorited) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorited) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorited) Color(0xFFE24B4A) else Color.Black,
                    modifier = Modifier.size(15.dp)
                )
            }
        }

        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = vendor.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                maxLines = 1
            )
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFF5A623), modifier = Modifier.size(12.dp))
                Spacer(Modifier.width(3.dp))
                Text("${vendor.rating} (${vendor.reviewCount} reviews)", fontSize = 11.sp, color = Color.Gray)
            }
            Spacer(Modifier.height(3.dp))
            Text("From ${vendor.priceFrom}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Composable
fun BrowseVendorsScreen(
    vendors: List<Vendor>,
    onVendorClick: (Vendor) -> Unit,
    onBackClick: () -> Unit,
    initialCategory: String = "All",
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    val categories = listOf("All", "Venue", "Photographer", "Makeup", "Live Band", "Emcee", "Attire","Deco")
    var selectedCategory by remember { mutableStateOf(initialCategory) }
    // Was: var selectedState by remember { mutableStateOf("Penang") } - that reset to
    // "Penang" every time this screen got recreated (e.g. navigating into
    // VendorDetailScreen and back). Now reads/writes VendorFilterSession instead, same
    // "hold it in a global object" pattern as CartSession/BudgetSession/UserSession, so
    // the selected state survives navigating away and back.
    var selectedState by VendorFilterSession.selectedState
    val filteredVendors = vendors.filter { vendor ->
        val matchesState = vendor.locationState == selectedState
        val matchesCategory = selectedCategory == "All" || vendor.category == selectedCategory
        val matchesSearch = query.isBlank() || vendor.name.contains(query, ignoreCase = true)
        matchesState && matchesCategory && matchesSearch
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFDF8F3))
    ) {
        BrowseVendorsTopBar(onBackClick = onBackClick)
        VendorSearchBar(query = query, onQueryChange = { query = it })

        Spacer(Modifier.height(8.dp))

        LocationSelector(
            selectedState = selectedState,
            onStateChosen = { state ->
                selectedState = state
            }
        )

        Spacer(Modifier.height(8.dp))

        CategoryFilterRow(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredVendors, key = { it.name }) { vendor ->
                VendorCard(vendor = vendor, onClick = { onVendorClick(vendor) })
            }
        }
    }
}

@Composable
fun BrowseVendorsTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Browse Vendors",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun VendorSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFF7F7F7))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(24.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            if (query.isEmpty()) {
                Text(
                    text = "Search vendors, services...",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    lineHeight = 16.sp
                )
            }
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 16.sp,
                    color = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CategoryChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isSelected) Color(0xFFB5722C) else Color.White
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun CategoryFilterRow(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            CategoryChip(
                label = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Preview
@Composable
fun VendorCardPreview(){
    MaterialTheme{
        VendorCard(
            vendor = Vendor(
                name = "The Light Hotel Penang",
                category = "Venue",
                rating = 4.9,
                reviewCount = 128,
                priceFrom = "RM45,900",
                imageUrl = null,
                locationArea = "George Town",
                locationState = "Penang"
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BrowseVendorsScreenPreview() {
    MaterialTheme {
        BrowseVendorsScreen(
            vendors = sampleVendors,
            onVendorClick = {},
            onBackClick = {}
        )
    }
}