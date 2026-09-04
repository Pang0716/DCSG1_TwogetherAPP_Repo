package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CardNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length > 16) text.text.substring(0, 16) else text.text
        val out = StringBuilder()
        for (i in trimmed.indices) {
            out.append(trimmed[i])
            if ((i + 1) % 4 == 0 && i != trimmed.lastIndex) out.append(" ")
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 8) return offset + 1
                if (offset <= 12) return offset + 2
                if (offset <= 16) return offset + 3
                return offset + 3
            }
            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 9) return offset - 1
                if (offset <= 14) return offset - 2
                if (offset <= 19) return offset - 3
                return offset - 3
            }
        }

        return TransformedText(AnnotatedString(out.toString()), offsetMapping)
    }
}

class ExpiryDateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length > 4) text.text.substring(0, 4) else text.text
        val out = StringBuilder()
        for (i in trimmed.indices) {
            out.append(trimmed[i])
            if (i == 1 && i != trimmed.lastIndex) out.append("/")
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 2) return offset
                return offset + 1
            }
            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                return offset - 1
            }
        }

        return TransformedText(AnnotatedString(out.toString()), offsetMapping)
    }
}

data class PaymentMethod(
    val id: String,
    val label: String,
    val iconResId: Int,
    val logoResId: Int
)

// label stays English — this is the value saved into the bookings table for a consistent record.
// Display text is localized separately via localizedPaymentMethodLabel(), same pattern as
// quickActions/bottomNavItems in HomeScreen.kt.
val paymentMethods = listOf(
    PaymentMethod("card", "Credit / Debit Card", R.drawable.ic_payment_card, R.drawable.ic_logo_card),
    PaymentMethod("fpx", "FPX Online Banking", R.drawable.ic_payment_fpx, R.drawable.ic_logo_fpx),
    PaymentMethod("tng", "Touch 'n Go eWallet", R.drawable.ic_payment_touchngo, R.drawable.ic_logo_touchngo),
    PaymentMethod("grabpay", "GrabPay", R.drawable.ic_payment_grabpay, R.drawable.ic_logo_grabpay),
    PaymentMethod("atome", "Atome Buy Now Pay Later", R.drawable.ic_payment_atome, R.drawable.ic_logo_atome)
)

val fpxBanks = listOf("Maybank2u", "CIMB Clicks", "Public Bank", "RHB Now", "Hong Leong Connect", "Bank Islam")

@Composable
private fun localizedPaymentMethodLabel(id: String): String = when (id) {
    "card" -> stringResource(R.string.pm_card)
    "fpx" -> stringResource(R.string.pm_fpx)
    "tng" -> stringResource(R.string.pm_tng)
    "grabpay" -> stringResource(R.string.pm_grabpay)
    "atome" -> stringResource(R.string.pm_atome)
    else -> id
}

