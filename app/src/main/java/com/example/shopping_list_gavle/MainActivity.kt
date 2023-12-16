// MainActivity.kt

package com.example.shopping_list_gavle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        // UI-komponenter
        val rvItems = findViewById<RecyclerView>(R.id.rvItems)
        val etItemName = findViewById<EditText>(R.id.etItemName)
        val spCategory = findViewById<Spinner>(R.id.spCategory)
        val btnAddItem = findViewById<Button>(R.id.btnAddItem)
        val btnShowDeletedItems = findViewById<Button>(R.id.btnShowDeletedItems)
        val btnShowPurchasedItems = findViewById<Button>(R.id.btnShowPurchasedItems)

        // Setup RecyclerView för varor
        itemsAdapter = ItemsAdapter(mutableListOf()) // Pseudokod - implementera ItemsAdapter
        rvItems.adapter = itemsAdapter
        rvItems.layoutManager = LinearLayoutManager(this)

        // Lägg till en vara
        btnAddItem.setOnClickListener {
            val itemName = etItemName.text.toString().trim()
            if(itemName.isNotEmpty()) {
                val category = spCategory.selectedItem.toString()
                val item = Item(0, itemName, category)
                val itemId = dbHelper.addItem(item)
                if (itemId > -1) {
                    itemsAdapter.addItem(item.copy(id = itemId.toInt()))
                    etItemName.text.clear()
                } else {
                    // Hantera fel här
                }
            } else {
                etItemName.error = "Varans namn kan inte vara tomt"
            }
        }

        // Visa borttagna varor
        btnShowDeletedItems.setOnClickListener {
            // Antag att du har en metod i din adapter för att visa borttagna varor
            val deletedItems = dbHelper.getDeletedItems()
            itemsAdapter.showDeletedItems(deletedItems) // Pseudokod - implementera denna metod
        }

        // Visa köpta varor
        btnShowPurchasedItems.setOnClickListener {
            // Antag att du har en metod i din adapter för att visa köpta varor
            val purchasedItems = dbHelper.getPurchasedItems()
            itemsAdapter.showPurchasedItems(purchasedItems) // Pseudokod - implementera denna metod
        }
    }
}
