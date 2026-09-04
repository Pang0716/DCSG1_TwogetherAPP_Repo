package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBackClick: () -> Unit,
    onProceedToPayment: () -> Unit,
    isLoggedIn: Boolean,
    onNavigateToLogin: () -> Unit
) {
    val cartItems = CartSession.items.value
    var showLoginDialog by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<CartItem?>(null) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF8F3))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .clickable { onBackClick() }
            )
            Text(
                stringResource(R.string.booking_summary),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (cartItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = null,
                    tint = Color(0xFFB5722C),
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(stringResource(R.string.cart_empty), fontSize = 15.sp, color = Color.Gray)
            }
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                cartItems.forEach { item ->
                    CartItemRow(
                        item = item,
                        onDelete = {
                            CartSession.removeVendor(item.vendor)
                            val userId = UserSession.currentUser.value?.id
                            if (userId != null) {
                                scope.launch { CartRepository.removeCartItem(context, userId, item.vendor.name) }
                            }
                        },
                        onCheckedChange = { checked ->
                            val userId = UserSession.currentUser.value?.id
                            if (userId != null) {
                                scope.launch { CartRepository.saveCartItem(context, userId, item.vendor.name, item.selectedPackage.name, checked) }
                            }
                        },
                        onEdit = { editingItem = item }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(R.string.total_items, CartSession.selectedCount),
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "RM ${"%,.0f".format(CartSession.totalSelected)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        if (isLoggedIn) onProceedToPayment() else showLoginDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(stringResource(R.string.proceed_to_payment), fontSize = 15.sp)
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

    editingItem?.let { item ->
        androidx.compose.material3.ModalBottomSheet(
            onDismissRequest = { editingItem = null },
            sheetState = sheetState
        ) {
            SelectPackageSheetContent(
                vendor = item.vendor,
                packages = generatePackages(item.vendor),
                onContinueClick = { newPackage ->
                    CartSession.updatePackage(item.vendor, newPackage)
                    val userId = UserSession.currentUser.value?.id
                    if (userId != null) {
                        scope.launch {
                            CartRepository.saveCartItem(context, userId, item.vendor.name, newPackage.name, item.isChecked.value)
                        }
                    }
                    editingItem = null
                }
            )
        }
    }
}

@Composable
fun CartItemRow(item: CartItem, onDelete: () -> Unit, onCheckedChange: (Boolean) -> Unit, onEdit: () -> Unit) {
    var checked by item.isChecked

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .padding(10.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it; onCheckedChange(it) },
            colors = CheckboxDefaults.colors(checkedColor = Color(0xFFB5722C))
        )

        if (item.vendor.imageResId != null) {
            Image(
                painter = painterResource(id = item.vendor.imageResId),
                contentDescription = item.vendor.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        } else {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFFDECD8)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Image, contentDescription = null, tint = Color(0xFFB5722C))
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(item.vendor.name, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color.Black, maxLines = 1)
            Text(item.vendor.category, fontSize = 12.sp, color = Color(0xFFB5722C))
            Text(item.selectedPackage.capacity, fontSize = 11.sp, color = Color.Gray, maxLines = 1)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(item.selectedPackage.price, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(6.dp))
            Row {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    tint = Color(0xFFB5722C),
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onEdit() }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onDelete() }
                )
            }
        }
    }
}