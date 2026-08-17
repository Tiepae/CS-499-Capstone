package com.zybooks.projectone_ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import android.database.Cursor;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class InventoryHelperDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Inventory";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_inventory";

    private static final String ID = "_id";
    private static final String ITEM_NAME = "item_name";
    private static final String ITEM_COUNT = "item_count";
    private final Context context;

    public InventoryHelperDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null,  DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ITEM_NAME + " TEXT, " +
                        ITEM_COUNT + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    // Creates new Data to the table
    public void addItem(String itemName, int itemCount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, itemName);
        values.put(ITEM_COUNT, itemCount);

        long result = db.insert(TABLE_NAME, null, values);
        if(result == -1){
            Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully added item", Toast.LENGTH_SHORT).show();
        }
    }
    // Reads Data from the table
   Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
   }
    // Updates Data from the Table
   public void updateData(String id, String itemName, int itemCount) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put(ITEM_NAME, itemName);
        cv.put(ITEM_COUNT, itemCount);

        db.update(TABLE_NAME, cv, ID + "=?", new String[]{id});
   }
   // Deletes Data from the table
    public void deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=?", new String[]{id});
    }
}
