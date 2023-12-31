// AndroidStudioProjects\Shopping_List_Gavle\app\src\main\java\com\example\shopping_list_gavle\MainActivity.kt

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

// MainActivity class inherits from AppCompatActivity and implements ItemClickListener from ItemsAdapter
class MainActivity : AppCompatActivity(), ItemsAdapter.ItemClickListener {
    private lateinit var itemsAdapter: ItemsAdapter  // Adapter for the RecyclerView
    private lateinit var dbHelper: DatabaseHelper   // Helper class for database operations

    private var showingDeletedItems = false         // Flag to track if deleted items are being shown
    private var showingPurchasedItems = false       // Flag to track if purchased items are being shown

    // Function to get the current date and time in a specific format
    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Set the layout for the activity

        // Initialize dbHelper and itemsAdapter
        dbHelper = DatabaseHelper(this)
        itemsAdapter = ItemsAdapter(this)

        // Set up RecyclerView with LinearLayoutManager and the adapter
        val rvItems = findViewById<RecyclerView>(R.id.rvItems)
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = itemsAdapter

        // Load items from the database and update the adapter
        val existingItems = dbHelper.getAllItems()
        itemsAdapter.enableItemClick = true
        itemsAdapter.setItems(existingItems, ItemsAdapter.ListType.DEFAULT)

        // UI components
        val etItemName = findViewById<EditText>(R.id.etItemName)  // EditText for item name
        val spCategory = findViewById<Spinner>(R.id.spCategory)   // Spinner for category selection

        val btnShowDeletedItems = findViewById<Button>(R.id.btnShowDeletedItems)  // Button to show deleted items
        val btnShowPurchasedItems = findViewById<Button>(R.id.btnShowPurchasedItems)  // Button to show purchased items

        // Categories array for the spinner (dropdown list)
        val categories = arrayOf(
            getString(R.string.category_miscellaneous),
            getString(R.string.category_food),
            getString(R.string.category_clothes),
            getString(R.string.category_electronics)
        )

        // Setting up the Add Item button
        val btnAddItem = findViewById<Button>(R.id.btnAddItem)
        btnAddItem.setOnClickListener {
            // Handle add item logic
            val itemName = etItemName.text.toString().trim()
            if(itemName.isNotEmpty()) {
                val category = spCategory.selectedItem.toString()
                val currentDateTime = getCurrentDateTime()
                val item = Item(0, itemName, category, currentDateTime)

                val itemId = dbHelper.addItem(item)
                if (itemId > -1) {
                    // Show success message and update RecyclerView
                    Toast.makeText(this, getString(R.string.item_added_success), Toast.LENGTH_SHORT).show()
                    updateRecyclerView()
                } else {
                    // Show error message if addition failed
                    Toast.makeText(this, getString(R.string.error_adding_item), Toast.LENGTH_SHORT).show()
                }
            } else {
                // Set error message for empty item name
                etItemName.error = getString(R.string.empty_item_name_error)
            }
        }

        // Set up the spinner with categories
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = spinnerAdapter

        // Set up the Show Deleted Items button
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

        // Set up the Show Purchased Items button
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

        // TextWatcher to enable/disable Add Item button based on text input
        etItemName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                btnAddItem.isEnabled = s.toString().trim().isNotEmpty()
            }
        })
    }

    // Function to update the RecyclerView with the latest items
    private fun updateRecyclerView() {
        val items = dbHelper.getAllItems()
        itemsAdapter.setItems(items, ItemsAdapter.ListType.DEFAULT)
    }

    // Handle item clicks based on the type of list being displayed
    override fun onItemClick(item: Item) {
        if (itemsAdapter.listType == ItemsAdapter.ListType.DEFAULT) {
            showItemActionDialog(item)
        }
    }

    // Function to show dialog for item actions (purchase or delete)
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

    // Function to mark an item as purchased
    private fun markItemAsPurchased(item: Item) {
        dbHelper.purchaseItem(item)
        updateRecyclerView()
    }

    // Function to delete an item
    private fun deleteItem(item: Item) {
        dbHelper.deleteItem(item)
        updateRecyclerView()
    }
}