package com.example.dcsg1_githubtwogetherapp

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun DesignEditorScreen(
    initialStyle: String = "Gold",
    onBackClick: () -> Unit = {},
    onSaveClick: (CardDesign) -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dao = remember { AppDatabase.getInstance(context).cardDesignDao() }

    var selectedStyle by remember { mutableStateOf(initialStyle) }
    var selectedFont by remember { mutableStateOf("Elegant") }
    var coupleNames by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var venue by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var savedDesignId by remember { mutableStateOf<String?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> photoUri = uri }

    val styles = listOf(
        "Gold" to Color(0xFFBA7517),
        "Green" to Color(0xFF0F6E56),
        "Pink" to Color(0xFF993556),
        "Blue" to Color(0xFF185FA5)
    )
    val fonts = listOf("Elegant", "Modern", "Classic")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAEEDA))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text("Design", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }

        Spacer(Modifier.height(12.dp))

        Text("Choose a style", fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(styles) { (name, color) ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(color.copy(alpha = 0.25f))
                            .border(
                                width = if (selectedStyle == name) 2.dp else 0.5.dp,
                                color = if (selectedStyle == name) color else Color(0xFFD9C9A8),
                                shape = CircleShape
                            )
                            .clickable { selectedStyle = name }
                    )
                    Text(name, fontSize = 11.sp)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFFFFFCF6))
                .border(0.5.dp, Color(0xFFE8D9BC), RoundedCornerShape(14.dp))
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFAEEDA)),
                contentAlignment = Alignment.Center
            ) {
                if (photoUri != null) {
                    AsyncImage(model = photoUri, contentDescription = null, modifier = Modifier.fillMaxSize())
                } else {
                    Icon(Icons.Default.Image, contentDescription = null, tint = Color(0xFFBA7517))
                }
            }
            Spacer(Modifier.height(12.dp))
            Text("WE ARE GETTING MARRIED", fontSize = 11.sp, letterSpacing = 1.5.sp, color = Color(0xFF854F0B))
            Spacer(Modifier.height(8.dp))
            Text(coupleNames.ifBlank { "Alex & Jamie" }, fontSize = 22.sp, color = Color(0xFF412402))
            Spacer(Modifier.height(8.dp))
            Text(
                "${eventDate.ifBlank { "Dec 12, 2026" }} · ${venue.ifBlank { "The Garden Hall" }}",
                fontSize = 12.sp,
                color = Color(0xFF854F0B)
            )
        }

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Upload, contentDescription = null)
            Spacer(Modifier.width(6.dp))
            Text("Add photo")
        }

        Spacer(Modifier.height(16.dp))

        Text("Font style", fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            fonts.forEach { font ->
                val selected = selectedFont == font
                Button(
                    onClick = { selectedFont = font },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selected) Color(0xFF412402) else Color.White,
                        contentColor = if (selected) Color.White else Color(0xFF412402)
                    )
                ) { Text(font) }
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Details", fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = coupleNames, onValueChange = { coupleNames = it },
            placeholder = { Text("e.g. Alex & Jamie") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = eventDate, onValueChange = { eventDate = it },
            placeholder = { Text("e.g. Dec 12, 2026") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = venue, onValueChange = { venue = it },
            placeholder = { Text("e.g. The Garden Hall") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val design = CardDesign(
                    coupleNames = coupleNames,
                    eventDate = eventDate,
                    venue = venue,
                    style = selectedStyle,
                    fontStyle = selectedFont,
                    photoUri = photoUri?.toString()
                )
                scope.launch {
                    saveCardDesign(design, dao)
                    savedDesignId = design.id
                    onSaveClick(design)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA7517))
        ) {
            Text("Save design", color = Color.White)
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { savedDesignId?.let { shareCardLink(context, it) } },
            enabled = savedDesignId != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Share, contentDescription = null)
            Spacer(Modifier.width(6.dp))
            Text("Share")
        }

        if (savedDesignId == null) {
            Text(
                "Save your design first to unlock sharing",
                fontSize = 11.sp,
                color = Color(0xFF9C8A66),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

