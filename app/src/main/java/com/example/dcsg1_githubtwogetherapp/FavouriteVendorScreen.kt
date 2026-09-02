package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * "My Favorites" list screen. Structured similarly to BrowseVendorsScreen (reuses
 * LazyColumn + VendorCard). The difference is the data source: this first queries
 * Supabase for which vendor names this user has favorited (fetchAllFavorites),
 * then looks up the full Vendor data locally by name.
 */
@Composable
fun FavoriteVendorsScreen(
    userId: String?,
    onVendorClick: (Vendor) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }
    var favoriteVendors by remember { mutableStateOf<List<Vendor>>(emptyList()) }

    LaunchedEffect(userId) {
        if (userId == null) {
            isLoading = false
        } else {
            isLoading = true
            try {
                val favorites = withContext(Dispatchers.IO) { fetchAllFavorites(userId) }
                // Look up the full Vendor for each favorited vendor name in local sampleVendors
                // mapNotNull: if a name isn't found in sampleVendors (e.g. the vendor was
                // removed), just skip it instead of crashing the whole list
                favoriteVendors = favorites.mapNotNull { fav ->
                    sampleVendors.find { it.name == fav.vendorname }
                }
            } catch (e: Exception) {
                favoriteVendors = emptyList()
            }
            isLoading = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFDF8F3))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                "My Favorites",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFB5722C))
                }
            }
            userId == null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Log in to see your favorite vendors",
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
            favoriteVendors.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "You haven't favorited any vendors yet",
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(favoriteVendors, key = { it.name }) { vendor ->
                        // VendorCard is already defined in VendorCard.kt, reused directly since it's the same package
                        VendorCard(vendor = vendor, onClick = { onVendorClick(vendor) })
                    }
                }
            }
        }
    }
}