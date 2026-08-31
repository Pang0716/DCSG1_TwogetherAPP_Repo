package com.example.dcsg1_githubtwogetherapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FaqItem(val question: String, val answer: String)

val faqList = listOf(
    FaqItem("How do I book a vendor?", "Browse vendors from the Home or Vendors tab, tap on one you like, and follow the booking steps on their details page."),
    FaqItem("Can I cancel a booking?", "Cancellation policies depend on each vendor. Check the booking details or contact the vendor directly."),
    FaqItem("How does the Budget Planner work?", "Set your total budget, and any vendor you book will automatically deduct its cost from your remaining balance."),
    FaqItem("Is my payment information secure?", "Yes, all account data is encrypted and securely stored."),
    FaqItem("How do I change my password?", "Go to Login, tap 'Forgot Password?', and follow the steps to reset it via email.")
)

@Composable
fun HelpSupportScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(24.dp).clickable { onBackClick() })
        Spacer(modifier = Modifier.height(16.dp))
        Text("Help & Support", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Spacer(modifier = Modifier.height(20.dp))
        Text("Frequently Asked Questions", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))

        faqList.forEach { faq -> FaqExpandable(faq) }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:support@twogether.app")
                    putExtra(Intent.EXTRA_SUBJECT, "Twogether Support Request")
                }
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5722C)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Filled.Email, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Contact Support")
        }
    }
}

@Composable
fun FaqExpandable(faq: FaqItem) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(faq.question, fontSize = 13.sp, color = Color.Black, modifier = Modifier.weight(1f))
            Icon(Icons.Filled.ExpandMore, contentDescription = null, tint = Color.Gray)
        }
        if (expanded) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(faq.answer, fontSize = 12.sp, color = Color.Gray)
        }
    }
    HorizontalDivider(color = Color(0xFFF0F0F0))
}