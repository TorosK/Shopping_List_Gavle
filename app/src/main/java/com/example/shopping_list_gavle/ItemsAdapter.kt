// AndroidStudioProjects\Shopping_List_Gavle\app\src\main\java\com\example\shopping_list_gavle\ItemsAdapter.kt

package com.example.shopping_list_gavle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

// Adapter class for managing the display of items in a RecyclerView
class ItemsAdapter(private val itemClickListener: ItemClickListener, var enableItemClick: Boolean = true) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private val items = mutableListOf<Item>()  // List to hold Item objects
    var listType = ListType.DEFAULT  // Variable to track the current type of list being displayed

    // Enum to define types of lists: Default, Deleted, and Purchased
    enum class ListType {
        DEFAULT, DELETED, PURCHASED
    }

    // ViewHolder class for each item in the RecyclerView
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tvItemName)  // TextView for item name
        val categoryTextView: TextView = view.findViewById(R.id.tvItemCategory)  // TextView for item category
        val dateTimeTextView: TextView = view.findViewById(R.id.tvItemDateTime)  // TextView for item date and time
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    // Interface for handling item clicks
    interface ItemClickListener {
        fun onItemClick(item: Item)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]  // Get item at current position
        val context = holder.itemView.context

        // Set text for TextViews
        holder.nameTextView.text = context.getString(R.string.item_name_label, item.name)
        holder.categoryTextView.text = context.getString(R.string.item_category_label, item.category)
        holder.dateTimeTextView.text = context.getString(R.string.item_datetime_label, item.datetimeAdded)

        // Set background color based on the type of list
        val backgroundColorId = when (listType) {
            ListType.DEFAULT -> R.color.defaultListBackground
            ListType.DELETED -> R.color.deletedItemsBackground
            ListType.PURCHASED -> R.color.purchasedItemsBackground
        }
        val backgroundColor = ContextCompat.getColor(holder.itemView.context, backgroundColorId)
        holder.itemView.setBackgroundColor(backgroundColor)

        // Set click listener for items, only if it's the default list and item clicks are enabled
        if (listType == ListType.DEFAULT && enableItemClick) {
            holder.itemView.setOnClickListener {
                itemClickListener.onItemClick(item)
            }
        } else {
            holder.itemView.setOnClickListener(null)
        }

        // Optional logging for debugging
        Log.d("ItemsAdapter", "Binding view for position $position with listType $listType")
    }

    // Return the size of the dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    // Method to update the adapter with new items and set the current list type
    fun setItems(newItems: List<Item>, listType: ListType) {
        this.listType = listType
        items.clear()           // Clear current items
        items.addAll(newItems)  // Add new items
        notifyDataSetChanged()  // Notify that data has changed
    }

    // Get the type of view at a particular position (used for handling different list types)
    override fun getItemViewType(position: Int): Int {
        return when (listType) {
            ListType.DEFAULT -> 0
            ListType.DELETED -> 1
            ListType.PURCHASED -> 2
        }
    }
}