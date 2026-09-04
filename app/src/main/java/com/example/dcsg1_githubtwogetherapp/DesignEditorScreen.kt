package com.example.dcsg1_githubtwogetherapp

import android.app.Activity
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DesignEditorScreen(
    initialStyle: String = "Gold",
    onBackClick: (() -> Unit)? = null,
    onSaveClick: (CardDesign) -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dao = remember {
        AppDatabase.getInstance(context).cardDesignDao()
    }

    // Used to capture the wedding card as an image
    val graphicsLayer = rememberGraphicsLayer()

    var selectedStyle by remember {
        mutableStateOf(initialStyle)
    }

    var selectedFont by remember {
        mutableStateOf("Elegant")
    }

    var coupleNames by remember {
        mutableStateOf("")
    }

    var venue by remember {
        mutableStateOf("")
    }

    var photoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var savedDesignId by remember {
        mutableStateOf<String?>(null)
    }

    var eventDateMillis by remember {
        mutableStateOf<Long?>(null)
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var namesError by remember {
        mutableStateOf<String?>(null)
    }

    var dateError by remember {
        mutableStateOf<String?>(null)
    }

    var venueError by remember {
        mutableStateOf<String?>(null)
    }

    val eventDate = eventDateMillis?.let {
        SimpleDateFormat(
            "dd MMM yyyy",
            Locale.getDefault()
        ).format(Date(it))
    } ?: ""

    fun validateNames(value: String): String? = when {
        value.isBlank() -> "Required"

        !value.matches(
            Regex("^[a-zA-Z& ]+$")
        ) -> "Letters only (e.g. Alex & Jamie)"

        else -> null
    }

    fun validateVenue(value: String): String? = when {
        value.isBlank() -> "Required"

        !value.matches(
            Regex("^[a-zA-Z0-9,.\\- ]+$")
        ) -> "Letters and numbers only"

        else -> null
    }

    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            photoUri = uri
        }

    val styles = listOf(
        "Gold" to Color(0xFFBA7517),
        "Green" to Color(0xFF0F6E56),
        "Pink" to Color(0xFF993556),
        "Blue" to Color(0xFF185FA5)
    )

    val fonts = listOf(
        "Elegant",
        "Modern",
        "Classic"
    )

    val currentFont = when (selectedFont) {
        "Elegant" -> FontFamily.Cursive
        "Modern" -> FontFamily.SansSerif
        "Classic" -> FontFamily.Serif
        else -> FontFamily.Default
    }

    val currentStyleColor = when (selectedStyle) {
        "Gold" -> Color(0xFFBA7517)
        "Green" -> Color(0xFF0F6E56)
        "Pink" -> Color(0xFF993556)
        "Blue" -> Color(0xFF185FA5)
        else -> Color(0xFFBA7517)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAEEDA))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // =========================
        // TOP BAR
        // =========================

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    if (onBackClick != null) {
                        onBackClick()
                    } else {
                        (context as? Activity)?.finish()
                    }
                }
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                "Design",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(12.dp))

        // =========================
        // CHOOSE STYLE
        // =========================

        Text(
            "Choose a style",
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(styles) { (name, color) ->

                Column(
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(
                                color.copy(alpha = 0.25f)
                            )
                            .border(
                                width =
                                    if (selectedStyle == name)
                                        2.dp
                                    else
                                        0.5.dp,
                                color =
                                    if (selectedStyle == name)
                                        color
                                    else
                                        Color(0xFFD9C9A8),
                                shape = CircleShape
                            )
                            .clickable {
                                selectedStyle = name
                            }
                    )

                    Text(
                        name,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // =========================
        // WEDDING CARD PREVIEW
        // =========================

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(14.dp)
                )
                .background(
                    Color(0xFFFFFCF6)
                )
                .border(
                    0.5.dp,
                    Color(0xFFE8D9BC),
                    RoundedCornerShape(14.dp)
                )
                .padding(18.dp)
                .drawWithContent {

                    graphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }

                    drawLayer(graphicsLayer)
                },
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(
                        Color(0xFFFAEEDA)
                    ),
                contentAlignment =
                    Alignment.Center
            ) {

                if (photoUri != null) {

                    AsyncImage(
                        model = photoUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                } else {

                    Icon(
                        Icons.Default.Image,
                        contentDescription = null,
                        tint = Color(0xFFBA7517)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "WE ARE GETTING MARRIED",
                fontSize = 11.sp,
                letterSpacing = 1.5.sp,
                color = currentStyleColor,
                fontFamily = currentFont
            )

            Spacer(Modifier.height(8.dp))

            Text(
                coupleNames.ifBlank {
                    "Alex & Jamie"
                },
                fontSize = 22.sp,
                color = Color(0xFF412402),
                fontFamily = currentFont
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "${eventDate.ifBlank { "Dec 12, 2026" }} · ${
                    venue.ifBlank { "The Garden Hall" }
                }",
                fontSize = 12.sp,
                color = Color(0xFF854F0B),
                fontFamily = currentFont
            )
        }

        Spacer(Modifier.height(16.dp))

        // =========================
        // ADD PHOTO
        // =========================

        OutlinedButton(
            onClick = {

                photoPickerLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(
                Icons.Default.Upload,
                contentDescription = null
            )

            Spacer(Modifier.width(6.dp))

            Text("Add photo")
        }

        Spacer(Modifier.height(16.dp))

        // =========================
        // FONT STYLE
        // =========================

        Text(
            "Font style",
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(8.dp))

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            fonts.forEach { font ->

                val selected =
                    selectedFont == font

                Button(
                    onClick = {
                        selectedFont = font
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (selected)
                                Color(0xFF412402)
                            else
                                Color.White,

                        contentColor =
                            if (selected)
                                Color.White
                            else
                                Color(0xFF412402)
                    )
                ) {
                    Text(font)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // =========================
        // DETAILS
        // =========================

        Text(
            "Details",
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = coupleNames,
            onValueChange = {
                coupleNames = it
                namesError = null
            },
            placeholder = {
                Text("e.g. Alex & Jamie")
            },
            isError = namesError != null,
            supportingText = {
                if (namesError != null) {
                    Text(
                        namesError!!,
                        color = Color.Red
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Box {

            OutlinedTextField(
                value = eventDate,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                placeholder = {
                    Text("Tap to select a date")
                },
                isError = dateError != null,
                supportingText = {
                    if (dateError != null) {
                        Text(
                            dateError!!,
                            color = Color.Red
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor =
                        Color(0xFF412402),

                    disabledBorderColor =
                        if (dateError != null)
                            Color.Red
                        else
                            Color(0xFFE8D9BC),

                    disabledPlaceholderColor =
                        Color.Gray,

                    disabledLabelColor =
                        Color.Gray
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable {
                        showDatePicker = true
                    }
            )
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = venue,
            onValueChange = {
                venue = it
                venueError = null
            },
            placeholder = {
                Text("e.g. The Garden Hall")
            },
            isError = venueError != null,
            supportingText = {
                if (venueError != null) {
                    Text(
                        venueError!!,
                        color = Color.Red
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // =========================
        // SAVE DESIGN
        // =========================

        Button(
            onClick = {

                namesError =
                    validateNames(coupleNames)

                dateError =
                    if (eventDateMillis == null)
                        "Please select a date"
                    else
                        null

                venueError =
                    validateVenue(venue)

                if (
                    namesError == null &&
                    dateError == null &&
                    venueError == null
                ) {

                    val design = CardDesign(
                        coupleNames = coupleNames,
                        eventDate = eventDate,
                        venue = venue,
                        style = selectedStyle,
                        fontStyle = selectedFont,
                        photoUri = photoUri?.toString()
                    )

                    scope.launch {

                        saveCardDesign(
                            design,
                            dao
                        )

                        savedDesignId = design.id

                        onSaveClick(design)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBA7517)
            )
        ) {

            Text(
                "Save design",
                color = Color.White
            )
        }

        Spacer(Modifier.height(8.dp))

        // =========================
        // SHARE
        // =========================

        Button(
            onClick = {

                if (savedDesignId != null) {

                    scope.launch {

                        val imageBitmap =
                            graphicsLayer.toImageBitmap()

                        val bitmap =
                            imageBitmap.asAndroidBitmap()

                        shareWeddingCard(
                            context,
                            bitmap
                        )
                    }
                }
            },
            enabled = savedDesignId != null,
            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(
                Icons.Default.Share,
                contentDescription = null
            )

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

    // =========================
    // DATE PICKER
    // =========================

    if (showDatePicker) {

        val today =
            System.currentTimeMillis()

        val datePickerState =
            rememberDatePickerState(
                selectableDates =
                    object : SelectableDates {

                        override fun isSelectableDate(
                            utcTimeMillis: Long
                        ): Boolean {
                            return utcTimeMillis >= today
                        }
                    }
            )

        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        datePickerState
                            .selectedDateMillis
                            ?.let {

                                eventDateMillis = it
                                dateError = null
                            }

                        showDatePicker = false
                    }
                ) {
                    Text("Confirm")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {

            DatePicker(
                state = datePickerState
            )
        }
    }
}