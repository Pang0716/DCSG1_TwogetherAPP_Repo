package com.example.dcsg1_githubtwogetherapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class CartItem(
    val vendor: Vendor,
    val selectedPackage: PackageOption,
    val isChecked: MutableState<Boolean> = mutableStateOf(true)
)

object CartSession {
    val items = mutableStateOf<List<CartItem>>(emptyList())

    fun addVendor(vendor: Vendor, pkg: PackageOption) {
        val alreadyInCart = items.value.any { it.vendor.name == vendor.name }
        if (!alreadyInCart) {
            items.value = items.value + CartItem(vendor, pkg)
        } else {
            updatePackage(vendor, pkg)
        }
    }

    fun updatePackage(vendor: Vendor, pkg: PackageOption) {
        items.value = items.value.map {
            if (it.vendor.name == vendor.name) it.copy(selectedPackage = pkg) else it
        }
    }

    fun removeVendor(vendor: Vendor) {
        items.value = items.value.filter { it.vendor.name != vendor.name }
    }

    private fun priceToDouble(price: String): Double {
        return price.replace("RM", "").replace(",", "").trim().toDoubleOrNull() ?: 0.0
    }

    val totalSelected: Double
        get() = items.value.filter { it.isChecked.value }
            .sumOf { priceToDouble(it.selectedPackage.price) }

    val totalCart: Double
        get() = items.value.sumOf { priceToDouble(it.selectedPackage.price) }

    val selectedCount: Int
        get() = items.value.count { it.isChecked.value }
}