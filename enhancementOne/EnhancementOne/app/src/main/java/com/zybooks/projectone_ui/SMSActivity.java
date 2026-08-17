package com.zybooks.projectone_ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SMSActivity extends AppCompatActivity {

    EditText editTextPhone, editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_smsactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout rootLayout = findViewById(R.id.main);
        Helper.applySavedBackground(rootLayout, this);

        // Allows for action bar functionality
        // Calls action bar
        ActionBar actionBar = getSupportActionBar();
        //Clears the action bar so no title is show
        actionBar.setTitle("");

        // shows the action bar back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Initialize sms EditText
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextMessage = findViewById(R.id.editTextMessage);
    }
    // Added Permissions below
public void btnPermission(View view){
    TextView tvStatus = findViewById(R.id.txtSmsStatus);

    if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        == PackageManager.PERMISSION_GRANTED){
        tvStatus.setText("Permission already granted");
    }else {
        // Requests permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                1);
    }
}
    // Method to safely send SMS if permission granted
    private void sendSmsGrantedAlert(){
        String phone = editTextPhone.getText().toString();
        String message = editTextMessage.getText().toString();

        if(!phone.isEmpty() && !message.isEmpty()){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, message, null, null);
                Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cannot send SMS, permission not granted", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Phone or message is empty", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        TextView tvStatus = findViewById(R.id.txtSmsStatus);

        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                tvStatus.setText("Permission granted");
                sendSmsGrantedAlert();  // You can send SMS if this is the right trigger point
            } else {
                tvStatus.setText("Permission denied");
                Toast.makeText(this, "SMS feature disabled, but app will continue", Toast.LENGTH_SHORT).show();
            }
        }
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