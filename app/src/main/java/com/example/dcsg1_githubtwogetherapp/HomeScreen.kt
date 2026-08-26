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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.filled.Star
import androidx.compose.foundation.layout.PaddingValues
import coil.compose.AsyncImage
import androidx.compose.material.icons.filled.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.foundation.clickable

data class QuickAction(val label: String, val icon: ImageVector)

val quickActions = listOf(
    QuickAction("Venue", Icons.Outlined.LocationCity),
    QuickAction("Photographer", Icons.Outlined.CameraAlt),
    QuickAction("Makeup", Icons.Outlined.Face),
    QuickAction("Live Band", Icons.Outlined.MusicNote),
    QuickAction("Emcee", Icons.Outlined.Mic),
    QuickAction("Deco", Icons.Outlined.LocalFlorist),
    QuickAction("Attire", Icons.Outlined.Checkroom),
    QuickAction("More", Icons.Outlined.MoreHoriz)
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
fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Welcome! 👋",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Let's plan your perfect wedding",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChatBubbleOutline,
                    contentDescription = "Chat",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Chat", fontSize = 10.sp, color = Color.Gray)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Outlined.NotificationsNone,
                    contentDescription = "Notifications",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Notifications", fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun LocationSelector(
    selectedArea: String,
    selectedState: String,
    onLocationChosen: (String, String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var tempSelectedState by remember { mutableStateOf<WeddingState?>(null) }

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
            Text(text = "$selectedArea, $selectedState", fontSize = 14.sp, color = Color.Black)
        }
        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Expand", tint = Color.Gray)
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false; tempSelectedState = null },
            title = { Text(tempSelectedState?.stateName ?: "Select a State") },
            text = {
                LazyColumn {
                    if (tempSelectedState == null) {
                        items(malaysiaWeddingLocations) { state ->
                            Text(
                                text = state.stateName,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { tempSelectedState = state }
                                    .padding(vertical = 12.dp)
                            )
                        }
                    } else {
                        items(tempSelectedState!!.areas) { area ->
                            Text(
                                text = area,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onLocationChosen(area, tempSelectedState!!.stateName)
                                        showDialog = false
                                        tempSelectedState = null
                                    }
                                    .padding(vertical = 12.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false; tempSelectedState = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun WeddingDateCard(onSetDateClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)   // was 200.dp — taller card
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFDECD8))
    ) {
        Image(
            painter = painterResource(id = R.drawable.wedding_flowers),
            contentDescription = "Wedding flowers",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(180.dp)   // was 150.dp — wider image
                .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
        )

        // Text + button — top left, stacked
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Text(
                text = "Set your wedding date",
                fontSize = 18.sp,
                color = Color(0xFFB5722C)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Let's set your wedding date\nto see your countdown!",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onSetDateClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C))
            ) {
                Text("Set Wedding Date", fontSize = 12.sp)
            }
        }

        // Guestlist pill — floats near bottom right
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Group,
                contentDescription = "Guestlist",
                tint = Color(0xFFB5722C),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Guestlist", fontSize = 12.sp, color = Color.Black)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "0", fontSize = 12.sp, color = Color.Black)
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
fun QuickActionsGrid() {
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
                        modifier = Modifier.weight(1f)
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
        Icon(
            imageVector = action.icon,
            contentDescription = action.label,
            tint = Color(0xFFB5722C),
            modifier = Modifier.size(34.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = action.label,
            fontSize = 11.sp,
            color = Color.Black,
            maxLines = 1
        )
    }
}

@Composable
fun FeaturedVendorsSection(currentArea: String) {
    val filteredVendors = sampleVendors.filter { it.locationArea == currentArea }

    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Featured Vendors", fontSize = 16.sp, color = Color.Black)
            Text("See All >", fontSize = 12.sp, color = Color(0xFFB5722C))
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (filteredVendors.isEmpty()) {
            Text(
                text = "No vendors found near $currentArea yet",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredVendors) { vendor -> VendorCard(vendor) }
            }
        }
    }
}

@Composable
fun VendorCard(vendor: Vendor) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFEFE0D0), RoundedCornerShape(12.dp))
    ) {
        if (vendor.imageUrl != null) {
            AsyncImage(
                model = vendor.imageUrl,
                contentDescription = vendor.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Color(0xFFFDECD8)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Image,
                    contentDescription = null,
                    tint = Color(0xFFB5722C)
                )
            }
        }

        Column(modifier = Modifier.padding(8.dp)) {
            Text(vendor.name, fontSize = 12.sp, color = Color.Black, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFF5A623), modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(2.dp))
                Text("${vendor.rating} (${vendor.reviewCount})", fontSize = 10.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text("From ${vendor.priceFrom}", fontSize = 11.sp, color = Color(0xFFB5722C))
        }
    }
}

@Composable
fun HomeScreen(
    isLoggedIn: Boolean,
    onNavigateToLogin: () -> Unit,
    onLogout: () -> Unit
) {
    var selectedArea by remember { mutableStateOf("George Town") }
    var selectedState by remember { mutableStateOf("Penang") }
    var selectedTab by remember { mutableStateOf(0) }
    var showLoginDialog by remember { mutableStateOf(false) }
    var showWelcomeBack by remember { mutableStateOf(false) }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn && UserSession.currentUser.value != null) {
            showWelcomeBack = true
        }
    }

    if (showWelcomeBack) {
        WelcomeBackDialog(
            userName = UserSession.currentUser.value?.fullName ?: "there",
            onDismiss = { showWelcomeBack = false }
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
        bottomBar = {
            BottomNavBar(selectedIndex = selectedTab, onItemSelected = { selectedTab = it })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            HomeTopBar()
            if (isLoggedIn) {
                Text(
                    text = "Logout (temporary for testing)",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { onLogout() }
                )
            }
            LocationSelector(
                selectedArea = selectedArea,
                selectedState = selectedState,
                onLocationChosen = { area, state ->
                    selectedArea = area
                    selectedState = state
                }
            )
            WeddingDateCard(
                onSetDateClick = {
                    if (isLoggedIn) {
                        // TODO: open real date picker later
                    } else {
                        showLoginDialog = true
                    }
                }
            )
            QuickActionsGrid()
            FeaturedVendorsSection(currentArea = selectedArea)
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
                    text = item.label,
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
                text = "Login Required",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Please login or register to set\nyour wedding date.",
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
                    Text("Cancel", color = Color.Black)
                }

                Button(
                    onClick = onLoginClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Login")
                }
            }
        }
    }
}

@Composable
fun WelcomeBackDialog(userName: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Welcome back, $userName! 🎉") },
        text = { Text("Great to see you again — let's continue planning your dream wedding.") },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C))
            ) {
                Text("Continue")
            }
        }
    )
}





@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        isLoggedIn = false,
        onNavigateToLogin = {},
        onLogout = {}
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLoggedInPreview() {
    HomeScreen(
        isLoggedIn = true,
        onNavigateToLogin = {},
        onLogout = {}
    )
}

