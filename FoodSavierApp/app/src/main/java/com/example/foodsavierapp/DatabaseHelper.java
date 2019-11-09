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
    private static final String FOOD_COL_3_ESTIMATEDAYS = "ESTIMATEDAYS";

    private static final String OWNS_TABLE = "OWNS";
    private static final String OWNS_COL_1_USERNAME = "USERNAME";
    private static final String OWNS_COL_2_FOODNAME = "FOODNAME";
    private static final String OWNS_COL_3_QUALITY = "QUALITY";
    private static final String OWNS_COL_4_STARTDATE = "STARTDATE";
    private static final String OWNS_COL_5_EXPIRATIONDATE= "EXPIRATIONDATE";
    private static final String OWNS_COL_6_STATUS = "STATUS";
    private static final String OWNS_COL_7_CATEGORY = "CATEGORY";

    private static final String FINISH_TABLE = "FINISH";
    private static final String FINISH_COL_1_USERNAME = "USERNAME";
    private static final String FINISH_COL_2_FOODNAME = "FOODNAME";
    private static final String FINISH_COL_3_QUALITY = "QUALITY";
    private static final String FINISH_COL_4_EXPIRATIONDATE= "EXPIRATIONDATE";
    private static final String FINISH_COL_5_STATUS = "STATUS";
    private static final String FINISH_COL_6_CATEGORY = "CATEGORY";


    private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + ACCOUNT_TABLE + " (" +
            ACCOUNT_COL_1_LNAME + " TEXT, " + ACCOUNT_COL_2_FNAME + " TEXT, " +
            ACCOUNT_COL_3_USERNAME + " TEXT PRIMARY KEY, " + ACCOUNT_COL_4_PASSWORD + " TEXT);";

    private  static final String CREATE_TABLE_FOOD = "CREATE TABLE " + FOOD_TABLE + " (" +
            FOOD_COL_1_NAME + " TEXT PRIMARY KEY, " + FOOD_COL_2_CATEGORY + " TEXT, " +
            FOOD_COL_3_ESTIMATEDAYS + " INTEGER);";

    private static final  String CREATE_TABLE_OWNS = "CREATE TABLE " + OWNS_TABLE + " (" +
            OWNS_COL_1_USERNAME + " TEXT, " + OWNS_COL_2_FOODNAME + " TEXT, " + OWNS_COL_3_QUALITY +
            " INTEGER, " + OWNS_COL_4_STARTDATE + " TEXT, " + OWNS_COL_5_EXPIRATIONDATE +
            " TEXT, " + OWNS_COL_6_STATUS + " TEXT, " + OWNS_COL_7_CATEGORY + " TEXT, PRIMARY KEY (" +
            OWNS_COL_1_USERNAME + ", " + OWNS_COL_2_FOODNAME + ") );";

    private static final String CREATE_TABLE_FINISH = "CREATE TABLE " + FINISH_TABLE + " (" +
            FINISH_COL_1_USERNAME + " TEXT, " + FINISH_COL_2_FOODNAME + " TEXT, " +
            FINISH_COL_3_QUALITY + " INTEGER, " + FINISH_COL_4_EXPIRATIONDATE + " TEXT, " +
            FINISH_COL_5_STATUS + " TEXT, " + FINISH_COL_6_CATEGORY + " TEXT);";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_FOOD);
        db.execSQL(CREATE_TABLE_OWNS);
        db.execSQL(CREATE_TABLE_FINISH);

        // Adding the default for food table
        addDefaultFood(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + OWNS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FINISH_TABLE);
        onCreate(db);
    }


    private void addDefaultFood(SQLiteDatabase db) {
        //***********FREEZER SECTION**************************
        ContentValues contentValues = new ContentValues();
        contentValues.put(FOOD_COL_1_NAME, "MANGO ICE CREAM");
        contentValues.put(FOOD_COL_2_CATEGORY, "FREEZER");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 60);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "PIZZA");
        contentValues.put(FOOD_COL_2_CATEGORY, "FREEZER");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 60);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "SHRIMPS");
        contentValues.put(FOOD_COL_2_CATEGORY, "FREEZER");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 90);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "FISH FILLET");
        contentValues.put(FOOD_COL_2_CATEGORY, "FREEZER");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 90);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "CHICKEN NUGGETS");
        contentValues.put(FOOD_COL_2_CATEGORY, "FREEZER");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 120);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "CORN");
        contentValues.put(FOOD_COL_2_CATEGORY, "FREEZER");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 100);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "PEAS");
        contentValues.put(FOOD_COL_2_CATEGORY, "FREEZER");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 100);
        db.insert(FOOD_TABLE, null, contentValues);


        //****************FRIDGE SECTION*************************
        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "ALMOND MILK");
        contentValues.put(FOOD_COL_2_CATEGORY, "FRIDGE");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 15);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "APPLE JUICE");
        contentValues.put(FOOD_COL_2_CATEGORY, "FRIDGE");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 150);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "BACON");
        contentValues.put(FOOD_COL_2_CATEGORY, "Fridge");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 15);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "CHICKEN");
        contentValues.put(FOOD_COL_2_CATEGORY, "FRIDGE");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 6);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "MILK");
        contentValues.put(FOOD_COL_2_CATEGORY, "FRIDGE");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 20);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "ORANGE JUICE");
        contentValues.put(FOOD_COL_2_CATEGORY, "FRIDGE");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 80);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "EGGS");
        contentValues.put(FOOD_COL_2_CATEGORY, "FRIDGE");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 20);
        db.insert(FOOD_TABLE, null, contentValues);


        //**************PANTRY SECTION**************************
        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "BANANA");
        contentValues.put(FOOD_COL_2_CATEGORY, "PANTRY");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 6);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "COOKIES");
        contentValues.put(FOOD_COL_2_CATEGORY, "PANTRY");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 10);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "WHITE BREAD");
        contentValues.put(FOOD_COL_2_CATEGORY, "PANTRY");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 10);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "BROWN BREAD");
        contentValues.put(FOOD_COL_2_CATEGORY, "PANTRY");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 10);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "EARL GREY TEA");
        contentValues.put(FOOD_COL_2_CATEGORY, "PANTRY");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 60);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "SUGAR");
        contentValues.put(FOOD_COL_2_CATEGORY, "PANTRY");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 200);
        db.insert(FOOD_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(FOOD_COL_1_NAME, "HOT PEPPER");
        contentValues.put(FOOD_COL_2_CATEGORY, "PANTRY");
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, 200);
        db.insert(FOOD_TABLE, null, contentValues);
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

    public Cursor getSingleAccount(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + ACCOUNT_TABLE +
                " where " + ACCOUNT_COL_3_USERNAME + "='" + username + "'", null);
        return res;
    }

    // return true if USERNAME exist, else return false
    public boolean searchExistAccount(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + ACCOUNT_COL_3_USERNAME + " from " + ACCOUNT_TABLE +
                " WHERE " + ACCOUNT_COL_3_USERNAME + "='" + username + "'", null);

        if (res.getCount() == 0) {
            res.close();
            return false;
        }
        res.close();
        return true;
    }

    public boolean updateLastnameAccount(String username, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_COL_1_LNAME, lastName);

        int isUpdated = db.update(ACCOUNT_TABLE, contentValues, ACCOUNT_COL_3_USERNAME + "=?", new String[] {username});

        if(isUpdated == 1)
            return true;
        else
            return false;
    }

    public boolean updateFirstnameAccount(String username, String firstName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_COL_2_FNAME, firstName);

        int isUpdated = db.update(ACCOUNT_TABLE, contentValues, ACCOUNT_COL_3_USERNAME + "=?", new String[] {username});

        if(isUpdated == 1)
            return true;
        else
            return false;
    }

    public boolean updatePasswordAccount(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_COL_4_PASSWORD, password);

        int isUpdated = db.update(ACCOUNT_TABLE, contentValues, ACCOUNT_COL_3_USERNAME + "=?", new String[] {username});

        if(isUpdated == 1)
            return true;
        else
            return false;
    }





    //******************************************
    //*********FOOD METHODS*******************
    //******************************************
    public boolean insertFood (String name, String category, int est_days) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FOOD_COL_1_NAME, name.toUpperCase());
        contentValues.put(FOOD_COL_2_CATEGORY, category.toUpperCase());
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, est_days);
        long result = db.insert(FOOD_TABLE, null, contentValues);

        if (db != null && db.isOpen())
            db.close();

        if (result == -1)
            return  false;
        else
            return  true;
    }

    //use only if food exist
    public boolean improveEstDate(String foodname, int estimateDays) {
        foodname = foodname.toUpperCase();


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + FOOD_TABLE +
                " WHERE " + FOOD_COL_1_NAME + "='" + foodname + "'", null);

        if (res.getCount() == 0) {
            res.close();
            return false;
        }

        res.moveToNext();
        int update_est_days = (Integer.parseInt(res.getString(2)) + estimateDays)/2;

        ContentValues contentValues = new ContentValues();
        contentValues.put(FOOD_COL_3_ESTIMATEDAYS, update_est_days);

        int isUpdated = db.update(FOOD_TABLE, contentValues, FOOD_COL_1_NAME + "=?", new String[] {foodname});

        if (db != null && db.isOpen())
            db.close();
        res.close();

        if(isUpdated == 0)
            return false;

        return true;


    }




    public Cursor getAllFoods() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + FOOD_TABLE, null);
        return res;
    }

    public Cursor getAllFoodsPantry() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + FOOD_TABLE + " where " +
                FOOD_COL_2_CATEGORY + " like 'PANTRY'", null);
        return res;
    }

    public Cursor getAllFoodsFreezer() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + FOOD_TABLE + " where " +
                FOOD_COL_2_CATEGORY + " like 'FREEZER'", null);
        return res;
    }

    public Cursor getAllFoodsFridge() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + FOOD_TABLE + " where " +
                FOOD_COL_2_CATEGORY + " like 'FRIDGE'", null);
        return res;
    }




    public  Integer deleteFood (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(FOOD_TABLE, "name = ?", new String[] {name});
    }


    // return true if FOODNAME exist, else return false
    public boolean searchExistFood(String foodname) {
        foodname = foodname.toUpperCase();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + FOOD_COL_1_NAME + " from " + FOOD_TABLE +
                " WHERE " + FOOD_COL_1_NAME + "='" + foodname + "'", null);

        if (res.getCount() == 0) {
            res.close();
            return false;
        }
        res.close();
        return true;
    }







    //******************************************
    //*********OWNS METHODS*******************
    //******************************************
    public boolean insertOwns (String username, String foodname, int quality, String startDate, String expirationDate, String status, String category) {
        foodname = foodname.toUpperCase();
        status = status.toUpperCase();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OWNS_COL_1_USERNAME, username);
        contentValues.put(OWNS_COL_2_FOODNAME, foodname);
        contentValues.put(OWNS_COL_3_QUALITY, quality);
        contentValues.put(OWNS_COL_4_STARTDATE, startDate);
        contentValues.put(OWNS_COL_5_EXPIRATIONDATE, expirationDate);
        contentValues.put(OWNS_COL_6_STATUS, status);
        contentValues.put(OWNS_COL_7_CATEGORY, category);

        long result = db.insert(OWNS_TABLE, null, contentValues);

        if (db != null && db.isOpen())
            db.close();

        if (result == -1)
            return  false;
        else
            return  true;
    }

    public Cursor getOwnsByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + OWNS_TABLE + " where " +
                OWNS_COL_1_USERNAME + "='" + username +"'", null);
        return res;
    }

    public Cursor isExistRowOwns(String username, String foodname) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + OWNS_TABLE + " where " +
                OWNS_COL_1_USERNAME + "='" + username +"' AND " + OWNS_COL_2_FOODNAME + "='" + foodname + "'", null);
        return res;
    }


    //username and foodname is primary key
    public  Integer deleteOwnsByUsernameFoodname (String username, String foodname) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(OWNS_TABLE,
                OWNS_COL_1_USERNAME + "=? AND " + OWNS_COL_2_FOODNAME + "=?",
                new String[] {username, foodname});
    }



    //******************************************
    //*********FINISH METHODS*******************
    //******************************************

    public boolean insertAndUpdateFinish (String username, String currentDate) {
        final int owns_expireddate_index = 4;


        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + OWNS_TABLE + " where " +
                OWNS_COL_1_USERNAME + " = '" + username + "'", null);

        //if nothing in OWNS table, do nothing
        if (res.getCount() == 0) {
            return true;
        }
        else { //if there is something to check
            while (res.moveToNext()) {
                String [] expired_date_list = res.getString(owns_expireddate_index).split("/"); // mm/dd/yyyy
                String [] current_date_list = currentDate.split("/");

                // compare two years: if expired < current
                if(Integer.parseInt(expired_date_list[2]) < Integer.parseInt(current_date_list[2]) ) {

                    // insert expired item into FINISH TABLE
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(FINISH_COL_1_USERNAME, username);
                    contentValues.put(FINISH_COL_2_FOODNAME, res.getString(1));
                    contentValues.put(FINISH_COL_3_QUALITY, res.getString(2));
                    contentValues.put(FINISH_COL_4_EXPIRATIONDATE, res.getString(4));
                    contentValues.put(FINISH_COL_5_STATUS, "EXPIRED");
                    contentValues.put(FINISH_COL_6_CATEGORY, res.getString(6));

                    long result = db.insert(FINISH_TABLE, null, contentValues);


                    if (result == -1)
                        return  false;
                    else {
                        // delete the expired item in the OWNS TABLE
                        db.delete(OWNS_TABLE,
                                OWNS_COL_1_USERNAME + "=? AND " + OWNS_COL_2_FOODNAME + "=?",
                                new String[] {username, res.getString(1)});

                        return true;
                    }



                } // if two year is the same
                else if(Integer.parseInt(expired_date_list[2]) == Integer.parseInt(current_date_list[2]) ) {

                    //check two months: if expire < current
                    if(Integer.parseInt(expired_date_list[0]) < Integer.parseInt(current_date_list[0])) {
                        // insert expired item into FINISH TABLE
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(FINISH_COL_1_USERNAME, username);
                        contentValues.put(FINISH_COL_2_FOODNAME, res.getString(1));
                        contentValues.put(FINISH_COL_3_QUALITY, res.getString(2));
                        contentValues.put(FINISH_COL_4_EXPIRATIONDATE, res.getString(4));
                        contentValues.put(FINISH_COL_5_STATUS, "EXPIRED");
                        contentValues.put(FINISH_COL_6_CATEGORY, res.getString(6));

                        long result = db.insert(FINISH_TABLE, null, contentValues);


                        if (result == -1)
                            return  false;
                        else {
                            // delete the expired item in the OWNS TABLE
                            db.delete(OWNS_TABLE,
                                    OWNS_COL_1_USERNAME + "=? AND " + OWNS_COL_2_FOODNAME + "=?",
                                    new String[] {username, res.getString(1)});

                            return true;
                        }


                    } // if two months are equal
                    else if (Integer.parseInt(expired_date_list[0]) == Integer.parseInt(current_date_list[0])) {

                        // check two dates: expired < current
                        if (Integer.parseInt(expired_date_list[1]) < Integer.parseInt(current_date_list[1])) {
                            // insert expired item into FINISH TABLE
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(FINISH_COL_1_USERNAME, username);
                            contentValues.put(FINISH_COL_2_FOODNAME, res.getString(1));
                            contentValues.put(FINISH_COL_3_QUALITY, res.getString(2));
                            contentValues.put(FINISH_COL_4_EXPIRATIONDATE, res.getString(4));
                            contentValues.put(FINISH_COL_5_STATUS, "EXPIRED");
                            contentValues.put(FINISH_COL_6_CATEGORY, res.getString(6));

                            long result = db.insert(FINISH_TABLE, null, contentValues);


                            if (result == -1)
                                return  false;
                            else {
                                // delete the expired item in the OWNS TABLE
                                db.delete(OWNS_TABLE,
                                        OWNS_COL_1_USERNAME + "=? AND " + OWNS_COL_2_FOODNAME + "=?",
                                        new String[] {username, res.getString(1)});
                                return true;
                            }

                        }
                    }

                }

            } //end while loop
        }

        res.close();

        if (db != null && db.isOpen())
            db.close();

        return true;
    }


    public Cursor getFinishByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + FINISH_TABLE + " where " +
                FINISH_COL_1_USERNAME + "='" + username +"'", null);
        return res;
    }

    //username and foodname is primary key
    public  Integer deleteFinishTableRow (String username, String foodname, String expireddate, String quality) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(FINISH_TABLE,
                FINISH_COL_1_USERNAME + "=? AND " + FINISH_COL_2_FOODNAME + "=? AND " +
                FINISH_COL_4_EXPIRATIONDATE + "=? AND " + FINISH_COL_3_QUALITY + "=?",
                new String[] {username, foodname, expireddate, quality});
    }








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
