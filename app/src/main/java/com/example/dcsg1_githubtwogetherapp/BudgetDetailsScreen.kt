package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetDetailsScreen(onBackClick: () -> Unit) {
    Scaffold(
        containerColor = Color(0xFFFAF7F2),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.budget_details_title), color = Color.Black, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier.padding(start = 12.dp).clickable { onBackClick() }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFAF7F2))
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            BudgetSummaryCard()

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.expenses_label),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            if (CartSession.items.value.isEmpty()) {
                EmptyExpensesState()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(CartSession.items.value) { cartItem -> ExpenseItemCard(cartItem) }
                    item { Spacer(modifier = Modifier.height(20.dp)) }
                }
            }
        }
    }
}

@Composable
fun BudgetSummaryCard() {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFFDECD8))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(96.dp), contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(96.dp)) {
                    val strokeWidth = 12.dp.toPx()
                    drawArc(
                        color = Color(0xFFF0DFC8), startAngle = -90f, sweepAngle = 360f, useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                        size = Size(size.width - strokeWidth, size.height - strokeWidth),
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
                    )
                    drawArc(
                        color = Color(0xFFB5722C), startAngle = -90f,
                        sweepAngle = 360f * (BudgetSession.percentageUsed.coerceAtMost(100) / 100f),
                        useCenter = false, style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                        size = Size(size.width - strokeWidth, size.height - strokeWidth),
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
                    )
                }
                Text("${BudgetSession.percentageUsed}%", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(stringResource(R.string.total_budget), fontSize = 12.sp, color = Color.Gray)
                Text(
                    text = "RM ${"%,.0f".format(BudgetSession.totalBudget.value)}",
                    fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black
                )
                if (BudgetSession.remainingBudget < 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.over_budget_by, "%,.0f".format(-BudgetSession.remainingBudget)),
                        fontSize = 11.sp, color = Color(0xFFC0392B), fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp))
                .background(Color.White).padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatColumn(stringResource(R.string.used_label), BudgetSession.usedBudget, Color(0xFFB5722C))
            VerticalDivider()
            StatColumn(
                stringResource(R.string.remaining_label), BudgetSession.remainingBudget,
                if (BudgetSession.remainingBudget < 0) Color(0xFFC0392B) else Color(0xFF3F7D4F)
            )
        }
    }
}

@Composable
fun StatColumn(label: String, amount: Double, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("RM ${"%,.0f".format(amount)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = color)
        Text(label, fontSize = 11.sp, color = Color.Gray)
    }
}

@Composable
fun VerticalDivider() {
    Box(modifier = Modifier.width(1.dp).height(32.dp).background(Color(0xFFEFE0D0)))
}

@Composable
fun ExpenseItemCard(cartItem: CartItem) {
    val iconResId = quickActions.find { it.label.equals(cartItem.vendor.category, ignoreCase = true) }?.iconResId

    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp))
            .background(Color.White).padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(44.dp).clip(CircleShape).background(Color(0xFFFDECD8)),
            contentAlignment = Alignment.Center
        ) {
            if (iconResId != null) {
                Image(painter = painterResource(id = iconResId), contentDescription = null, modifier = Modifier.size(22.dp))
            } else {
                Icon(imageVector = Icons.Outlined.MoreHoriz, contentDescription = null, tint = Color(0xFFB5722C), modifier = Modifier.size(22.dp))
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(cartItem.vendor.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(cartItem.vendor.category, fontSize = 12.sp, color = Color.Gray)
        }
        Text(cartItem.selectedPackage.price, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB5722C))
    }
}

@Composable
fun EmptyExpensesState() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(64.dp).clip(CircleShape).background(Color(0xFFFDECD8)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Outlined.MoreHoriz, contentDescription = null, tint = Color(0xFFB5722C), modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(stringResource(R.string.no_expenses_yet), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(stringResource(R.string.add_vendor_to_cart_hint), fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center)
    }
}