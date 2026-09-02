package com.example.dcsg1_githubtwogetherapp

import android.content.Context
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartRow(
    @SerialName("user_id") val userId: String,
    @SerialName("vendor_name") val vendorName: String,
    @SerialName("package_name") val packageName: String,
    @SerialName("is_checked") val isChecked: Boolean
)

object CartRepository {

    suspend fun saveCartItem(context: Context, userId: String, vendorName: String, packageName: String, isChecked: Boolean) {
        try {
            supabase.postgrest["cart_items"].upsert(CartRow(userId, vendorName, packageName, isChecked))
        } catch (e: Exception) { /* offline — Room copy below still saves */ }

        AppDatabase.getInstance(context).cartDao().upsertCartItem(CartEntity(userId, vendorName, packageName, isChecked))
    }

    suspend fun removeCartItem(context: Context, userId: String, vendorName: String) {
        try {
            supabase.postgrest["cart_items"].delete { filter { eq("user_id", userId); eq("vendor_name", vendorName) } }
        } catch (e: Exception) { /* offline — Room copy below still saves */ }

        AppDatabase.getInstance(context).cartDao().deleteCartItem(userId, vendorName)
    }

    suspend fun loadCart(context: Context, userId: String): List<CartItem> {
        val rows: List<CartRow> = try {
            val remote = supabase.postgrest["cart_items"]
                .select { filter { eq("user_id", userId) } }
                .decodeList<CartRow>()
            remote.forEach {
                AppDatabase.getInstance(context).cartDao().upsertCartItem(CartEntity(it.userId, it.vendorName, it.packageName, it.isChecked))
            }
            remote
        } catch (e: Exception) {
            AppDatabase.getInstance(context).cartDao().getCartItems(userId)
                .map { CartRow(it.userId, it.vendorName, it.packageName, it.isChecked) }
        }

        return rows.mapNotNull { row ->
            sampleVendors.find { it.name == row.vendorName }?.let { vendor ->
                val pkg = generatePackages(vendor).find { it.name == row.packageName }
                    ?: generatePackages(vendor).first()
                CartItem(vendor, pkg, androidx.compose.runtime.mutableStateOf(row.isChecked))
            }
        }
    }
}