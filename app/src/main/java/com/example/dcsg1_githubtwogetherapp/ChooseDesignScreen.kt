package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DesignStyle(
    val id: String,
    val label: String,
    val colors: List<Color>,
    val emoji: String,
    val popular: Boolean = false
)

@Composable
fun ChooseDesignScreen(
    onBackClick: () -> Unit = {},
    onStyleSelected: (String) -> Unit = {}
) {
    val styles = listOf(
        DesignStyle("Gold", "Gold Elegance", listOf(Color(0xFFF5E3C4), Color(0xFFEAC98A)), "❤️", popular = true),
        DesignStyle("Green", "Sage Garden", listOf(Color(0xFFDCEFE6), Color(0xFFB7DFCB)), "🍃"),
        DesignStyle("Pink", "Rose Blush", listOf(Color(0xFFF4E0EA), Color(0xFFEBC0D5)), "🌸"),
        DesignStyle("Blue", "Ocean Mist", listOf(Color(0xFFDCE9F5), Color(0xFFB7D6EF)), "🌊")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAEEDA))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                "Choose Design",
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(6.dp))
        Text(
            "Pick a style to start customizing",
            fontSize = 12.sp,
            color = Color(0xFF9C8A66),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(styles) { style ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onStyleSelected(style.id) }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Brush.linearGradient(style.colors)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(style.emoji, fontSize = 22.sp)
                        if (style.popular) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(6.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFBA7517))
                                    .padding(horizontal = 7.dp, vertical = 2.dp)
                            ) {
                                Text("Popular", fontSize = 9.sp, color = Color.White)
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        style.label,
                        fontSize = 12.5.sp,
                        fontWeight = if (style.popular) FontWeight.SemiBold else FontWeight.Normal,
                        color = Color(0xFF412402)
                    )
                }
            }
        }

        Spacer(Modifier.height(26.dp))
        Text(
            "Tap a style to open the customizer",
            fontSize = 11.sp,
            color = Color(0xFF9C8A66),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

