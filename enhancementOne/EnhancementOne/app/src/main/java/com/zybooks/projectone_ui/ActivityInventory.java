package com.zybooks.projectone_ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ActivityInventory extends AppCompatActivity {
    private InventoryManager inventoryManager;
    private InventoryAdapter inventoryAdapter;

    // Items for listview
    ListView listView;
    EditText new_data;
    ImageView enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Allows for action bar functionality
        // Calls action bar
        ActionBar actionBar = getSupportActionBar();
        //Clears the action bar so no title is show
        actionBar.setTitle("");

        // shows the action bar back button
        actionBar.setDisplayHomeAsUpEnabled(true);


        // Link to the correct layout
        setContentView(R.layout.activity_inventory);

        LinearLayout rootLayout = findViewById(R.id.inventory); // make sure your root layout has an ID
        Helper.applySavedBackground(rootLayout, this);


        // Creates ID for adding items to list
        new_data = findViewById(R.id.new_data);
        enter = findViewById(R.id.add);

        // Button for removing items from list
        Button btnRemove;

        // Reference the ListView that exists
        listView = findViewById(R.id.myList);

        inventoryManager = new InventoryManager(this);
        inventoryManager.loadList();
        if(inventoryManager.getItems().size() < 0) {
            // These are here just to make it look a little populated
            inventoryManager.addItem("Pliers temp");
            inventoryManager.addItem("Screwdrivers temp");
            inventoryManager.addItem("Hammers temp");
        }
        // Initialize custom adapter
        inventoryAdapter = new InventoryAdapter(this, inventoryManager.getItems());
        listView.setAdapter(inventoryAdapter);

        // Button click to add new items
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = new_data.getText().toString();
                if(text == null || text.length() == 0){
                    Toast.makeText(ActivityInventory.this, "Enter an item.", Toast.LENGTH_SHORT).show();
                } else {
                    inventoryManager.addItem(text);
                    inventoryAdapter.notifyDataSetChanged();
                    inventoryManager.saveList();
                    new_data.setText("");
                    Toast.makeText(ActivityInventory.this, "Added: " + text, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRemove = findViewById(R.id.btnRemove);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inventoryManager.getItems().isEmpty()) {
                    inventoryManager.removeFirst();
                    inventoryAdapter.notifyDataSetChanged();
                    inventoryManager.saveList();
                }
            }
        });
    }
    // Enables back button function in action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Custom adapter for inventory items
    public class InventoryAdapter extends ArrayAdapter<InventoryManager.InventoryItem> {
        public InventoryAdapter(Context context, ArrayList<InventoryManager.InventoryItem> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            InventoryManager.InventoryItem item = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.inventory_increment_decrement, parent, false);
            }

            TextView nameText = convertView.findViewById(R.id.itemName);
            TextView countText = convertView.findViewById(R.id.itemCount);
            Button btnInc = convertView.findViewById(R.id.btnIncrement);
            Button btnDec = convertView.findViewById(R.id.btnDecrement);
            Button btnDel = convertView.findViewById(R.id.btnDelete);

            nameText.setText(item.name);
            countText.setText(String.valueOf(item.count));

            btnInc.setOnClickListener(v -> {
                item.count++;
                countText.setText(String.valueOf(item.count));
                inventoryManager.saveList();
            });

            btnDec.setOnClickListener(v -> {
                if (item.count > 0) {
                    item.count--;
                    countText.setText(String.valueOf(item.count));
                    inventoryManager.saveList();
                }
            });
            // FINISH BUTTON DELETE
            btnDel.setOnClickListener(v -> {
                inventoryManager.getItems().remove(position);
                inventoryAdapter.notifyDataSetChanged();
                inventoryManager.saveList();
            });

            return convertView;
        }
    }

}