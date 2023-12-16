// ItemsAdapter.kt

package com.example.shopping_list_gavle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private var items: List<Item>) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    // ViewHolder för varje vara i listan
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tvItemName)
        val categoryTextView: TextView = view.findViewById(R.id.tvItemCategory)
        // Lägg till fler Views här om nödvändigt
    }

    // Skapar nya Views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    // Byter innehållet i en vy
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        holder.categoryTextView.text = item.category
        // Hantera klick för varje vara här om nödvändigt
    }

    // Returnerar storleken på din dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    // Metod för att lägga till varor i listan
    fun addItem(item: Item) {
        items = items + item
        notifyItemInserted(items.size - 1)
    }

    // Metod för att visa borttagna varor (du måste implementera logiken)
    fun showDeletedItems(deletedItems: List<DeletedItem>) {
        // Pseudokod - ersätt items med deletedItems och uppdatera adaptern
    }

    // Metod för att visa köpta varor (du måste implementera logiken)
    fun showPurchasedItems(purchasedItems: List<PurchasedItem>) {
        // Pseudokod - ersätt items med purchasedItems och uppdatera adaptern
    }
}