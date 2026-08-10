package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dcsg1_githubtwogetherapp.ui.theme.SplashBackground
import com.example.dcsg1_githubtwogetherapp.ui.theme.SplashGold
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1800)
        onTimeout()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // TODO: replace with your rings+heart icon (Image composable)
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Twogether logo",
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = "Twogether",
            fontFamily = FontFamily.Serif,
            fontSize = 34.sp,
            color = SplashGold
        )

        val goldGradientLeft = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFF4E7D4),
                Color(0xFFBE863A)
            )
        )

        val goldGradientRight = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFBE863A),
                Color(0xFFF4E7D4)
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        ) {
            // left line
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(1.5.dp)
                    .background(goldGradientLeft)
            )

            // heart in the middle
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = Color(0xFFD5B17F),
                modifier = Modifier.size(14.dp)
            )

            // right line
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(1.5.dp)
                    .background(goldGradientRight)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Plan • Connect • Celebrate",
            fontSize = 13.sp,
            color = SplashGold
        )
    }
}