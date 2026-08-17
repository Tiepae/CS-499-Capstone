package com.zybooks.projectone_ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DataGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_grid);

        ConstraintLayout rootLayout = findViewById(R.id.data);
        // make sure your root layout has an ID
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);


        Helper.applySavedBackground(rootLayout, this);


        Button smsButton = findViewById(R.id.btnRequestPermission);
        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataGridActivity.this, SMSActivity.class);
                startActivity(intent);
            }
        });
        // Allows for the user to view the inventory tab
        CardView inventoryCard = findViewById(R.id.inventory_click);
        inventoryCard.setOnClickListener(v -> {
            Intent intent = new Intent(DataGridActivity.this, ActivityInventory.class);
            startActivity(intent);
        });

        // WIP- Will allow for user to view setting tab
        CardView settingsCard = findViewById(R.id.settings_click);
        settingsCard.setOnClickListener(v -> {
            Intent intent = new Intent(DataGridActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}

