package com.example.dcsg1_githubtwogetherapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class CartItem(
    val vendor: Vendor,
    val isChecked: MutableState<Boolean> = mutableStateOf(true)
)

object CartSession {
    val items = mutableStateOf<List<CartItem>>(emptyList())

    fun addVendor(vendor: Vendor) {
        val alreadyInCart = items.value.any { it.vendor.name == vendor.name }
        if (!alreadyInCart) {
            items.value = items.value + CartItem(vendor)
        }
    }

    fun removeVendor(vendor: Vendor) {
        items.value = items.value.filter { it.vendor.name != vendor.name }
    }

    private fun priceToDouble(priceFrom: String): Double {
        return priceFrom.replace("RM", "").replace(",", "").trim().toDoubleOrNull() ?: 0.0
    }

    val totalSelected: Double
        get() = items.value.filter { it.isChecked.value }
            .sumOf { priceToDouble(it.vendor.priceFrom) }

    val selectedCount: Int
        get() = items.value.count { it.isChecked.value }
}