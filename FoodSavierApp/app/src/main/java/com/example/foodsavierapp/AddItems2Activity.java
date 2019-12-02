package com.example.foodsavierapp;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddItems2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener   {
    DatabaseHelper myDb;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    // CURRENT: username, foodname, estimatedays, foodCategory
    // AFTER TRANSFER TO LOGACTIVITY: username
    Intent intent;

    Spinner spinnerCategory;
    TextView textViewQuantity;
    TextView textViewFoodName;
    ImageButton imageButtonCancel;
    ImageButton imageButtonAdd;
    TimePicker timePicker;

    TextView textViewDisplayNotifyDate;
    DatePickerDialog.OnDateSetListener dateSetNotifyDateListener;

    TextView textViewDisplayExpiredDate;
    DatePickerDialog.OnDateSetListener dateSetExpiredDateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items2);

        myDb = new DatabaseHelper(this);

        // define elements
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategoryAddItems2);

        textViewDisplayExpiredDate = (TextView)findViewById(R.id.textViewExpiredDateAddItems2);
        textViewDisplayNotifyDate = (TextView)findViewById(R.id.textViewNotifySelectDateAddItems2);
        textViewFoodName = (TextView)findViewById(R.id.textViewFoodNameAddItems2);
        textViewQuantity = (TextView)findViewById(R.id.editTextQuantityAddItems2);

        imageButtonCancel = (ImageButton)findViewById(R.id.imageButtonCancelAddItem2);
        imageButtonAdd = (ImageButton)findViewById(R.id.imageButtonAddAddItems2);

        timePicker = (TimePicker)findViewById(R.id.simpleTimePickerAddItems2);


        //**********************************************
        //Setting up navigation menu
        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //End navigation menu
        //***********************************************


//********* initialize food name
        textViewFoodName.setText(getIntent().getStringExtra("foodname"));

//********* initialize expired date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // (jan = 0) + 1
        int day = calendar.get(Calendar.DAY_OF_MONTH) + Integer.parseInt(getIntent().getStringExtra("estimatedays"));

        while (day > 31 || month > 12) {
            if (day > 31) {
                day = day - 30;
                month = month + 1;
            }
            else if (month > 12) {
                month = month -12;
                year = year + 1;
            }
        }
        textViewDisplayExpiredDate.setText(month + "/" + day + "/" + year);

