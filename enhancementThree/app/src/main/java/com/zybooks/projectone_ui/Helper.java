package com.zybooks.projectone_ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.core.content.ContextCompat;

public class Helper {

    public static void applySavedBackground(View root, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        int bgResId = prefs.getInt("selected_background", -1);
        if (bgResId != -1) {
            try {
                root.setBackgroundResource(bgResId); // if drawable
            } catch (Exception e) {
                // fallback for colors
                root.setBackgroundColor(ContextCompat.getColor(context, bgResId));
            }
        }
    }
}

