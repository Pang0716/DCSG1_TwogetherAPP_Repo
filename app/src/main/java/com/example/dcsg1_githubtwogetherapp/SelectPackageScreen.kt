package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

/**
 * Content shown inside the ModalBottomSheet triggered by "Save to Cart" on VendorDetailScreen.
 * No Scaffold/topBar here on purpose - the bottom sheet itself already provides the drag
 * handle and the swipe-down-to-dismiss / tap-outside-to-dismiss gestures, so a back button
 * and its own app bar would be redundant.
 */
@Composable
fun SelectPackageSheetContent(
    vendor: Vendor,
    packages: List<PackageOption>,
    onContinueClick: (PackageOption) -> Unit,
    modifier: Modifier = Modifier
) {
    // Default to whichever package is marked isPopular, otherwise just the first one
    var selectedIndex by remember {
        mutableStateOf(packages.indexOfFirst { it.isPopular }.let { if (it >= 0) it else 0 })
    }
    val selectedPackage = packages.getOrNull(selectedIndex)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
    ) {
        Column(
            modifier = Modifier
                .weight(1f, fill = false)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                VendorThumbnail(
                    vendor = vendor,
                    modifier = Modifier.size(56.dp).clip(RoundedCornerShape(12.dp))
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(vendor.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFF5A623),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "${vendor.rating} (${vendor.reviewCount} reviews)",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "${vendor.locationArea}, ${vendor.locationState}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(Modifier.height(18.dp))
            Text("Choose a Wedding Package", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(
                "Find the perfect package for your special day.",
                fontSize = 13.sp,
                color = Color.Gray
            )
            Spacer(Modifier.height(12.dp))

            packages.forEachIndexed { index, pkg ->
                SelectablePackageCard(
                    pkg = pkg,
                    selected = index == selectedIndex,
                    onSelect = { selectedIndex = index }
                )
            }
            Spacer(Modifier.height(8.dp))
        }

        if (selectedPackage != null) {
            HorizontalDivider(color = CardBorderColor)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("From", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        selectedPackage.price,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentColor
                    )
                    Text(selectedPackage.capacity, fontSize = 12.sp, color = Color.Gray)
                }
                Button(
                    onClick = { onContinueClick(selectedPackage) },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentColor),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.height(50.dp)
                ) {
                    Text("Continue")
                    Spacer(Modifier.width(2.dp))
                    Icon(Icons.Filled.ChevronRight, contentDescription = null)
                }
            }
        }
    }
}

/**
 * Same visual layout as the display-only PackageCard in PackageCard.kt, but clickable and
 * with a RadioButton - this is the one place in the app where picking a package actually
 * matters, so the selection UI belongs here rather than on the read-only Packages Tab.
 */
@Composable
private fun SelectablePackageCard(
    pkg: PackageOption,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(
                width = if (selected) 1.5.dp else 1.dp,
                color = if (selected) AccentColor else CardBorderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onSelect() }
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            PackageImage(
                pkg = pkg,
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            pkg.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        if (pkg.isPopular) {
                            Spacer(Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(TagChipBg)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text("Most Popular", fontSize = 10.sp, color = TagChipText, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                    RadioButton(
                        selected = selected,
                        onClick = onSelect,
                        colors = RadioButtonDefaults.colors(selectedColor = AccentColor)
                    )
                }
                Text(pkg.price, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AccentColor)
                Spacer(Modifier.height(2.dp))
                Text(pkg.capacity, fontSize = 12.sp, color = Color.Gray)
            }
        }

        if (pkg.tags.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            HorizontalDivider(color = CardBorderColor)
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                pkg.tags.forEach { tag ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            tagIcon(tag),
                            contentDescription = null,
                            tint = AccentColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(tag, fontSize = 11.sp, color = Color.DarkGray, maxLines = 2)
                    }
                }
            }
        }
    }
}

/** Small vendor thumbnail, same imageResId -> imageUrl -> placeholder fallback used elsewhere. */
@Composable
private fun VendorThumbnail(vendor: Vendor, modifier: Modifier = Modifier) {
    when {
        vendor.imageResId != null -> {
            Image(
                painter = painterResource(id = vendor.imageResId),
                contentDescription = vendor.name,
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        }
        vendor.imageUrl != null -> {
            AsyncImage(
                model = vendor.imageUrl,
                contentDescription = vendor.name,
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        }
        else -> {
            Box(
                modifier = modifier.background(Color(0xFFF2F2F2)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Image, contentDescription = null, tint = Color.Gray)
            }
        }
    }
}