//********* initialize notification date
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DAY_OF_MONTH);
        textViewDisplayNotifyDate.setText(m + "/" + d + "/" + y);



        // ACTION LISTENER
        cancelButtonListener();
        addButtonListener();

        spinnerCategoryActionListener();

        addExpiratedDateListener();
        addNotificationDateListener();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch ( id ) {
            case R.id.nav_profile:
                intent = new Intent(AddItems2Activity.this, ProfileActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_add:
                intent = new Intent(AddItems2Activity.this, AddItemsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_log:
                intent = new Intent(AddItems2Activity.this, LogActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_help:
                intent = new Intent(AddItems2Activity.this, HelpActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_aboutus:
                intent = new Intent(AddItems2Activity.this, AboutUsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_logout:
                intent = new Intent(AddItems2Activity.this, MainActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_dashboard:
                intent = new Intent(AddItems2Activity.this, DashboardActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Set Notification date for food item
     */
    public void setNotification() {
        int notify_date = Integer.parseInt(textViewDisplayNotifyDate.getText().toString().split("/")[1]);
        int notify_month = Integer.parseInt(textViewDisplayNotifyDate.getText().toString().split("/")[0]) - 1;
        int notify_year = Integer.parseInt(textViewDisplayNotifyDate.getText().toString().split("/")[2]);
        int notify_hour = Integer.parseInt(timePicker.getCurrentHour().toString());
        int notify_minute = Integer.parseInt(timePicker.getCurrentMinute().toString());

//        System.out.println(notify_hour + ":" + notify_minute);
//        System.out.println(notify_month+"/"+notify_date+"/"+notify_year);

        //**setting up notification
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.MINUTE, notify_minute);
        cal.set(Calendar.HOUR_OF_DAY, notify_hour);
        cal.set(Calendar.DAY_OF_MONTH, notify_date);
        cal.set(Calendar.MONTH, notify_month);
        cal.set(Calendar.YEAR, notify_year);
        cal.set(Calendar.SECOND, 0);

        //System.out.println(cal.get(Calendar) );

        //set notification
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
    }

    /**
     * On click listener for add food button
     */
    public void addButtonListener() {
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //**Check database
                Calendar myCalender = Calendar.getInstance();
                String foodName = getIntent().getStringExtra("foodname").toUpperCase(); // textViewFoodName.getText().toString();
                int foodQuantity = Integer.parseInt(textViewQuantity.getText().toString());
                String expirationDate = textViewDisplayExpiredDate.getText().toString();
                String currentDate = (myCalender.get(Calendar.MONTH)+1) + "/" + myCalender.get(Calendar.DAY_OF_MONTH) + "/" + myCalender.get(Calendar.YEAR);
                String username = getIntent().getStringExtra("username");
                String foodCategory = spinnerCategory.getSelectedItem().toString();
                int estimationDate = 0;

                /////////////////////////
                //HH converts hour in 24 hours format (0-23), day calculation
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                Date d1 = null;
                Date d2 = null;

                try {
                    d1 = format.parse(currentDate);
                    d2 = format.parse(expirationDate);

                    //in milliseconds
                    long diff = d2.getTime() - d1.getTime();
                    estimationDate = (int) Math.abs(diff / (24 * 60 * 60 * 1000));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //////////////////////////////

                //if food already in the database
                if (myDb.searchExistFood(foodName)) {
                    Cursor cursorOwnsExist = myDb.isExistRowOwns(username, foodName);

                    // prevent unique exception (database) when insert with data same as primary key
                    if (cursorOwnsExist.getCount() ==  0) {

                        boolean isInserted = myDb.insertOwns(username, foodName, foodQuantity, currentDate, expirationDate, "EXPIRE ON", foodCategory);

                        //if insert to owns table successfully
                        if (isInserted) {
                            //if auto-update estimate date successfully
                            if (myDb.improveEstDate(foodName, estimationDate)) {

                                //set notification no matter whether auto-update estimate date success or not
                                setNotification();

                                Toast.makeText(AddItems2Activity.this, "Add " + foodName + " successfully", Toast.LENGTH_LONG).show();
                                intent = new Intent(AddItems2Activity.this, LogActivity.class);
                                intent.putExtra("username", getIntent().getStringExtra("username"));
                                startActivity(intent);
                            } else
                                Toast.makeText(AddItems2Activity.this, "Fail: to auto-update data", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(AddItems2Activity.this, "Fail: Already has " + foodName, Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                        Toast.makeText(AddItems2Activity.this, "Fail: " + foodName + " already exist", Toast.LENGTH_LONG).show();
                }
                else { //if food is not in the database yet
                    boolean isInsertedFood = myDb.insertFood(foodName, foodCategory, estimationDate);

                    //if insert food successfully
                    if (isInsertedFood) {
                        boolean isInsertedOwns = myDb.insertOwns(username, foodName, foodQuantity, currentDate, expirationDate, "EXPIRE ON", foodCategory);

                        //if insert data into Owns Table successfully
                        if (isInsertedOwns) {
                            //set notification
                            setNotification();

                            Toast.makeText(AddItems2Activity.this, "Added " + foodName + " successfully", Toast.LENGTH_LONG).show();
                            intent = new Intent(AddItems2Activity.this, LogActivity.class);
                            intent.putExtra("username", getIntent().getStringExtra("username"));
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(AddItems2Activity.this, "Fail: add data to Owns Table", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(AddItems2Activity.this, "Fail: add food to Food Table", Toast.LENGTH_LONG).show();

                }

            }
        });
    }


    public void cancelButtonListener() {
        imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AddItems2Activity.this, AddItemsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
            }
        });
    }

    public void addExpiratedDateListener() {
        textViewDisplayExpiredDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddItems2Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetExpiredDateListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        dateSetExpiredDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Jan is 0 so increment it
                month = month + 1;
                //Log.d("AddItems2Activity", "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);
                String date = month + "/" + dayOfMonth + "/" + year;
                textViewDisplayExpiredDate.setText(date);
            }
        };

    }


    public void addNotificationDateListener() {
        textViewDisplayNotifyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddItems2Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetNotifyDateListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        dateSetNotifyDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Jan is 0 so increment it
                month = month + 1;
                //Log.d("AddItems2Activity", "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);
                String date = month + "/" + dayOfMonth + "/" + year;
                textViewDisplayNotifyDate.setText(date);
            }
        };

    }




    public void spinnerCategoryActionListener() {
        // set options list
        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("FREEZER");
        spinnerList.add("PANTRY");
        spinnerList.add("FRIDGE");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);


        // set the default items
        if (getIntent().getStringExtra("foodCategory").equals("FREEZER"))
            spinnerCategory.setSelection(0);
        else if (getIntent().getStringExtra("foodCategory").equals("PANTRY"))
            spinnerCategory.setSelection(1);
        else
            spinnerCategory.setSelection(2);


        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCategory.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
