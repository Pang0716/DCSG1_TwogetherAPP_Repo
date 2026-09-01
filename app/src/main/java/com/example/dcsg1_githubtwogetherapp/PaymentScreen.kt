package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// iconResId  -> the small badge icon on the LEFT (ic_payment_xxx)
// logoResId  -> the brand wordmark/logo on the RIGHT (ic_logo_xxx)
data class PaymentMethod(
    val id: String,
    val label: String,
    val iconResId: Int,
    val logoResId: Int
)

val paymentMethods = listOf(
    PaymentMethod("card", "Credit / Debit Card", R.drawable.ic_payment_card, R.drawable.ic_logo_card),
    PaymentMethod("fpx", "FPX Online Banking", R.drawable.ic_payment_fpx, R.drawable.ic_logo_fpx),
    PaymentMethod("tng", "Touch 'n Go eWallet", R.drawable.ic_payment_touchngo, R.drawable.ic_logo_touchngo),
    PaymentMethod("grabpay", "GrabPay", R.drawable.ic_payment_grabpay, R.drawable.ic_logo_grabpay),
    PaymentMethod("atome", "Atome Buy Now Pay Later", R.drawable.ic_payment_atome, R.drawable.ic_logo_atome)
)

@Composable
fun PaymentScreen(
    onBackClick: () -> Unit,
    onPayNowClick: () -> Unit
) {
    var selectedMethod by remember { mutableStateOf(paymentMethods.first().id) }

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
                "Payment",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                "Select Payment Method",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))

            paymentMethods.forEach { method ->
                PaymentMethodRow(
                    method = method,
                    isSelected = method.id == selectedMethod,
                    onSelect = { selectedMethod = method.id }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Secure and encrypted payment", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Button(
                onClick = onPayNowClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Pay Now", fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun PaymentMethodRow(
    method: PaymentMethod,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .selectable(selected = isSelected, onClick = onSelect)
            .padding(14.dp)
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFB5722C))
        )
        Spacer(modifier = Modifier.width(8.dp))

        // Leading badge icon (ic_payment_xxx)
        Image(
            painter = painterResource(id = method.iconResId),
            contentDescription = method.label,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            method.label,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Trailing brand logo (ic_logo_xxx)
        Image(
            painter = painterResource(id = method.logoResId),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .widthIn(max = 64.dp)
                .height(22.dp)
        )
    }
}