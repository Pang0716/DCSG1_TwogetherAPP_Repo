package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.layout.statusBarsPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationListScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    var notifications by remember { mutableStateOf<List<NotificationEntity>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val userId = UserSession.currentUser.value?.id
        if (userId != null) {
            notifications = NotificationRepository.loadAll(context, userId)
            NotificationRepository.markAllRead(context, userId)
        }
        isLoading = false
    }

    Scaffold(
        containerColor = Color(0xFFFDF8F3),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.notifications_title), fontWeight = FontWeight.Bold, color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFDF8F3))
            )
        }
    ) { innerPadding ->
        when {
            isLoading -> {
                Box(modifier = Modifier.padding(innerPadding).fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFB5722C))
                }
            }
            notifications.isEmpty() -> {
                Column(
                    modifier = Modifier.padding(innerPadding).fillMaxSize().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Outlined.NotificationsNone, contentDescription = null, tint = Color(0xFFB5722C), modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(stringResource(R.string.no_notifications_yet), fontSize = 15.sp, color = Color.Gray)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding).fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(notifications) { _, notification ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color.White)
                                .padding(14.dp)
                        ) {
                            // Note: title/message are stored as-created (English), not re-translated here —
                            // see NotificationRepository.add() call sites if full localization is needed later.
                            Text(notification.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(notification.message, fontSize = 13.sp, color = Color.DarkGray)
                            Spacer(modifier = Modifier.height(6.dp))
                            val currentLocale = androidx.compose.ui.platform.LocalConfiguration.current.locales[0]
                            Text(
                                remember(notification.createdAt, currentLocale) {
                                    SimpleDateFormat("dd MMM yyyy, h:mm a", currentLocale).format(Date(notification.createdAt))
                                },
                                fontSize = 11.sp, color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}