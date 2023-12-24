// AndroidStudioProjects\Shopping_List_Gavle\app\src\main\java\com\example\shopping_list_gavle\DeletedItem.kt
package com.example.shopping_list_gavle

data class DeletedItem(
    val id: Int,
    val itemId: Int,
    val name: String,
    val category: String,
    val datetimeDeleted: String
) {
    fun toItem(): Item {
        return Item(itemId, name, category, datetimeDeleted)
    }
}