// AndroidStudioProjects\Shopping_List_Gavle\app\src\main\java\com\example\shopping_list_gavle\DeletedItem.kt

// Defines the DeletedItem data class which represents items that have been deleted
package com.example.shopping_list_gavle

data class DeletedItem(
    val id: Int,               // Unique ID for the deleted item
    val itemId: Int,           // Original ID of the item before deletion
    val name: String,          // Name of the deleted item
    val category: String,      // Category of the deleted item
    val datetimeDeleted: String  // Date and time when the item was deleted
) {
    // Method to convert a DeletedItem back into a regular Item
    fun toItem(): Item {
        return Item(itemId, name, category, datetimeDeleted)
    }
}