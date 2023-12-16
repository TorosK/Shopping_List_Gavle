// PurchasedItem.kt
package com.example.shopping_list_gavle

data class PurchasedItem(
    val id: Int,
    val itemId: Int,
    val name: String,
    val category: String,
    val datetimePurchased: String
)