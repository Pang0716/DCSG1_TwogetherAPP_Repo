package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

private val AccentColor = Color(0xFFB5722C)
private val CardBorderColor = Color(0xFFE8DFD3)
private val TagChipBg = Color(0xFFF5E9D9)
private val TagChipText = Color(0xFF7A4E1D)

/**
 * Horizontal layout package card, display-only, not clickable.
 * imageResId takes priority (local resource) -> imageUrl (remote image) -> placeholder icon.
 * All three cards use the same orange border; "Most Popular" is shown via a badge instead of the border.
 */
@Composable
fun PackageCard(
    pkg: PackageOption,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(
                width = 1.5.dp,
                color = AccentColor,
                shape = RoundedCornerShape(16.dp)
            )
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        pkg.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (pkg.isPopular) {
                        Spacer(Modifier.width(8.dp))
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

                Text(
                    pkg.price,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentColor
                )
                Spacer(Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(pkg.capacity, fontSize = 12.sp, color = Color.Gray)
                }
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
                        Text(
                            tag,
                            fontSize = 11.sp,
                            color = Color.DarkGray,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}

/** Picks a fitting icon based on keywords in the tag; falls back to a gift icon otherwise. */
private fun tagIcon(tag: String): ImageVector {
    val lower = tag.lowercase()
    return when {
        "catering" in lower || "food" in lower -> Icons.Outlined.Restaurant
        "ballroom" in lower || "décor" in lower || "decor" in lower -> Icons.Outlined.WorkspacePremium
        else -> Icons.Filled.CardGiftcard
    }
}

@Composable
private fun PackageImage(pkg: PackageOption, modifier: Modifier = Modifier) {
    when {
        pkg.imageResId != null -> {
            Image(
                painter = painterResource(id = pkg.imageResId),
                contentDescription = pkg.name,
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        }
        pkg.imageUrl != null -> {
            AsyncImage(
                model = pkg.imageUrl,
                contentDescription = pkg.name,
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