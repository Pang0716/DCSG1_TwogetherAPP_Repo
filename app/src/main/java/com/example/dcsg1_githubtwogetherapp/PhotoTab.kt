package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

/**
 * One row of up to two thumbnails, giving a "grid" effect.
 * This is NOT wrapped in its own LazyColumn - it's a plain Composable, called row-by-row
 * by the outer LazyColumn (in VendorDetailScreen) via items(photos.chunked(2)).
 * This avoids nesting a LazyColumn inside another LazyColumn.
 */
@Composable
fun PhotoThumbnailRow(
    photos: List<Photo>,
    onPhotoClick: (Photo) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        photos.forEach { photo ->
            AsyncImage(
                model = photo.url,
                contentDescription = "Vendor photo ${photo.id}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onPhotoClick(photo) }
            )
        }
        // If this row only has 1 photo, add an empty spacer so the grid still lines up
        if (photos.size == 1) {
            Row(modifier = Modifier.weight(1f)) {}
        }
    }
}

/**
 * Full-size preview shown after tapping a thumbnail.
 * AlertDialog + a nullable state controls visibility - same pattern as the delete
 * confirmation dialog in Practical 6: non-null selectedPhoto shows it, and
 * onDismissRequest sets it back to null to close it.
 */
@Composable
fun PhotoViewerDialog(photo: Photo, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Photo") },
        text = {
            AsyncImage(
                model = photo.url,
                contentDescription = "Vendor photo ${photo.id}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        },
        confirmButton = {
            Text(
                "Close",
                modifier = Modifier.clickable { onDismiss() }
            )
        }
    )
}