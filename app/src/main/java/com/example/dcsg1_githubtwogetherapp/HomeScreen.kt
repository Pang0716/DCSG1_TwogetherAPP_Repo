package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Scaffold
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.window.Dialog
import androidx.compose.material.icons.filled.Lock
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material3.SelectableDates
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.launch
import androidx.compose.ui.text.input.ImeAction


data class QuickAction(val label: String, val iconResId: Int)

val quickActions = listOf(
    QuickAction("Venue", R.drawable.icon_venue),
    QuickAction("Photographer", R.drawable.icon_photographer),
    QuickAction("Makeup", R.drawable.icon_makeup),
    QuickAction("Live Band", R.drawable.icon_liveband),
    QuickAction("Emcee", R.drawable.icon_emcee),
    QuickAction("Deco", R.drawable.icon_deco),
    QuickAction("Attire", R.drawable.icon_attire),
    QuickAction("More", R.drawable.icon_more)
)

data class NavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    NavItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
    NavItem("Vendors", Icons.Filled.Storefront, Icons.Outlined.Storefront),
    NavItem("Design", Icons.Filled.Palette, Icons.Outlined.Palette),
    NavItem("Cart", Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart),
    NavItem("Profile", Icons.Filled.Person, Icons.Outlined.Person)
)

@Composable
private fun localizedQuickActionLabel(label: String): String = when (label) {
    "Venue" -> stringResource(R.string.nav_venue)
    "Photographer" -> stringResource(R.string.nav_photographer)
    "Makeup" -> stringResource(R.string.nav_makeup)
    "Live Band" -> stringResource(R.string.nav_liveband)
    "Emcee" -> stringResource(R.string.nav_emcee)
    "Deco" -> stringResource(R.string.nav_deco)
    "Attire" -> stringResource(R.string.nav_attire)
    "More" -> stringResource(R.string.nav_more)
    else -> label
}

@Composable
private fun localizedNavLabel(label: String): String = when (label) {
    "Home" -> stringResource(R.string.bottom_home)
    "Vendors" -> stringResource(R.string.bottom_vendors)
    "Design" -> stringResource(R.string.bottom_design)
    "Cart" -> stringResource(R.string.bottom_cart)
    "Profile" -> stringResource(R.string.bottom_profile)
    else -> label
}