@Composable
fun PaymentScreen(
    onBackClick: () -> Unit,
    onPayNowClick: (methodLabel: String) -> Unit
) {
    var selectedMethod by remember { mutableStateOf(paymentMethods.first().id) }

    // Card fields
    var cardNumber by remember { mutableStateOf("") }
    var cardExpiry by remember { mutableStateOf("") }
    var cardCvv by remember { mutableStateOf("") }
    var cardName by remember { mutableStateOf("") }

    // FPX
    var selectedBank by remember { mutableStateOf<String?>(null) }
    var bankMenuExpanded by remember { mutableStateOf(false) }

    val cardValid = cardNumber.length == 16 &&
            Regex("^(0[1-9]|1[0-2])\\d{2}$").matches(cardExpiry) &&
            cardCvv.length == 3 &&
            cardName.isNotBlank()

    val isFormValid = when (selectedMethod) {
        "card" -> cardValid
        "fpx" -> selectedBank != null
        else -> true // eWallets/Atome: no extra input needed before redirect, same as real apps
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF8F3))
            .statusBarsPadding()
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back_description),
                tint = Color.Black,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp).clickable { onBackClick() }
            )
            Text(stringResource(R.string.payment_title), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.align(Alignment.Center))
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Text(stringResource(R.string.select_payment_method), fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))

            paymentMethods.forEach { method ->
                PaymentMethodRow(
                    method = method,
                    isSelected = method.id == selectedMethod,
                    onSelect = { selectedMethod = method.id }
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (method.id == selectedMethod) {
                    when (method.id) {
                        "card" -> CardDetailsForm(
                            cardNumber = cardNumber,
                            onCardNumberChange = { cardNumber = it.filter { c -> c.isDigit() }.take(16) },
                            cardExpiry = cardExpiry,
                            onCardExpiryChange = { cardExpiry = it.filter { c -> c.isDigit() }.take(4) },
                            cardCvv = cardCvv,
                            onCardCvvChange = { cardCvv = it.filter { c -> c.isDigit() }.take(3) },
                            cardName = cardName,
                            onCardNameChange = { cardName = it }
                        )
                        "fpx" -> FpxBankPicker(
                            selectedBank = selectedBank,
                            expanded = bankMenuExpanded,
                            onExpandedChange = { bankMenuExpanded = it },
                            onBankSelected = { selectedBank = it; bankMenuExpanded = false }
                        )
                        else -> Text(
                            stringResource(R.string.redirect_to_pay, localizedPaymentMethodLabel(method.id)),
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(stringResource(R.string.secure_encrypted_payment), fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    val methodLabel = paymentMethods.find { it.id == selectedMethod }?.label ?: "Unknown"
                    val fullLabel = if (selectedMethod == "fpx" && selectedBank != null) "$methodLabel ($selectedBank)" else methodLabel
                    onPayNowClick(fullLabel)
                },
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C), disabledContainerColor = Color(0xFFE0D5C8)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text(stringResource(R.string.pay_now), fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun CardDetailsForm(
    cardNumber: String, onCardNumberChange: (String) -> Unit,
    cardExpiry: String, onCardExpiryChange: (String) -> Unit,
    cardCvv: String, onCardCvvChange: (String) -> Unit,
    cardName: String, onCardNameChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(14.dp)
    ) {
        Text(stringResource(R.string.cardholder_name), fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = cardName, onValueChange = onCardNameChange,
            placeholder = { Text(stringResource(R.string.name_on_card)) }, singleLine = true,
            shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(stringResource(R.string.card_number), fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = cardNumber, onValueChange = onCardNumberChange,
            placeholder = { Text("1234 5678 9012 3456") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(),
            visualTransformation = CardNumberVisualTransformation(),
            isError = cardNumber.isNotEmpty() && cardNumber.length != 16
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.expiry_mmyy), fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = cardExpiry, onValueChange = onCardExpiryChange,
                    placeholder = { Text("MM/YY") }, singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(),
                    visualTransformation = ExpiryDateVisualTransformation(),
                    isError = cardExpiry.length == 4 && !Regex("^(0[1-9]|1[0-2])\\d{2}$").matches(cardExpiry)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.cvv_label), fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = cardCvv, onValueChange = onCardCvvChange,
                    placeholder = { Text("123") }, singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(),
                    isError = cardCvv.isNotEmpty() && cardCvv.length != 3
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FpxBankPicker(
    selectedBank: String?,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onBankSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(14.dp)
    ) {
        Text(stringResource(R.string.select_your_bank), fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(6.dp))
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = onExpandedChange) {
            OutlinedTextField(
                value = selectedBank ?: stringResource(R.string.choose_a_bank),
                onValueChange = {}, readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { onExpandedChange(false) }) {
                fpxBanks.forEach { bank ->
                    DropdownMenuItem(text = { Text(bank) }, onClick = { onBankSelected(bank) })
                }
            }
        }
    }
}

@Composable
fun PaymentMethodRow(method: PaymentMethod, isSelected: Boolean, onSelect: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .selectable(selected = isSelected, onClick = onSelect)
            .padding(14.dp)
    ) {
        RadioButton(selected = isSelected, onClick = onSelect, colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFB5722C)))
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = method.iconResId), contentDescription = method.label,
            contentScale = ContentScale.Fit, modifier = Modifier.size(36.dp).clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(localizedPaymentMethodLabel(method.id), fontSize = 14.sp, color = Color.Black, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(8.dp))
        Image(painter = painterResource(id = method.logoResId), contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier.widthIn(max = 64.dp).height(22.dp))
    }
}