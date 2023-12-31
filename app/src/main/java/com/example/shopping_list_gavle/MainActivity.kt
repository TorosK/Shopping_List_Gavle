// AndroidStudioProjects\Shopping_List_Gavle\app\src\main\java\com\example\shopping_list_gavle\MainActivity.kt

package com.example.shopping_list_gavle

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var dbHelper: DatabaseHelper

    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize DatabaseHelper
        dbHelper = DatabaseHelper(this)

        // Initialize the RecyclerView adapter
        itemsAdapter = ItemsAdapter()

        // Setup RecyclerView and Adapter
        val rvItems = findViewById<RecyclerView>(R.id.rvItems)
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = itemsAdapter

        // Load existing items from the database and populate RecyclerView
        val existingItems = dbHelper.getAllItems()
        itemsAdapter.setItems(existingItems)

        // UI-komponenter
        val etItemName = findViewById<EditText>(R.id.etItemName)
        val spCategory = findViewById<Spinner>(R.id.spCategory)
        val btnShowDeletedItems = findViewById<Button>(R.id.btnShowDeletedItems)
        val btnShowPurchasedItems = findViewById<Button>(R.id.btnShowPurchasedItems)

        // Example categories - modify as needed
        val categories = arrayOf("Food", "Clothing", "Electronics")

        // Setup Add Item Button
        val btnAddItem = findViewById<Button>(R.id.btnAddItem)
        // Lägg till en vara
        btnAddItem.setOnClickListener {
            val itemName = etItemName.text.toString().trim()
            if(itemName.isNotEmpty()) {
                val category = spCategory.selectedItem.toString()
                val currentDateTime = getCurrentDateTime()
                val item = Item(0, itemName, category, currentDateTime)

                val itemId = dbHelper.addItem(item)
                if (itemId > -1) {
                    Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()
                    // Update RecyclerView with the latest items
                    updateRecyclerView()
                } else {
                    Toast.makeText(this, "Error adding item", Toast.LENGTH_SHORT).show()
                }
            } else {
                etItemName.error = "Varans namn kan inte vara tomt"
            }
        }

        // In onCreate of MainActivity
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = spinnerAdapter

        // Visa borttagna varor
        btnShowDeletedItems.setOnClickListener {
            val deletedItems = dbHelper.getDeletedItems()
            // Antag att vi har en metod i DeletedItem för att konvertera till Item
            val itemsList = deletedItems.map { it.toItem() }
            itemsAdapter.setItems(itemsList) // Skicka en lista av Item-objekt
        }

        // Visa köpta varor
        btnShowPurchasedItems.setOnClickListener {
            val purchasedItems = dbHelper.getPurchasedItems()
            // Antag att vi har en metod i PurchasedItem för att konvertera till Item
            val itemsList = purchasedItems.map { it.toItem() }
            itemsAdapter.setItems(itemsList) // Skicka en lista av Item-objekt
        }

        etItemName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed here for your use case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No action needed here for your use case
            }

            override fun afterTextChanged(s: Editable?) {
                btnAddItem.isEnabled = s.toString().trim().isNotEmpty()
            }
        })
    }

    // Method to update RecyclerView with the latest items
    private fun updateRecyclerView() {
        val currentItems = dbHelper.getAllItems() // Hämta aktuella varor från databasen
        itemsAdapter.setItems(currentItems) // Antag att setItems är en metod i ItemsAdapter
    }

}
