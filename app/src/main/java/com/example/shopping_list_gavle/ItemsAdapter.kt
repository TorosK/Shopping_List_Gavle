// AndroidStudioProjects\Shopping_List_Gavle\app\src\main\java\com\example\shopping_list_gavle\ItemsAdapter.kt

package com.example.shopping_list_gavle

import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private var items: MutableList<Item>) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    var onItemClick: ((Item) -> Unit)? = null

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
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
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
        items.clear() // Rensa befintliga varor
        items.addAll(deletedItems.map { it.toItem() }) // Lägg till nya varor
        notifyDataSetChanged() // Meddela att datan har ändrats
    }

    // Metod för att visa köpta varor
    fun showPurchasedItems(purchasedItems: List<PurchasedItem>) {
        items.clear() // Rensa befintliga varor
        items.addAll(purchasedItems.map { it.toItem() }) // Lägg till nya varor
        notifyDataSetChanged() // Meddela att datan har ändrats
    }

    // Metod för att uppdatera listan med varor
    fun setItems(newItems: List<Item>) {
        items.clear()           // Rensa befintliga varor
        items.addAll(newItems)  // Lägg till nya varor
        notifyDataSetChanged()  // Meddela att datan har ändrats
    }
}