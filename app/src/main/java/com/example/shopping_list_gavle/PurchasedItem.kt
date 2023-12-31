// AndroidStudioProjects\Shopping_List_Gavle\app\src\main\java\com\example\shopping_list_gavle\PurchasedItem.kt

// Defines the PurchasedItem data class which represents items that have been marked as purchased
package com.example.shopping_list_gavle

data class PurchasedItem(
    val id: Int,                // Unique ID for the purchased item
    val itemId: Int,            // Original ID of the item before it was marked as purchased
    val name: String,           // Name of the purchased item
    val category: String,       // Category of the purchased item
    val datetimePurchased: String  // Date and time when the item was marked as purchased
) {
    // Method to convert a PurchasedItem back into a regular Item
    fun toItem(): Item {
        return Item(itemId, name, category, datetimePurchased)
    }
}
