package com.example.foodsavier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FoodSaver.db";

    public static final String ACCOUNT_TABLE_NAME = "ACCOUNT";
    public static final String ACCOUNT_COL_1_LNAME = "LAST_NAME";
    public static final String ACCOUNT_COL_2_FNAME = "FIRST_NAME";
    public static final String ACCOUNT_COL_3_USERNAME = "USERNAME";
    public static final String ACCOUNT_COL_4_PASSWORD = "PASSWORD";
    public static final String ACCOUNT_COL_5_ID = "ID";

    public static final String FOOD_TABLE_NAME = "FOOD";
    public static final String FOOD_COL_1_NAME = "NAME";
    public static final String FOOD_COL_2_QUALITY = "QUALITY";
    public static final String FOOD_COL_3_CATEGORY = "CATEGORY";
    public static final String FOOD_COL_4_EXPIRATIONDATE = "EXPIRATION DATE";
    public static final String FOOD_COL_5_USERID = "ID";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ACCOUNT_TABLE_NAME +
                " (ACCOUNT_COL_1_LNAME TEXT, ACCOUNT_COL_2_FNAME TEXT, " +
                "ACCOUNT_COL_3_USERNAME TEXT, ACCOUNT_COL_4_PASSWORD TEXT, " +
                "ACCOUNT_COL_5_ID INTEGER PRIMARY KEY AUTOINCREMENT)");
        db.execSQL("create table " + FOOD_TABLE_NAME +
                " (FOOD_COL_1_NAME TEXT, FOOD_COL_2_QUALITY INTEGER, " +
                "FOOD_COL_3_CATEGORY TEXT, FOOD_COL_4_EXPIRATIONDATE TEXT, " +
                "FOOD_COL_5_USERID INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE_NAME);
        onCreate(db);
    }

    // insert a new value for Account table where its schema is
    // Account(lname, fname, username, password, ID) ; NOTE: id is auto-increment
    public boolean insertAccount (String lname, String fname, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_COL_1_LNAME, lname);
        contentValues.put(ACCOUNT_COL_2_FNAME, fname);
        contentValues.put(ACCOUNT_COL_3_USERNAME, username);
        contentValues.put(ACCOUNT_COL_4_PASSWORD, password);
        long result = db.insert(ACCOUNT_TABLE_NAME, null, contentValues);

        db.close();
        if (result == -1)
            return  false;
        else
            return  true;

    }

    // insert a new value for FOOD table where its schema is
    // FOOD(name, quality, category, expirationDate, id)
    public boolean insertFood (String name, int quality, String category, String expirationDate, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FOOD_COL_1_NAME, name);
        contentValues.put(FOOD_COL_2_QUALITY, quality);
        contentValues.put(FOOD_COL_3_CATEGORY, category);
        contentValues.put(FOOD_COL_4_EXPIRATIONDATE, expirationDate);
        contentValues.put(FOOD_COL_5_USERID, id);
        long result = db.insert(FOOD_TABLE_NAME, null, contentValues);
        if (result == -1)
            return  false;
        else
            return  true;
    }

    public Cursor getAllAccounts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + ACCOUNT_TABLE_NAME, null);
        return res;
    }

    public Cursor getAllFoods() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + FOOD_TABLE_NAME, null);
        return res;
    }

    public boolean updateFood(String name, int quality, String category, String expirationDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FOOD_COL_1_NAME, name);
        contentValues.put(FOOD_COL_2_QUALITY, quality);
        contentValues.put(FOOD_COL_3_CATEGORY, category);
        contentValues.put(FOOD_COL_4_EXPIRATIONDATE, expirationDate);
        db.update(FOOD_TABLE_NAME, contentValues, "name = ?", new String[] {name});
        return true;
    }

    public  Integer deleteFood (String name, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(FOOD_TABLE_NAME, "name = ? and id = ?", new String[] {name, id});
    }

    // return true if data exist, else return false
    public boolean searchAccount(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + ACCOUNT_TABLE_NAME, null);

        while (res.moveToNext()) {
            if (res.getString(2) == username) {
                res.close();
                return true;
            }
        }
        res.close();
        return false;
    }



    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
