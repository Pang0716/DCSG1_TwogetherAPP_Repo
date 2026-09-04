package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    otherPartyId: String,
    otherPartyName: String,
    vendorName: String,
    onBackClick: () -> Unit,
    onVendorClick: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val me = UserSession.currentUser.value

    var messages by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(otherPartyId, vendorName) {
        if (me != null) {
            messages = ChatRepository.loadMessages(context, me.id, otherPartyId, vendorName)
            ChatRepository.markConversationRead(context, me.id, otherPartyId, vendorName)
        }
    }

    LaunchedEffect(otherPartyId, vendorName) {
        if (me != null) {
            ChatRepository.subscribeToMessages(me.id, otherPartyId, vendorName).collect { row ->
                messages = messages + ChatMessage(row.content, row.senderId == me.id, System.currentTimeMillis())
            }
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    Scaffold(
        containerColor = Color(0xFFFDF8F3),
        topBar = {
            TopAppBar(
                title = {
                    val isVendorViewing = me?.role == "vendor"
                    val displayName = if (isVendorViewing) otherPartyName else vendorName
                    val vendor = if (isVendorViewing) null else sampleVendors.find { it.name == vendorName }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(enabled = !isVendorViewing) { onVendorClick() }
                    ) {
                        Box(
                            modifier = Modifier.size(34.dp).clip(androidx.compose.foundation.shape.CircleShape).background(Color(0xFFFDECD8)),
                            contentAlignment = Alignment.Center
                        ) {
                            when {
                                vendor?.imageResId != null -> androidx.compose.foundation.Image(
                                    painter = androidx.compose.ui.res.painterResource(id = vendor.imageResId),
                                    contentDescription = null,
                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize().clip(androidx.compose.foundation.shape.CircleShape)
                                )
                                vendor?.imageUrl != null -> coil.compose.AsyncImage(
                                    model = vendor.imageUrl,
                                    contentDescription = null,
                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize().clip(androidx.compose.foundation.shape.CircleShape)
                                )
                                else -> Text(displayName.take(1).uppercase(), color = Color(0xFFB5722C), fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(displayName, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFDF8F3))
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text(stringResource(R.string.type_a_message)) },
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        val text = inputText.trim()
                        if (text.isNotEmpty() && me != null) {
                            inputText = ""
                            scope.launch {
                                ChatRepository.sendMessage(
                                    context, me.id, me.fullName ?: "User",
                                    otherPartyId, otherPartyName, vendorName, text
                                )
                                messages = messages + ChatMessage(text, true, System.currentTimeMillis())
                            }
                        }
                    }
                ) {
                    Icon(Icons.Filled.Send, contentDescription = "Send", tint = Color(0xFFB5722C))
                }
            }
        }
    ) { innerPadding ->
        if (messages.isEmpty()) {
            Box(modifier = Modifier.padding(innerPadding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.say_hello_to, otherPartyName), fontSize = 13.sp, color = Color.Gray)
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(messages) { _, message -> ChatBubble(message) }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isMine) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 260.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(if (message.isMine) Color(0xFFB5722C) else Color.White)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(message.content, fontSize = 14.sp, color = if (message.isMine) Color.White else Color.Black)
        }
    }
}