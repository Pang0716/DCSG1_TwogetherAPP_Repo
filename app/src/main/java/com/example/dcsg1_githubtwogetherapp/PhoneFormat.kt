package com.example.dcsg1_githubtwogetherapp

/**
 * Formats digits as a Malaysian phone number while typing.
 * 01X-XXX XXXX for 10-digit numbers (e.g. 012-345 6789)
 * 011-XXXX XXXX for 11-digit numbers (e.g. 011-1234 5678)
 * Anything typed beyond 11 digits is ignored.
 */
fun formatMalaysianPhone(input: String): String {
    val digits = input.filter { it.isDigit() }.take(11)

    return when {
        digits.length <= 3 -> digits
        digits.startsWith("011") -> {
            when {
                digits.length <= 7 -> "${digits.take(3)}-${digits.drop(3)}"
                else -> "${digits.take(3)}-${digits.substring(3, 7)} ${digits.drop(7)}"
            }
        }
        else -> {
            when {
                digits.length <= 6 -> "${digits.take(3)}-${digits.drop(3)}"
                else -> "${digits.take(3)}-${digits.substring(3, 6)} ${digits.drop(6).take(4)}"
            }
        }
    }
}