package com.example.pharmaease20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MedicineDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "medicine_db";
    private static final int DATABASE_VERSION = 2;  // Incremented version for schema changes

    public static final String TABLE_NAME = "medicine";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_IMAGE = "image";

    public MedicineDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_PRICE + " TEXT NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                COLUMN_IMAGE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table and recreate it when upgrading
        Log.d("DB_UPGRADE", "Upgrading database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert medicine details safely using ContentValues
    public long insertMedicine(String name, String price, String description, String type, int quantity, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_IMAGE, imagePath);

        // Log database insertion values
        Log.d("DB_INSERT", "Inserting: " + name + ", " + price + ", " + description + ", " + type + ", " + quantity + ", " + imagePath);

        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1) {
            Log.e("DB_INSERT", "Insert failed");
        } else {
            Log.d("DB_INSERT", "Insert success, ID: " + result);
        }
        db.close();
        return result;  // Returns row ID if successful, -1 if failed
    }

    // Retrieve all medicines from the database
    public Cursor getAllMedicines() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COLUMN_ID + " AS _id, " +
                COLUMN_NAME + ", " +
                COLUMN_PRICE + ", " +
                COLUMN_TYPE + " FROM " + TABLE_NAME, null);
    }

    // Check if the table exists in the database
    public boolean doesTableExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + TABLE_NAME + "'", null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }
}
