package com.zybooks.projectone_ui;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class InventoryManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private ArrayList<InventoryItem> items;

    public InventoryManager(Context context) {
        prefs = context.getSharedPreferences("InventoryPrefs", Context.MODE_PRIVATE);
        editor = prefs.edit();
        items = new ArrayList<>();
        loadList();  // Load saved items when app starts
    }
    public ArrayList<InventoryItem> getItems() {
        return items;
    }

    public void addItem(String item){
        items.add(new InventoryItem(item));
        //inventoryAdapter.notifyDataSetChanged(); TODO: FIX
        saveList();
    }
    public void removeFirst() {
        items.remove(0); // removes the first item
    }
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
                        items.add(new InventoryItem(parts[0], Integer.parseInt(parts[1])));
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
    }
}
