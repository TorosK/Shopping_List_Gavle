// DeletedItem.kt
package com.example.shopping_list_gavle

data class DeletedItem(
    val id: Int,
    val itemId: Int,
    val name: String,
    val category: String,
    val datetimeDeleted: String
)