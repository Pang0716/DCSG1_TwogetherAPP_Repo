package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(onBackClick: () -> Unit, onConversationClick: (ChatConversation) -> Unit) {
    val context = LocalContext.current
    var conversations by remember { mutableStateOf<List<ChatConversation>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val userId = UserSession.currentUser.value?.id
        if (userId != null) {
            conversations = ChatRepository.loadConversations(context, userId)
        }
        isLoading = false
    }

    Scaffold(
        containerColor = Color(0xFFFDF8F3),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.chats_title), fontWeight = FontWeight.Bold, color = Color.Black) },
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
            conversations.isEmpty() -> {
                Column(
                    modifier = Modifier.padding(innerPadding).fillMaxSize().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Filled.ChatBubbleOutline, contentDescription = null, tint = Color(0xFFB5722C), modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(stringResource(R.string.no_conversations_yet), fontSize = 15.sp, color = Color.Gray)
                    Text(stringResource(R.string.chat_with_vendor_hint), fontSize = 12.sp, color = Color.Gray)
                }
            }
            else -> {
                LazyColumn(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                    items(conversations, key = { "${it.otherPartyId}_${it.vendorName}" }) { convo ->
                        ConversationRow(convo, onClick = { onConversationClick(convo) })
                    }
                }
            }
        }
    }
}

@Composable
fun ConversationRow(convo: ChatConversation, onClick: () -> Unit) {
    val isVendorViewing = UserSession.currentUser.value?.role == "vendor"
    val vendor = if (isVendorViewing) null else sampleVendors.find { it.name == convo.vendorName }
    val displayName = if (isVendorViewing) convo.otherPartyName else convo.vendorName

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier.size(50.dp).clip(CircleShape).background(Color(0xFFFDECD8)),
            contentAlignment = Alignment.Center
        ) {
            when {
                vendor?.imageResId != null -> Image(
                    painter = painterResource(id = vendor.imageResId),
                    contentDescription = vendor.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(CircleShape)
                )
                vendor?.imageUrl != null -> AsyncImage(
                    model = vendor.imageUrl,
                    contentDescription = vendor.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(CircleShape)
                )
                isVendorViewing -> Text(
                    displayName.take(1).uppercase(),
                    color = Color(0xFFB5722C),
                    fontWeight = FontWeight.Bold
                )
                else -> Icon(Icons.Filled.Image, contentDescription = null, tint = Color(0xFFB5722C))
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(displayName, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black, maxLines = 1)
                if (convo.isUnread) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE24B4A))
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(convo.lastMessage, fontSize = 12.sp, color = Color.Gray, maxLines = 1)
        }

        val currentLocale = androidx.compose.ui.platform.LocalConfiguration.current.locales[0]
        Text(
            remember(convo.lastMessageTime, currentLocale) {
                SimpleDateFormat("h:mm a", currentLocale).format(Date(convo.lastMessageTime))
            },
            fontSize = 11.sp,
            color = Color.Gray
        )
    }
    HorizontalDivider(color = Color(0xFFF0E4D8))
}