@Composable
fun HomeTopBar(onChatClick: () -> Unit, hasUnreadChats: Boolean, onNotificationClick: () -> Unit, hasUnreadNotifications: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stringResource(R.string.welcome_greeting),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = stringResource(R.string.welcome_tagline),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 16.dp).clickable { onChatClick() }
            ) {
                Box {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = "Chat",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                    if (hasUnreadChats) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .align(Alignment.TopEnd)
                                .clip(CircleShape)
                                .background(Color(0xFFE24B4A))
                        )
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onNotificationClick() }
            ) {
                Box {
                    Icon(
                        imageVector = Icons.Outlined.NotificationsNone,
                        contentDescription = "Notifications",
                        tint = Color.Black,
                        modifier = Modifier.size(25.dp)
                    )
                    if (hasUnreadNotifications) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .align(Alignment.TopEnd)
                                .clip(CircleShape)
                                .background(Color(0xFFE24B4A))
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LocationSelector(
    selectedState: String,
    onStateChosen: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .clickable { showDialog = true }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Location",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp).padding(end = 6.dp)
            )
            Text(text = selectedState, fontSize = 14.sp, color = Color.Black)
        }
        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Expand", tint = Color.Gray)
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.select_a_state)) },
            text = {
                LazyColumn {
                    items(malaysiaWeddingLocations) { state ->
                        Text(
                            text = state.stateName,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onStateChosen(state.stateName)
                                    showDialog = false
                                }
                                .padding(vertical = 12.dp)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun WeddingDateCard(
    onSetDateClick: () -> Unit,
    onGuestListClick: () -> Unit,
    context: android.content.Context,
    weddingSaveScope: kotlinx.coroutines.CoroutineScope
) {
    val dateMillis = WeddingSession.weddingDateMillis.value
    val hasArrived = dateMillis != null && daysUntil(dateMillis) <= 0

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = if (hasArrived) 230.dp else 200.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (hasArrived)
                    Brush.horizontalGradient(listOf(Color(0xFFB5722C), Color(0xFFE0A868)))
                else
                    Brush.horizontalGradient(listOf(Color(0xFFFDECD8), Color(0xFFFDECD8)))
            )
    ) {
        if (!hasArrived) {
            Image(
                painter = painterResource(id = R.drawable.wedding_flowers),
                contentDescription = "Wedding flowers",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .width(150.dp)
                    .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
            )
        }

        when {
            dateMillis == null -> {
                Column(modifier = Modifier.align(Alignment.TopStart).padding(20.dp)) {
                    Text(stringResource(R.string.set_your_wedding_date), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB5722C))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.wedding_date_subtitle),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onSetDateClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(stringResource(R.string.set_wedding_date_btn), fontSize = 12.sp)
                    }
                }
            }
            hasArrived -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🎉", fontSize = 26.sp)
                    Text(
                        text = stringResource(R.string.congratulations),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = stringResource(R.string.congrats_message),
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .clickable {
                                WeddingSession.weddingDateMillis.value = null
                                WeddingReminderWorker.scheduleReminders(context, null)
                                val userId = UserSession.currentUser.value?.id
                                if (userId != null) {
                                    weddingSaveScope.launch {
                                        WeddingRepository.saveWedding(context, userId, null, WeddingSession.guestList.value)
                                    }
                                }
                            }
                            .padding(horizontal = 28.dp, vertical = 8.dp)
                    ) {
                        Text(stringResource(R.string.ok), color = Color(0xFFB5722C), fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            else -> {
                val days = daysUntil(dateMillis)
                Column(modifier = Modifier.align(Alignment.TopStart).padding(20.dp)) {
                    Text(stringResource(R.string.wedding_countdown), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB5722C))
                    Text(formatWeddingDate(dateMillis), fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(text = "$days", fontSize = 44.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (days == 1L) stringResource(R.string.day_to_go) else stringResource(R.string.days_to_go),
                            fontSize = 13.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(R.string.change_date),
                        fontSize = 12.sp,
                        color = Color(0xFFB5722C),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onSetDateClick() }
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .clickable { onGuestListClick() }
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Group,
                contentDescription = "Guestlist",
                tint = Color(0xFFB5722C),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = stringResource(R.string.guestlist), fontSize = 12.sp, color = Color.Black)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "${WeddingSession.guestList.value.size}", fontSize = 12.sp, color = Color.Black)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun QuickActionsGrid(onCategoryClick: (String) -> Unit) {
    val rows = quickActions.chunked(4)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowItems.forEach { action ->
                    QuickActionItem(
                        action = action,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onCategoryClick(if (action.label == "More") "All" else action.label)
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun QuickActionItem(action: QuickAction, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFEFE0D0), RoundedCornerShape(14.dp))
            .padding(6.dp)
    ) {
        Image(
            painter = painterResource(id = action.iconResId),
            contentDescription = action.label,
            modifier = Modifier.size(34.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = localizedQuickActionLabel(action.label),
            fontSize = 11.sp,
            color = Color.Black,
            maxLines = 1
        )
    }
}

@Composable
fun FeaturedVendorsSection(
    currentArea: String,
    isLoggedIn: Boolean,
    onNavigateToLogin: () -> Unit,
    onVendorClick: (Vendor) -> Unit = {},
    onSeeAllClick: () -> Unit = {}
) {
    val filteredVendors = sampleVendors.filter { it.locationState == currentArea }
    var favoriteNames by remember { mutableStateOf<Set<String>>(emptySet()) }
    val scope = rememberCoroutineScope()
    var showLoginDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    LaunchedEffect(isLoggedIn) {
        val userId = UserSession.currentUser.value?.id
        if (isLoggedIn && userId != null) {
            favoriteNames = FavoriteRepository.loadFavoriteNames(context, userId)
        } else {
            favoriteNames = emptySet()
        }
    }

    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.featured_vendors), fontSize = 16.sp, color = Color.Black)
            Text(
                stringResource(R.string.see_all),
                fontSize = 12.sp,
                color = Color(0xFFB5722C),
                modifier = Modifier.clickable { onSeeAllClick() }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (filteredVendors.isEmpty()) {
            Text(
                text = stringResource(R.string.no_vendors_found, currentArea),
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredVendors) { vendor ->
                    FeaturedVendorCard(
                        vendor = vendor,
                        isFavorited = favoriteNames.contains(vendor.name),
                        onClick = { onVendorClick(vendor) },
                        onFavoriteClick = {
                            if (!isLoggedIn) {
                                showLoginDialog = true
                            } else {
                                val userId = UserSession.currentUser.value?.id
                                if (userId != null) {
                                    scope.launch {
                                        try {
                                            if (favoriteNames.contains(vendor.name)) {
                                                val record = fetchFavorite(userId, vendor.name)
                                                if (record != null) removeFavorite(record.id)
                                                FavoriteRepository.cacheRemove(context, userId, vendor.name)
                                                favoriteNames = favoriteNames - vendor.name
                                            } else {
                                                addFavorite(userId, vendor.name)
                                                FavoriteRepository.cacheAdd(context, userId, vendor.name)
                                                favoriteNames = favoriteNames + vendor.name
                                            }
                                        } catch (e: Exception) { /* leave state unchanged on failure */ }
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    if (showLoginDialog) {
        LoginRequiredDialog(
            onDismiss = { showLoginDialog = false },
            onLoginClick = {
                showLoginDialog = false
                onNavigateToLogin()
            }
        )
    }
}
@Composable
fun HomeScreen(
    isLoggedIn: Boolean,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onNavigateToLogin: () -> Unit,
    onLogout: () -> Unit,
    onEditProfile: () -> Unit,
    onHelpSupport: () -> Unit,
    onLanguage: () -> Unit,
    onVendorClick: (Vendor) -> Unit,
    onProceedToPayment: () -> Unit,
    onViewBudgetDetails: () -> Unit,
    onViewSavedVendors: () -> Unit,
    onBrowseVendors: (String) -> Unit,
    onCreateDesignClick: () -> Unit,
    onOpenChatList: () -> Unit,
    onViewMyBookings: () -> Unit,
    onOpenNotifications: () -> Unit
) {
    var selectedState by remember { mutableStateOf("Penang") }
    var showLoginDialog by remember { mutableStateOf(false) }
    var showSetBudgetDialog by remember { mutableStateOf(false) }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showGuestListDialog by remember { mutableStateOf(false) }
    var pendingCategory by remember { mutableStateOf("All") }
    var hasUnreadChats by remember { mutableStateOf(false) }
    var hasUnreadNotifications by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isLoggedIn) {
        val userId = UserSession.currentUser.value?.id
        if (isLoggedIn && userId != null) {
            BudgetSession.totalBudget.value = BudgetRepository.loadBudget(context, userId)
            val (date, guests) = WeddingRepository.loadWedding(context, userId)
            WeddingSession.weddingDateMillis.value = date
            WeddingReminderWorker.scheduleReminders(context, date)
            WeddingSession.guestList.value = guests
            CartSession.items.value = CartRepository.loadCart(context, userId)
            hasUnreadChats = ChatRepository.hasUnreadMessages(context, userId)
        }
    }

    LaunchedEffect(isLoggedIn) {
        val userId = UserSession.currentUser.value?.id
        if (isLoggedIn && userId != null) {
            // Catch up on anything missed while this account wasn't actively subscribed
            val conversations = ChatRepository.loadConversations(context, userId)
            conversations.filter { it.isUnread }.forEach { convo ->
                NotificationRepository.add(context, userId, "New message from ${convo.vendorName}", convo.lastMessage)
            }
            if (conversations.any { it.isUnread }) {
                hasUnreadNotifications = true
            }

            // Then keep listening live for anything new from here on
            ChatRepository.subscribeToAllIncoming(userId).collect { row ->
                NotificationRepository.add(context, userId, "New message from ${row.senderName}", row.content)
                WeddingReminderWorker.showNotification(context, "${row.senderName}: ${row.content}")
                hasUnreadChats = true
                hasUnreadNotifications = true
            }
        }
    }

    LaunchedEffect(selectedTab) {
        val userId = UserSession.currentUser.value?.id
        if (selectedTab == 0 && isLoggedIn && userId != null) {
            hasUnreadChats = ChatRepository.hasUnreadMessages(context, userId)
        }
    }

    if (showDatePickerDialog) {
        SetWeddingDateDialog(
            onDismiss = { showDatePickerDialog = false },
            onConfirm = { millis ->
                WeddingSession.weddingDateMillis.value = millis
                showDatePickerDialog = false
                WeddingReminderWorker.scheduleReminders(context, millis)
                val userId = UserSession.currentUser.value?.id
                if (userId != null) {
                    coroutineScope.launch {
                        WeddingRepository.saveWedding(context, userId, millis, WeddingSession.guestList.value)
                    }
                }
            }
        )
    }

    if (showGuestListDialog) {
        GuestListDialog(onDismiss = { showGuestListDialog = false })
    }

    if (showSetBudgetDialog) {
        SetBudgetDialog(
            initialAmount = BudgetSession.totalBudget.value,
            onDismiss = { showSetBudgetDialog = false },
            onConfirm = { amount ->
                BudgetSession.totalBudget.value = amount
                showSetBudgetDialog = false
                val userId = UserSession.currentUser.value?.id
                if (userId != null) {
                    coroutineScope.launch {
                        BudgetRepository.saveBudget(context, userId, amount)
                    }
                }
            }
        )
    }

    if (LoginEventState.showWelcomeMessage.value) {
        WelcomeBackDialog(
            userName = UserSession.currentUser.value?.fullName ?: "there",
            onDismiss = { LoginEventState.showWelcomeMessage.value = false }
        )
    }

    if (showLoginDialog) {
        LoginRequiredDialog(
            onDismiss = { showLoginDialog = false },
            onLoginClick = {
                showLoginDialog = false
                onNavigateToLogin()
            }
        )
    }

    Scaffold(
        containerColor = Color(0xFFFDF8F3),
        bottomBar = {
            BottomNavBar(selectedIndex = selectedTab, onItemSelected = onTabSelected)
        }
    ) { innerPadding ->
        if (selectedTab == 1) {
            Box(modifier = Modifier.padding(innerPadding)) {
                BrowseVendorsScreen(
                    vendors = sampleVendors,
                    onVendorClick = onVendorClick,
                    onBackClick = { onTabSelected(0) },
                    initialCategory = pendingCategory
                )
            }
        } else if (selectedTab == 2) {
            Box(modifier = Modifier.padding(innerPadding)) {
                DesignScreen(
                    onBackClick = { onTabSelected(0) },
                    onCreateNowClick = onCreateDesignClick,
                    isLoggedIn = isLoggedIn,
                    onNavigateToLogin = onNavigateToLogin
                )
            }

        } else if (selectedTab == 3) {
            Box(modifier = Modifier.padding(innerPadding)) {
                CartScreen(
                    onBackClick = { onTabSelected(0) },
                    onProceedToPayment = onProceedToPayment,
                    isLoggedIn = isLoggedIn,
                    onNavigateToLogin = onNavigateToLogin
                )
            }
        } else if (selectedTab == 4) {
            Box(modifier = Modifier.padding(innerPadding)) {
                ProfileScreen(
                    isLoggedIn = isLoggedIn,
                    onLogout = onLogout,
                    onNavigateToLogin = onNavigateToLogin,
                    onEditProfile = onEditProfile,
                    onHelpSupport = onHelpSupport,
                    onLanguage = onLanguage,
                    onViewBookings = onViewMyBookings,
                    onViewSavedVendors = onViewSavedVendors
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .background(Color(0xFFFDF8F3))
                    .padding(top = 10.dp)
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                HomeTopBar(
                    onChatClick = {
                        if (isLoggedIn) onOpenChatList() else showLoginDialog = true
                    },
                    hasUnreadChats = hasUnreadChats,
                    onNotificationClick = {
                        if (isLoggedIn) onOpenNotifications() else showLoginDialog = true
                    },
                    hasUnreadNotifications = hasUnreadNotifications
                )
                Spacer(Modifier.height(8.dp))
                LocationSelector(
                    selectedState = selectedState,
                    onStateChosen = { state ->
                        selectedState = state
                    }
                )
                Spacer(Modifier.height(10.dp))
                WeddingDateCard(
                    onSetDateClick = {
                        if (isLoggedIn) showDatePickerDialog = true else showLoginDialog = true
                    },
                    onGuestListClick = {
                        if (isLoggedIn) showGuestListDialog = true else showLoginDialog = true
                    },
                    context = context,
                    weddingSaveScope = coroutineScope
                )
                Spacer(Modifier.height(12.dp))
                QuickActionsGrid(onCategoryClick = { category ->
                    pendingCategory = category
                    onTabSelected(1)
                })
                Spacer(Modifier.height(12.dp))
                FeaturedVendorsSection(
                    currentArea = selectedState,
                    isLoggedIn = isLoggedIn,
                    onNavigateToLogin = onNavigateToLogin,
                    onVendorClick = onVendorClick,
                    onSeeAllClick = {
                        pendingCategory = "All"
                        onTabSelected(1)
                    }
                )
                if (isLoggedIn) {
                    Spacer(Modifier.height(12.dp))
                    BudgetPlannerCard(
                        onSetBudgetClick = { showSetBudgetDialog = true },
                        onViewDetailsClick = { onViewBudgetDetails() }
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun BottomNavBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(width = 1.dp, color = Color(0xFFF0F0F0))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        bottomNavItems.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onItemSelected(index) }
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                    contentDescription = item.label,
                    tint = if (isSelected) Color(0xFFB5722C) else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = localizedNavLabel(item.label),
                    fontSize = 10.sp,
                    color = if (isSelected) Color(0xFFB5722C) else Color.Gray
                )
            }
        }
    }
}

@Composable
fun LoginRequiredDialog(
    onDismiss: () -> Unit,
    onLoginClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFDECD8)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null,
                    tint = Color(0xFFB5722C),
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.login_required_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.login_required_wedding_date),
                fontSize = 13.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.cancel), color = Color.Black)
                }

                Button(
                    onClick = onLoginClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.login_button))
                }
            }
        }
    }
}

