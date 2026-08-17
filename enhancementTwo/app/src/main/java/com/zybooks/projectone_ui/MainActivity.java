package com.zybooks.projectone_ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Set's up the login UI before Initializing it
    EditText username_input;
    EditText password_input;
    Button Button1;
    Button Button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        LinearLayout rootLayout = findViewById(R.id.main); // make sure your root layout has an ID
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);

        Helper.applySavedBackground(rootLayout, this);


        // Initializes the user input & buttons
        username_input = findViewById(R.id.username_input);
        password_input = findViewById(R.id.password_input);
        Button1 = findViewById(R.id.Button1);
        Button2 = findViewById(R.id.Button2);
        // This allows the user to login and will bring them to the next page
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String savedUsername = prefs.getString("username", null);
                String savedPassword = prefs.getString("password", null);

                if(username_input.getText().toString().equals(savedUsername) &&
                        password_input.getText().toString().equals(savedPassword)) {
                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, DataGridActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View popupView = inflater.inflate(R.layout.create_new_login, null);

                // Initializes the Create new login data
                EditText new_username_input = popupView.findViewById(R.id.new_username_input);
                EditText new_password_input = popupView.findViewById(R.id.new_password_input);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(popupView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button create_button = popupView.findViewById(R.id.create_button);
                create_button.setOnClickListener(v -> {
                    String username = new_username_input.getText().toString();
                    String password = new_password_input.getText().toString();

                    // Save credentials
                    SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.apply();

                    Toast.makeText(MainActivity.this, "Creation Successful!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                });
                //if(new_username_input.getText().toString().equals("") && new_password_input.getText().toString().equals("")) {

                //}
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}