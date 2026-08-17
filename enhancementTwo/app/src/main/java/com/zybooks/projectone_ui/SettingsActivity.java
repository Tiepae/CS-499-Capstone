package com.zybooks.projectone_ui;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout rootLayout;


    private SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for this activity
        setContentView(R.layout.activity_settings);

        prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);


        // Allows for action bar functionality
        // Calls action bar
        ActionBar actionBar = getSupportActionBar();
        //Clears the action bar so no title is show
        actionBar.setTitle("");

        // shows the action bar back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Root layout
        rootLayout = findViewById(R.id.settings);
        Helper.applySavedBackground(rootLayout, this);

        // Buttons
        Button btnPurple = findViewById(R.id.btnPurple);
        Button btnBlack = findViewById(R.id.btnBlack);
        Button btnBlue = findViewById(R.id.btnBlue);

        // Set click listeners
        btnPurple.setOnClickListener(v -> {
            rootLayout.setBackgroundResource(R.drawable.bg_purple_gradient);
            prefs.edit().putInt("selected_background", R.drawable.bg_purple_gradient).apply();
        });

        btnBlack.setOnClickListener(v -> {
            rootLayout.setBackgroundColor(Color.BLACK);
            prefs.edit().putInt("selected_background", R.color.black).apply();
        });

        btnBlue.setOnClickListener(v -> {
            rootLayout.setBackgroundColor(Color.BLUE);
            prefs.edit().putInt("selected_background", R.color.blue).apply();
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
}