@Composable
fun WelcomeBackDialog(userName: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(28.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFDECD8)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFFB5722C),
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.login_successful),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.welcome_user, userName),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB5722C),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.wedding_journey_starts),
                fontSize = 13.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.done))
            }
        }
    }
}

@Composable
fun BudgetPlannerCard(onSetBudgetClick: () -> Unit, onViewDetailsClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFEFE0D0), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(stringResource(R.string.budget_planner), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(
                    text = stringResource(R.string.budget_planner_subtitle),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            if (BudgetSession.totalBudget.value > 0) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Budget",
                    tint = Color(0xFFB5722C),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onSetBudgetClick() }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (BudgetSession.totalBudget.value <= 0) {
            Text(
                text = stringResource(R.string.no_budget_yet),
                fontSize = 13.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = onSetBudgetClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(stringResource(R.string.set_your_budget), fontSize = 13.sp)
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(90.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(90.dp)) {
                        val strokeWidth = 10.dp.toPx()
                        drawArc(
                            color = Color(0xFFF0E4D8),
                            startAngle = -90f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                            size = Size(size.width - strokeWidth, size.height - strokeWidth),
                            topLeft = androidx.compose.ui.geometry.Offset(strokeWidth / 2, strokeWidth / 2)
                        )
                        drawArc(
                            color = Color(0xFFB5722C),
                            startAngle = -90f,
                            sweepAngle = 360f * (BudgetSession.percentageUsed / 100f),
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                            size = Size(size.width - strokeWidth, size.height - strokeWidth),
                            topLeft = androidx.compose.ui.geometry.Offset(strokeWidth / 2, strokeWidth / 2)
                        )
                    }
                    Text(
                        text = "${BudgetSession.percentageUsed}%\n${stringResource(R.string.used_label)}",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = stringResource(R.string.total_budget),
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "RM ${"%,.0f".format(BudgetSession.totalBudget.value)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LegendDot(color = Color(0xFFB5722C), label = "${stringResource(R.string.used_label)}: RM ${"%,.0f".format(BudgetSession.usedBudget)}")
                    LegendDot(color = Color(0xFFF0E4D8), label = "${stringResource(R.string.remaining_label)}: RM ${"%,.0f".format(BudgetSession.remainingBudget)}")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick = onViewDetailsClick,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.view_details), color = Color(0xFFB5722C))
                Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = Color(0xFFB5722C))
            }
        }
    }
}

