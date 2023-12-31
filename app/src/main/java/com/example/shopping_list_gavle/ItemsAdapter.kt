// AndroidStudioProjects\Shopping_List_Gavle\app\src\main\java\com\example\shopping_list_gavle\ItemsAdapter.kt

package com.example.shopping_list_gavle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private val itemClickListener: ItemClickListener, var enableItemClick: Boolean = true) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private val items = mutableListOf<Item>()
    var listType = ListType.DEFAULT // Keep track of the list type

    enum class ListType {
        DEFAULT, DELETED, PURCHASED
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tvItemName)
        val categoryTextView: TextView = view.findViewById(R.id.tvItemCategory)
        val dateTimeTextView: TextView = view.findViewById(R.id.tvItemDateTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    interface ItemClickListener {
        fun onItemClick(item: Item)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context

        holder.nameTextView.text = context.getString(R.string.item_name_label, item.name)
        holder.categoryTextView.text = context.getString(R.string.item_category_label, item.category)
        holder.dateTimeTextView.text = context.getString(R.string.item_datetime_label, item.datetimeAdded)

        val backgroundColorId = when (listType) {
            ListType.DEFAULT -> R.color.defaultListBackground
            ListType.DELETED -> R.color.deletedItemsBackground
            ListType.PURCHASED -> R.color.purchasedItemsBackground
        }
        val backgroundColor = ContextCompat.getColor(holder.itemView.context, backgroundColorId)
        holder.itemView.setBackgroundColor(backgroundColor)

        if (enableItemClick) {
            holder.itemView.setOnClickListener {
                itemClickListener.onItemClick(item)
            }
        } else {
            holder.itemView.setOnClickListener(null)
        }

        // Loggning för felsökning (valfritt)
        Log.d("ItemsAdapter", "Binder vy för position $position med listType $listType")
    }

    // Returnerar storleken på din dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    // Method to update the list with items and set the list type
    fun setItems(newItems: List<Item>, listType: ListType) {
        this.listType = listType
        items.clear()           // Clear existing items
        items.addAll(newItems)  // Add new items
        notifyDataSetChanged()  // Notify that data has changed
    }

    override fun getItemViewType(position: Int): Int {
        return when (listType) {
            ListType.DEFAULT -> 0
            ListType.DELETED -> 1
            ListType.PURCHASED -> 2
        }
    }
}