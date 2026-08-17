package com.zybooks.projectone_ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private ArrayList<InventoryItem> items;

    private HashMap<String, InventoryItem> inventoryMap;

    private Context context;

    public InventoryManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("InventoryPrefs", Context.MODE_PRIVATE);
        editor = prefs.edit();
        items = new ArrayList<>();
        inventoryMap = new HashMap<>(); //hashmap declared
        loadList();  // Load saved items when app starts
    }
    public ArrayList<InventoryItem> getItems() {
        return items;
    }

    // Initializes Hashmap
    //inventoryMap = new HashMap<>();

    public HashMap<String, InventoryItem> getInventoryMap() {
        return inventoryMap;
    }

    // TODO FIX THIS
    // SearchView method
//private void initSearchWidget()
//{
//    SearchView searchView = (SearchView) findViewById(R.id.search_bar);
//
//    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//        @Override
//        public boolean onQueryTextChange(String s) {
//            return false;
//        }
//
//        @Override
//        public boolean onQueryTextSubmit(String s) {
//            ArrayList<InventoryItem> results = new ArrayList<>();
//            for(InventoryManager.InventoryItem item : inventoryManager.getItems()){
//                if (item.getItemName().toLowerCase().contains(s.toLowerCase()))
//                {
//                    results.add(item);
//                }
//            }
//            ActivityInventory.InventoryAdapter.updateList(results);
//            return false;
//        }
//    });
//}

    public void addItem(String item){
        //checking duplicate item in hashmap
        if(inventoryMap.containsKey(item)) {
            Toast.makeText(context, "Duplicate Item already exists!", Toast.LENGTH_SHORT).show();
            return;
            //
        }

        InventoryItem myItem= new InventoryItem(item);
        items.add(myItem);
        //inventoryAdapter.notifyDataSetChanged(); TODO: FIX
        saveList();
    }
    // Related to remove button in activity
    //public void removeFirst() {
   //     items.remove(0); // removes the first item
    //}
    public void removeItem(int index) {
        items.remove(index);
    }

    // Save list to shared preferences
    public void saveList(){
        StringBuilder sb = new StringBuilder();
        for(InventoryItem item : items) {
            sb.append(item.name).append(",,").append(item.count).append(";;");
        }

        editor.putString("item_list", sb.toString());
        editor.apply();
    }

    // Load list from shared preferences
    public void loadList(){
        String saved = prefs.getString("item_list", "");
        items.clear();  // clear the existing list

        if(!saved.isEmpty()){
            String[] savedItems = saved.split(";;");
            for(String s : savedItems){
                if(!s.trim().isEmpty()) {
                    String[] parts = s.split(",,");
                    if(parts.length == 2) {
                        InventoryItem item = new InventoryItem(parts[0], Integer.parseInt(parts[1]));
                        items.add(item);
                        inventoryMap.put(parts[0].toLowerCase(), item);
                    }
                }
            }
        }
    }
    // Inventory item class
    public static class InventoryItem {
        String name;
        int count;

        public InventoryItem(String name) {
            this.name = name;
            this.count = 0; // start count at 0
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
