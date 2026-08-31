package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LegalDocumentScreen(title: String, content: String, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(24.dp).clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text(
                text = content,
                fontSize = 13.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun TermsOfServiceScreen(onBackClick: () -> Unit) {
    LegalDocumentScreen(
        title = "Terms of Service",
        content = TERMS_OF_SERVICE_TEXT,
        onBackClick = onBackClick
    )
}

@Composable
fun PrivacyPolicyScreen(onBackClick: () -> Unit) {
    LegalDocumentScreen(
        title = "Privacy Policy",
        content = PRIVACY_POLICY_TEXT,
        onBackClick = onBackClick
    )
}

private const val TERMS_OF_SERVICE_TEXT = """Last updated: 2026

1. ACCEPTANCE OF TERMS

By creating an account or using Twogether ("the App"), you agree to be bound by these Terms of Service. If you do not agree, please do not use the App.

2. WHO CAN USE TWOGETHER

You must be at least 18 years old to create an account and book services through the App. By registering, you confirm that the information you provide is accurate and that you have the legal capacity to enter into agreements with vendors.

3. YOUR ACCOUNT

You are responsible for maintaining the confidentiality of your login credentials, including passwords and any accounts linked via Google or Facebook. You agree to notify us immediately of any unauthorized use of your account.

4. VENDOR BOOKINGS

Twogether acts as a platform connecting users with independent wedding vendors (venues, photographers, makeup artists, and other service providers). We are not the vendor and are not directly responsible for the quality, timeliness, or fulfillment of services booked through the platform. Any disputes regarding vendor services should first be raised directly with the vendor.

5. PAYMENTS

Prices displayed on the App are provided by vendors and may change without notice. Once a booking is confirmed and payment is made, cancellation and refund policies are determined by the individual vendor unless otherwise stated.

6. BUDGET PLANNER

The Budget Planner tool is provided for your convenience to help estimate and track wedding-related expenses. Figures are based on information you provide and bookings made through the App; Twogether does not guarantee the accuracy of third-party costs not booked through the platform.

7. PROHIBITED CONDUCT

You agree not to misuse the App, including but not limited to: providing false information, attempting to access other users' accounts, or using the platform for any unlawful purpose.

8. TERMINATION

We reserve the right to suspend or terminate accounts that violate these Terms or engage in fraudulent activity.

9. CHANGES TO THESE TERMS

We may update these Terms from time to time. Continued use of the App after changes are posted constitutes acceptance of the revised Terms.

10. CONTACT US

If you have questions about these Terms, please contact us through the Help & Support section in the App."""

private const val PRIVACY_POLICY_TEXT = """Last updated: 2026

1. INFORMATION WE COLLECT

When you register for Twogether, we collect the following information:
- Full name
- Email address
- Phone number
- Password (stored securely, encrypted)
- If you sign in with Google or Facebook: your name, email, and profile picture as provided by that service

We also collect information you provide while using the App, such as your wedding date, guest list numbers, budget figures, saved vendors, and booking history.

2. HOW WE USE YOUR INFORMATION

We use your information to:
- Create and manage your account
- Allow you to book and communicate with vendors
- Personalize vendor recommendations based on your selected location
- Track your wedding budget and bookings within the Budget Planner
- Send you booking confirmations and account-related notifications
- Improve the App's features and user experience

3. HOW WE SHARE YOUR INFORMATION

We do not sell your personal information. We may share limited information with:
- Vendors you choose to book or message, so they can fulfil your booking
- Google or Facebook, only if you choose to sign in using those services, in accordance with their own privacy policies
- Service providers who help us operate the App (such as our database and authentication provider, Supabase), solely for the purpose of running the App

4. DATA STORAGE AND SECURITY

Your data is stored securely using industry-standard encryption. Passwords are never stored in plain text. While we take reasonable steps to protect your information, no method of electronic storage is 100% secure.

5. YOUR CHOICES

You may:
- Edit your profile information at any time from the Profile page
- Request deletion of your account and associated data by contacting Help & Support
- Log out of the App at any time, which ends your active session

6. CHILDREN'S PRIVACY

Twogether is not intended for individuals under the age of 18. We do not knowingly collect personal information from children.

7. THIRD-PARTY LINKS

The App may contain links to vendor websites or social media pages. We are not responsible for the privacy practices of these third parties.

8. CHANGES TO THIS POLICY

We may update this Privacy Policy from time to time. We encourage you to review it periodically.

9. CONTACT US

If you have questions about this Privacy Policy or how your data is handled, please contact us through the Help & Support section in the App."""