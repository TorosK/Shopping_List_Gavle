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

        dbHelper = DatabaseHelper(this)

        // UI-komponenter
        val rvItems = findViewById<RecyclerView>(R.id.rvItems)
        val etItemName = findViewById<EditText>(R.id.etItemName)
        val spCategory = findViewById<Spinner>(R.id.spCategory)
        val btnAddItem = findViewById<Button>(R.id.btnAddItem)
        val btnShowDeletedItems = findViewById<Button>(R.id.btnShowDeletedItems)
        val btnShowPurchasedItems = findViewById<Button>(R.id.btnShowPurchasedItems)

        // Example categories - modify as needed
        val categories = arrayOf("Food", "Clothing", "Electronics")

        // In onCreate of MainActivity
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = spinnerAdapter

        // Setup RecyclerView för varor
        itemsAdapter = ItemsAdapter(mutableListOf()) // Pseudokod - implementera ItemsAdapter
        rvItems.adapter = itemsAdapter
        rvItems.layoutManager = LinearLayoutManager(this)

        // Lägg till en vara
        btnAddItem.setOnClickListener {
            val itemName = etItemName.text.toString().trim()
            if(itemName.isNotEmpty()) {
                val category = spCategory.selectedItem.toString()

                // Assume you want to add the current date and time when the item is added
                val currentDateTime = getCurrentDateTime() // You need to implement this method

                // Now, pass the currentDateTime as the datetimeAdded parameter
                val item = Item(0, itemName, category, currentDateTime)

                val itemId = dbHelper.addItem(item)
                if (itemId > -1) {
                    Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()
                    // Update RecyclerView
                } else {
                    Toast.makeText(this, "Error adding item", Toast.LENGTH_SHORT).show()
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

        etItemName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                btnAddItem.isEnabled = s.toString().trim().isNotEmpty()
            }

            // Implement other required methods of TextWatcher
        })
    }
}
