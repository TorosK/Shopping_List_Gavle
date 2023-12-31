// AndroidStudioProjects\Shopping_List_Gavle\app\src\main\java\com\example\shopping_list_gavle\ItemsAdapter.kt

package com.example.shopping_list_gavle

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
        holder.nameTextView.text = "Namn -> ${item.name}"
        holder.categoryTextView.text = "Kategori -> ${item.category}"
        holder.dateTimeTextView.text = "Datum -> ${item.datetimeAdded}"

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
    }

    // Returnerar storleken på din dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    // Metod för att lägga till varor i listan
    fun addItem(item: Item) {
        items.add(item) // Lägg till item i listan
        notifyItemInserted(items.size - 1)
    }

    // Metod för att visa borttagna varor
    fun showDeletedItems(deletedItems: List<DeletedItem>) {
        listType = ListType.DELETED
        items.clear() // Rensa befintliga varor
        items.addAll(deletedItems.map { it.toItem() }) // Lägg till nya varor
        notifyDataSetChanged() // Meddela att datan har ändrats
    }

    // Metod för att visa köpta varor
    fun showPurchasedItems(purchasedItems: List<PurchasedItem>) {
        listType = ListType.PURCHASED
        items.clear() // Rensa befintliga varor
        items.addAll(purchasedItems.map { it.toItem() }) // Lägg till nya varor
        notifyDataSetChanged() // Meddela att datan har ändrats
    }

    // Metod för att uppdatera listan med varor
    fun setItems(newItems: List<Item>) {
        listType = ListType.DEFAULT
        items.clear()           // Rensa befintliga varor
        items.addAll(newItems)  // Lägg till nya varor
        notifyDataSetChanged()  // Meddela att datan har ändrats
    }

    override fun getItemViewType(position: Int): Int {
        return when (listType) {
            ListType.DEFAULT -> 0
            ListType.DELETED -> 1
            ListType.PURCHASED -> 2
        }
    }
}