@Composable
fun LegendDot(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(color))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = label, fontSize = 11.sp, color = Color.Gray)
    }
}

@Composable
fun SetBudgetDialog(
    initialAmount: Double = 0.0,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit
) {
    var budgetInput by remember {
        mutableStateOf(if (initialAmount > 0) initialAmount.toInt().toString() else "")
    }
    var showConfirmWarningDialog by remember { mutableStateOf(false) }

    val amount = budgetInput.toDoubleOrNull()
    val errorMessage = when {
        budgetInput.isBlank() -> null
        amount == null -> stringResource(R.string.budget_error_required)
        amount <= 0 -> stringResource(R.string.budget_error_zero)
        else -> null
    }
    val warningMessage = if (amount != null && amount > 0 && amount < CartSession.totalCart) {
        stringResource(R.string.budget_warning_less_than_cart, "%,.0f".format(CartSession.totalCart))
    } else null

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initialAmount > 0) stringResource(R.string.edit_your_wedding_budget) else stringResource(R.string.set_your_wedding_budget)) },
        text = {
            Column {
                OutlinedTextField(
                    value = budgetInput,
                    onValueChange = { budgetInput = it },
                    placeholder = { Text(stringResource(R.string.budget_placeholder)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    isError = errorMessage != null
                )
                if (errorMessage != null) {
                    Text(errorMessage, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                } else if (warningMessage != null) {
                    Text(warningMessage, color = Color(0xFFB5722C), fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (amount != null && amount > 0) {
                        if (warningMessage != null) showConfirmWarningDialog = true
                        else onConfirm(amount)
                    }
                },
                enabled = amount != null && amount > 0,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C))
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) }
        }
    )

    if (showConfirmWarningDialog && amount != null) {
        AlertDialog(
            onDismissRequest = { showConfirmWarningDialog = false },
            title = { Text(stringResource(R.string.are_you_sure)) },
            text = {
                Text(stringResource(R.string.budget_confirm_warning, "%,.0f".format(amount), "%,.0f".format(CartSession.totalCart)))
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmWarningDialog = false
                        onConfirm(amount)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C))
                ) { Text(stringResource(R.string.yes_confirm)) }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmWarningDialog = false }) { Text(stringResource(R.string.go_back)) }
            }
        )
    }
}

