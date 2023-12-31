// AndroidStudioProjects\Shopping_List_Gavle\app\src\main\java\com\example\shopping_list_gavle\DatabaseHelper.kt

package com.example.shopping_list_gavle

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, context.getString(R.string.database_name), null, DATABASE_VERSION) {

    // Flyttade konstantvärden från companion object till klassnivå
    private val DATABASE_NAME = context.getString(R.string.database_name)
    private val TABLE_ITEMS = context.getString(R.string.table_items)
    private val TABLE_DELETED = context.getString(R.string.table_deleted)
    private val TABLE_PURCHASED = context.getString(R.string.table_purchased)

    companion object {
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // SQL-skript för att skapa tabeller
        db.execSQL("""
            CREATE TABLE items (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                category TEXT,
                datetime_added DATETIME DEFAULT CURRENT_TIMESTAMP
            );
        """)
        db.execSQL("""
            CREATE TABLE deleted (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                item_id INTEGER NOT NULL,
                name TEXT NOT NULL,
                category TEXT,
                datetime_deleted DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (item_id) REFERENCES items (id)
            );
        """)
        db.execSQL("""
            CREATE TABLE purchased (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                item_id INTEGER NOT NULL,
                name TEXT NOT NULL,
                category TEXT,
                datetime_purchased DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (item_id) REFERENCES items (id)
            );
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            // Example: Add a new column to the items table
            db.execSQL("ALTER TABLE $TABLE_ITEMS ADD COLUMN new_column TEXT")
        }
        // Handle other version upgrades
    }

    // CRUD-metoder nedan

    fun addItem(item: Item): Long {
        return try {
            val db = writableDatabase
            val values = ContentValues().apply {
                put("name", item.name)
                put("category", item.category)
                // datetime_added sätts automatiskt till nuvarande tidsstämpel
                // Add other fields ?
            }
            val id = db.insert(TABLE_ITEMS, null, values)
            db.close()
            id
        } catch (e: Exception) {
            -1 // Indicate failure
        }
    }

    fun deleteItem(item: Item) {
        val db = writableDatabase
        // Flytta först item till "deleted" tabellen
        db.execSQL("""
        INSERT INTO $TABLE_DELETED (item_id, name, category)
        VALUES (${item.id}, '${item.name}', '${item.category}');
    """)
        // Ta bort item från "items" tabellen
        db.delete(TABLE_ITEMS, "id = ?", arrayOf(item.id.toString()))
        db.close()
    }

    fun purchaseItem(item: Item) {
        val db = writableDatabase
        // Flytta först item till "purchased" tabellen
        db.execSQL("""
        INSERT INTO $TABLE_PURCHASED (item_id, name, category)
        VALUES (${item.id}, '${item.name}', '${item.category}');
    """)
        // Ta bort item från "items" tabellen
        db.delete(TABLE_ITEMS, "id = ?", arrayOf(item.id.toString()))
        db.close()
    }

    fun getAllItems(): List<Item> {
        val itemList = mutableListOf<Item>()
        val db = readableDatabase
        val cursor = db.query(TABLE_ITEMS, arrayOf("id", "name", "category", "datetime_added"), null, null, null, null, "datetime_added DESC")
        if (cursor.moveToFirst()) {
            do {
                val item = Item(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("category")),
                    cursor.getString(cursor.getColumnIndex("datetime_added"))
                )
                itemList.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return itemList
    }

    fun getDeletedItems(): List<DeletedItem> {
        val deletedItemList = mutableListOf<DeletedItem>()
        val db = readableDatabase
        val cursor = db.query(TABLE_DELETED, arrayOf("id", "item_id", "name", "category", "datetime_deleted"), null, null, null, null, "datetime_deleted DESC")

        if (cursor.moveToFirst()) {
            do {
                val deletedItem = DeletedItem(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getInt(cursor.getColumnIndex("item_id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("category")),
                    cursor.getString(cursor.getColumnIndex("datetime_deleted"))
                )
                deletedItemList.add(deletedItem)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return deletedItemList
    }

    fun getPurchasedItems(): List<PurchasedItem> {
        val purchasedItemList = mutableListOf<PurchasedItem>()
        val db = readableDatabase
        val cursor = db.query(TABLE_PURCHASED, arrayOf("id", "item_id", "name", "category", "datetime_purchased"), null, null, null, null, "datetime_purchased DESC")

        if (cursor.moveToFirst()) {
            do {
                val purchasedItem = PurchasedItem(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getInt(cursor.getColumnIndex("item_id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("category")),
                    cursor.getString(cursor.getColumnIndex("datetime_purchased"))
                )
                purchasedItemList.add(purchasedItem)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return purchasedItemList
    }

    fun restoreDeletedItem(deletedItem: DeletedItem) {
        val db = writableDatabase
        // Återställ item från "deleted" tabellen till "items" tabellen
        db.execSQL("""
        INSERT INTO $TABLE_ITEMS (id, name, category)
        SELECT item_id, name, category FROM $TABLE_DELETED WHERE id = ${deletedItem.id};
    """)
        // Ta bort item från "deleted" tabellen
        db.delete(TABLE_DELETED, "id = ?", arrayOf(deletedItem.id.toString()))
        db.close()
    }

    fun restorePurchasedItem(purchasedItem: PurchasedItem) {
        val db = writableDatabase
        // Återställ item från "purchased" tabellen till "items" tabellen
        db.execSQL("""
        INSERT INTO $TABLE_ITEMS (id, name, category)
        SELECT item_id, name, category FROM $TABLE_PURCHASED WHERE id = ${purchasedItem.id};
    """)
        // Ta bort item från "purchased" tabellen
        db.delete(TABLE_PURCHASED, "id = ?", arrayOf(purchasedItem.id.toString()))
        db.close()
    }
}