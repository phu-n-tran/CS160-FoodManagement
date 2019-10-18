package com.example.foodsavierapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "foodSaverApp.db";
    private static final int DB_VERSION = 1;

    private static final String ACCOUNT_TABLE = "ACCOUNT";
    private static final String ACCOUNT_COL_1_LNAME = "LNAME";
    private static final String ACCOUNT_COL_2_FNAME = "FNAME";
    private static final String ACCOUNT_COL_3_USERNAME = "USERNAME";
    private static final String ACCOUNT_COL_4_PASSWORD = "PASSWORD";

    private static final String FOOD_TABLE = "FOOD";
    private static final String FOOD_COL_1_NAME = "NAME";
    private static final String FOOD_COL_2_CATEGORY = "CATEGORY";

    private static final String OWNS_TABLE = "OWNS";
    private static final String OWNS_COL_1_USERNAME = "A_USERNAME";
    private static final String OWNS_COL_2_FOODNAME = "FOODNAME";
    private static final String OWNS_COL_3_QUALITY = "QUALITY";
    private static final String OWNS_COL_4_EXPIRATIONDATE = "EXPIRATION";


    private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + ACCOUNT_TABLE + " (" +
            ACCOUNT_COL_1_LNAME + " TEXT, " + ACCOUNT_COL_2_FNAME + " TEXT, " +
            ACCOUNT_COL_3_USERNAME + " TEXT PRIMARY KEY, " + ACCOUNT_COL_4_PASSWORD + " TEXT);";

    private  static final String CREATE_TABLE_FOOD = "CREATE TABLE " + FOOD_TABLE + " (" +
            FOOD_COL_1_NAME + " TEXT PRIMARY KEY, " + FOOD_COL_2_CATEGORY + " TEXT);";

    private static final  String CREATE_TABLE_OWNS = "CREATE TABLE " + OWNS_TABLE + " (" +
            OWNS_COL_1_USERNAME + " TEXT, " + OWNS_COL_2_FOODNAME + " TEXT, " + OWNS_COL_3_QUALITY +
            " INTEGER, " + OWNS_COL_4_EXPIRATIONDATE + " TEXT, FOREIGN KEY (" +
            OWNS_COL_1_USERNAME + ") REFERENCES " + ACCOUNT_TABLE + "(" + ACCOUNT_COL_3_USERNAME +
            "), FOREIGN KEY (" + OWNS_COL_2_FOODNAME + ") REFERENCES " + FOOD_TABLE + "(" +
            FOOD_COL_1_NAME + "), PRIMARY KEY (" + OWNS_COL_1_USERNAME + ", " +
            OWNS_COL_2_FOODNAME + ") );";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_FOOD);
        db.execSQL(CREATE_TABLE_OWNS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + OWNS_TABLE);
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    //******************************************
    //*********ACCOUNT METHODS*******************
    //******************************************
    public boolean insertAccount (String lname, String fname, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_COL_1_LNAME, lname);
        contentValues.put(ACCOUNT_COL_2_FNAME, fname);
        contentValues.put(ACCOUNT_COL_3_USERNAME, username);
        contentValues.put(ACCOUNT_COL_4_PASSWORD, password);
        long result = db.insert(ACCOUNT_TABLE, null, contentValues);

        if (db != null && db.isOpen())
            db.close();

        if (result == -1)
            return  false;
        else
            return  true;
    }

    public Cursor getAllAccounts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + ACCOUNT_TABLE, null);
        return res;
    }

    // return true if USERNAME exist, else return false
    public boolean searchAccount(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + ACCOUNT_TABLE, null);

        while (res.moveToNext()) {
            if (res.getString(2).equals(username)) {
                res.close();
                return true;
            }
        }
        res.close();
        return false;
    }





    //******************************************
    //*********FOOD METHODS*******************
    //******************************************
    public boolean insertFood (String name, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FOOD_COL_1_NAME, name);
        contentValues.put(FOOD_COL_2_CATEGORY, category);
        long result = db.insert(FOOD_TABLE, null, contentValues);

        if (db != null && db.isOpen())
            db.close();

        if (result == -1)
            return  false;
        else
            return  true;
    }

    public Cursor getAllFoods() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + FOOD_TABLE, null);
        return res;
    }



    public  Integer deleteFood (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(FOOD_TABLE, "name = ?", new String[] {name});
    }







    //******************************************
    //*********OWNS METHODS*******************
    //******************************************






/*

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

 */




}
