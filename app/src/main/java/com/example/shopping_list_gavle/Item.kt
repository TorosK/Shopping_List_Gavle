// Item.kt

// Defines the Item data class which represents a general item in the shopping list
package com.example.shopping_list_gavle

data class Item(
    val id: Int,                // Unique ID of the item
    val name: String,           // Name of the item
    val category: String,       // Category of the item
    val datetimeAdded: String   // Date and time when the item was added to the list
)
