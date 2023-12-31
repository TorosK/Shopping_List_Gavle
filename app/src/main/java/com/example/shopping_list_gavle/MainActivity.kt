// AndroidStudioProjects\Shopping_List_Gavle\app\src\main\java\com\example\shopping_list_gavle
// \MainActivity.kt

package com.example.shopping_list_gavle

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date


class MainActivity : AppCompatActivity(), ItemsAdapter.ItemClickListener {
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var dbHelper: DatabaseHelper

    private var showingDeletedItems = false
    private var showingPurchasedItems = false

    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize DatabaseHelper and ItemsAdapter
        dbHelper = DatabaseHelper(this)
        itemsAdapter = ItemsAdapter(this)
        val rvItems = findViewById<RecyclerView>(R.id.rvItems)
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = itemsAdapter

        // Load existing items from the database for the default list
        val existingItems = dbHelper.getAllItems()
        itemsAdapter.enableItemClick = true
        itemsAdapter.setItems(existingItems, ItemsAdapter.ListType.DEFAULT) // Updated call

        // UI-komponenter
        val etItemName = findViewById<EditText>(R.id.etItemName)
        val spCategory = findViewById<Spinner>(R.id.spCategory)

        val btnShowDeletedItems = findViewById<Button>(R.id.btnShowDeletedItems)
        val btnShowPurchasedItems = findViewById<Button>(R.id.btnShowPurchasedItems)

        // Example categories
        val categories = arrayOf(
            getString(R.string.category_miscellaneous),
            getString(R.string.category_food),
            getString(R.string.category_clothes),
            getString(R.string.category_electronics)
        )

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
                    Toast.makeText(this, getString(R.string.item_added_success), Toast.LENGTH_SHORT).show()
                    // Update RecyclerView with the latest items
                    updateRecyclerView()
                } else {
                    Toast.makeText(this, getString(R.string.error_adding_item), Toast.LENGTH_SHORT).show()
                }
            } else {
                etItemName.error = getString(R.string.empty_item_name_error)
            }
        }

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = spinnerAdapter

        btnShowDeletedItems.setOnClickListener {
            if (showingDeletedItems) {
                updateRecyclerView() // Switch back to default list
                btnShowDeletedItems.setBackgroundResource(R.drawable.button_gradient) // Reset button appearance
                showingDeletedItems = false
            } else {
                val deletedItems = dbHelper.getDeletedItems() // Fetch deleted items
                itemsAdapter.setItems(deletedItems.map { it.toItem() }, ItemsAdapter.ListType.DELETED)
                btnShowDeletedItems.setBackgroundResource(android.R.drawable.button_onoff_indicator_on) // Change button appearance
                showingDeletedItems = true
                btnShowPurchasedItems.setBackgroundResource(R.drawable.button_gradient) // Reset other button appearance
                showingPurchasedItems = false
            }
        }

        btnShowPurchasedItems.setOnClickListener {
            if (showingPurchasedItems) {
                updateRecyclerView() // Switch back to default list
                btnShowPurchasedItems.setBackgroundResource(R.drawable.button_gradient) // Reset button appearance
                showingPurchasedItems = false
            } else {
                val purchasedItems = dbHelper.getPurchasedItems() // Fetch purchased items
                itemsAdapter.setItems(purchasedItems.map { it.toItem() }, ItemsAdapter.ListType.PURCHASED)
                btnShowPurchasedItems.setBackgroundResource(android.R.drawable.button_onoff_indicator_on) // Change button appearance
                showingPurchasedItems = true
                btnShowDeletedItems.setBackgroundResource(R.drawable.button_gradient) // Reset other button appearance
                showingDeletedItems = false
            }
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
        val items = dbHelper.getAllItems()
        itemsAdapter.setItems(items, ItemsAdapter.ListType.DEFAULT)
    }

    override fun onItemClick(item: Item) {
        // Show a dialog or menu here to let the user mark the item as purchased or delete it
        // Example:
        showItemActionDialog(item)
    }

    private fun showItemActionDialog(item: Item) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.item_action_dialog_title))

        val message = getString(
            R.string.item_details_message,
            item.name,
            item.category,
            item.datetimeAdded
        )
        builder.setMessage(message)

        builder.setPositiveButton(getString(R.string.mark_as_purchased)) { dialog, which ->
            markItemAsPurchased(item)
        }

        builder.setNegativeButton(getString(R.string.delete)) { dialog, which ->
            deleteItem(item)
        }

        builder.setNeutralButton(getString(R.string.cancel), null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun markItemAsPurchased(item: Item) {
        dbHelper.purchaseItem(item) // Pass the entire item object
        updateRecyclerView()
    }

    private fun deleteItem(item: Item) {
        dbHelper.deleteItem(item) // Pass the entire item object
        updateRecyclerView()
    }
}