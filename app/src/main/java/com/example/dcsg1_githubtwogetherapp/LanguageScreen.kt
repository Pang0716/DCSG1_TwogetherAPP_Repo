package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object AppLanguage {
    val selected = mutableStateOf("English")
}

@Composable
fun LanguageScreen(onBackClick: () -> Unit) {
    val languages = listOf("English", "Bahasa Malaysia", "中文")

    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Spacer(modifier = Modifier.height(20.dp))
        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(24.dp).clickable { onBackClick() })
        Spacer(modifier = Modifier.height(16.dp))
        Text("Language", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(20.dp))

        languages.forEach { lang ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { AppLanguage.selected.value = lang }
                    .padding(vertical = 14.dp)
            ) {
                Text(lang, fontSize = 14.sp, color = Color.Black, modifier = Modifier.weight(1f))
                if (AppLanguage.selected.value == lang) {
                    Icon(Icons.Filled.Check, contentDescription = null, tint = Color(0xFFB5722C))
                }
            }
            HorizontalDivider(color = Color(0xFFF0F0F0))
        }
    }
}