@Composable
fun SetWeddingDateDialog(onDismiss: () -> Unit, onConfirm: (Long) -> Unit) {
    val today = System.currentTimeMillis()

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= today
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { onConfirm(it) }
            }) { Text(stringResource(R.string.confirm), color = Color(0xFFB5722C)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun formatWeddingDate(millis: Long): String {
    val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return sdf.format(Date(millis))
}

fun daysUntil(millis: Long): Long {
    val today = System.currentTimeMillis()
    return ((millis - today) / (1000 * 60 * 60 * 24)).coerceAtLeast(0)
}

@Composable
fun GuestListDialog(onDismiss: () -> Unit) {
    var newGuestName by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    fun addGuest() {
        if (newGuestName.isNotBlank()) {
            WeddingSession.guestList.value = WeddingSession.guestList.value + newGuestName.trim()
            newGuestName = ""
            focusManager.clearFocus()
            val userId = UserSession.currentUser.value?.id
            if (userId != null) {
                coroutineScope.launch {
                    WeddingRepository.saveWedding(context, userId, WeddingSession.weddingDateMillis.value, WeddingSession.guestList.value)
                }
            }
        }
    }

    fun removeGuest(guest: String) {
        WeddingSession.guestList.value = WeddingSession.guestList.value.filter { it != guest }
        val userId = UserSession.currentUser.value?.id
        if (userId != null) {
            coroutineScope.launch {
                WeddingRepository.saveWedding(context, userId, WeddingSession.weddingDateMillis.value, WeddingSession.guestList.value)
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Group, contentDescription = null, tint = Color(0xFFB5722C), modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.guest_list_title, WeddingSession.guestList.value.size))
            }
        },
        text = {
            Column {
                if (WeddingSession.guestList.value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_guests_added),
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 220.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(WeddingSession.guestList.value) { guest ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFFAF7F2))
                                    .padding(horizontal = 10.dp, vertical = 8.dp)
                            ) {
                                Box(
                                    modifier = Modifier.size(30.dp).clip(CircleShape).background(Color(0xFFFDECD8)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = guest.trim().firstOrNull()?.uppercase() ?: "?",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFB5722C)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(guest, fontSize = 14.sp, color = Color.Black, modifier = Modifier.weight(1f))
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = "Remove",
                                    tint = Color.Gray,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clickable { removeGuest(guest) }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = newGuestName,
                        onValueChange = { newGuestName = it },
                        placeholder = { Text(stringResource(R.string.guest_name_placeholder)) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { addGuest() })
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFB5722C))
                            .clickable { addGuest() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C))) {
                Text(stringResource(R.string.done))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        isLoggedIn = false,
        selectedTab = 0,
        onTabSelected = {},
        onNavigateToLogin = {},
        onLogout = {},
        onEditProfile = {},
        onHelpSupport = {},
        onLanguage = {},
        onVendorClick = {},
        onProceedToPayment = {},
        onViewBudgetDetails = { },
        onViewSavedVendors = { },
        onBrowseVendors = {},
        onCreateDesignClick = {},
        onOpenChatList = {},
        onViewMyBookings = { },
        onOpenNotifications = { }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLoggedInPreview() {
    HomeScreen(
        isLoggedIn = true,
        selectedTab = 0,
        onTabSelected = {},
        onNavigateToLogin = {},
        onLogout = {},
        onEditProfile = {},
        onHelpSupport = {},
        onLanguage = {},
        onVendorClick = {},
        onProceedToPayment = {},
        onViewBudgetDetails = { },
        onViewSavedVendors = { },
        onBrowseVendors = {},
        onCreateDesignClick = {},
        onOpenChatList = {},
        onViewMyBookings = { },
        onOpenNotifications = { }
    )
}