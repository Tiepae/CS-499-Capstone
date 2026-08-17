package com.zybooks.projectone_ui;

import android.content.Context;
import android.database.Cursor;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryManager {

    private ArrayList<InventoryItem> items;

    private HashMap<String, InventoryItem> inventoryMap;

    private Context context;

    // InventoryHelperDatabase object
    private InventoryHelperDatabase database;

    public InventoryManager(Context context) {
        this.context = context;

        items = new ArrayList<>();
        inventoryMap = new HashMap<>();

        // Initializes my database I created in InventoryHelperDatabase
        database = new InventoryHelperDatabase(context);

        loadList();  // Load saved items when app starts
    }

    public ArrayList<InventoryItem> getItems() {
        return items;
    }

    public HashMap<String, InventoryItem> getInventoryMap() {
        return inventoryMap;
    }

    public void addItem(String item){
        //checking duplicate item in hashmap
        if(inventoryMap.containsKey(item)) {
            Toast.makeText(context, "Duplicate Item already exists!", Toast.LENGTH_SHORT).show();
            return;
        }

        InventoryItem myItem = new InventoryItem(item);
        items.add(myItem);

        database.addItem(myItem.name, myItem.count);
    }

    // Remove item database code
    public void removeItem(int position) {
        InventoryItem item = items.get(position);

        database.deleteData(String.valueOf(item.id));

        items.remove(position);
    }

    public void updateItem(InventoryItem item) {
        database.updateData(
                String.valueOf(item.id),
                item.name,
                item.count
        );
    }

    // Loadlist from SQLite database
    public void loadList() {
        items.clear();
        inventoryMap.clear();

        Cursor cursor = database.readAllData();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String itemName = cursor.getString(1);
                int itemCount = cursor.getInt(2);

                InventoryItem item = new InventoryItem(id, itemName, itemCount);

                items.add(item);
                inventoryMap.put(itemName.toLowerCase(), item);
            }
            cursor.close();
        }
    }

    // Inventory item class
    public static class InventoryItem {
        int id;
        String name;
        int count;

        public InventoryItem(int id, String name, int count) {
            this.id = id;
            this.name = name;
            this.count = count;
        }

        public InventoryItem(String name) {
            this.name = name;
            this.count = 0;
        }

        public InventoryItem(String name, int count) {
            this.name = name;
            this.count = count;
        }

        public String getItemName() {
            return name;
        }
    }
}