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
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberDatePickerState
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material3.SelectableDates


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
            title = { Text("Select a State") },
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
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun WeddingDateCard(onSetDateClick: () -> Unit, onGuestListClick: () -> Unit) {
    val dateMillis = WeddingSession.weddingDateMillis.value

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
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
                .width(180.dp)
                .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            if (dateMillis == null) {
                Text("Set your wedding date", fontSize = 18.sp, color = Color(0xFFB5722C))
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Let's set your wedding date\nto see your countdown!",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            } else {
                Text("Your Wedding Day", fontSize = 18.sp, color = Color(0xFFB5722C))
                Spacer(modifier = Modifier.height(4.dp))
                Text(formatWeddingDate(dateMillis), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text("${daysUntil(dateMillis)} days to go!", fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onSetDateClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C))
            ) {
                Text(if (dateMillis == null) "Set Wedding Date" else "Change Date", fontSize = 12.sp)
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
            Text(text = "Guestlist", fontSize = 12.sp, color = Color.Black)
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
fun FeaturedVendorsSection(
    currentArea: String,
    onVendorClick: (Vendor) -> Unit = {}
) {
    val filteredVendors = sampleVendors.filter { it.locationState == currentArea }

    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
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
                items(filteredVendors) { vendor ->
                    VendorCard(
                        vendor = vendor,
                        onClick = { onVendorClick(vendor) }
                    )
                }
            }
        }
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
    onProceedToPayment: () -> Unit          // ← new
) {
    var selectedState by remember { mutableStateOf("Penang") }
    var showLoginDialog by remember { mutableStateOf(false) }
    var showSetBudgetDialog by remember { mutableStateOf(false) }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showGuestListDialog by remember { mutableStateOf(false) }

    if (showDatePickerDialog) {
        SetWeddingDateDialog(
            onDismiss = { showDatePickerDialog = false },
            onConfirm = { millis ->
                WeddingSession.weddingDateMillis.value = millis
                showDatePickerDialog = false
            }
        )
    }

    if (showGuestListDialog) {
        GuestListDialog(onDismiss = { showGuestListDialog = false })
    }

    if (showSetBudgetDialog) {
        SetBudgetDialog(
            onDismiss = { showSetBudgetDialog = false },
            onConfirm = { amount ->
                BudgetSession.totalBudget.value = amount
                showSetBudgetDialog = false
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
        bottomBar = {
            BottomNavBar(selectedIndex = selectedTab, onItemSelected = onTabSelected)
        }
    ) { innerPadding ->
        if (selectedTab == 1) {
            Box(modifier = Modifier.padding(innerPadding)) {
                BrowseVendorsScreen(
                    vendors = sampleVendors,
                    onVendorClick = onVendorClick,
                    onBackClick = { onTabSelected(0) }
                )
            }
        } else if (selectedTab == 2) {
            Box(modifier = Modifier.padding(innerPadding)) {
                DesignScreen(
                    onBackClick = { onTabSelected(0) },
                    onCreateNowClick = { /* TODO: next step — invitation templates/editor */ }
                )
            }
        } else if (selectedTab == 3) {
            Box(modifier = Modifier.padding(innerPadding)) {
                CartScreen(
                    onBackClick = { onTabSelected(0) },
                    onProceedToPayment = onProceedToPayment   // ← was the TODO lambda
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
                    onLanguage = onLanguage
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                HomeTopBar()
                LocationSelector(
                    selectedState = selectedState,
                    onStateChosen = { state ->
                        selectedState = state
                    }
                )
                WeddingDateCard(
                    onSetDateClick = {
                        if (isLoggedIn) showDatePickerDialog = true else showLoginDialog = true
                    },
                    onGuestListClick = {
                        if (isLoggedIn) showGuestListDialog = true else showLoginDialog = true
                    }
                )
                QuickActionsGrid()
                FeaturedVendorsSection(currentArea = selectedState)
                if (isLoggedIn) {
                    BudgetPlannerCard(
                        onSetBudgetClick = { showSetBudgetDialog = true },
                        onViewDetailsClick = { }
                    )
                }
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
                text = "Login Successful",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Welcome, $userName!",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB5722C),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Your wedding journey starts here.",
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
                Text("Done")
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
        Text("Budget Planner", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(
            text = "Track your budget and plan smartly",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (BudgetSession.totalBudget.value <= 0) {
            Text(
                text = "You haven't set a budget yet.",
                fontSize = 13.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = onSetBudgetClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Set Your Budget", fontSize = 13.sp)
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
                        text = "${BudgetSession.percentageUsed}%\nUsed",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Total Budget",
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
                    LegendDot(color = Color(0xFFB5722C), label = "Used: RM ${"%,.0f".format(BudgetSession.usedBudget.value)}")
                    LegendDot(color = Color(0xFFF0E4D8), label = "Remaining: RM ${"%,.0f".format(BudgetSession.remainingBudget)}")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick = onViewDetailsClick,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Details", color = Color(0xFFB5722C))
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
fun SetBudgetDialog(onDismiss: () -> Unit, onConfirm: (Double) -> Unit) {
    var budgetInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set Your Wedding Budget") },
        text = {
            OutlinedTextField(
                value = budgetInput,
                onValueChange = { budgetInput = it },
                placeholder = { Text("e.g. 70000") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    val amount = budgetInput.toDoubleOrNull()
                    if (amount != null && amount > 0) {
                        onConfirm(amount)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C))
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
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
            }) { Text("Confirm", color = Color(0xFFB5722C)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
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

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Guest List (${WeddingSession.guestList.value.size})") },
        text = {
            Column {
                LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                    items(WeddingSession.guestList.value) { guest ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Text(guest, fontSize = 14.sp, modifier = Modifier.weight(1f))
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Remove",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable {
                                        WeddingSession.guestList.value =
                                            WeddingSession.guestList.value.filter { it != guest }
                                    }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = newGuestName,
                        onValueChange = { newGuestName = it },
                        placeholder = { Text("Guest name") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        if (newGuestName.isNotBlank()) {
                            WeddingSession.guestList.value = WeddingSession.guestList.value + newGuestName.trim()
                            newGuestName = ""
                        }
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color(0xFFB5722C))
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C))) {
                Text("Done")
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
        onProceedToPayment = {}          // ← new
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
        onProceedToPayment = {}          // ← new
    )
}







