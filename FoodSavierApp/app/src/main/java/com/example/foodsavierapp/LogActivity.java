package com.example.foodsavierapp;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Implementation for log activity
 */
public class LogActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener   {
    //Intent
    //CURRENT: username
    //TRANSFER TO
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    DatabaseHelper myDB;

    ArrayList<ExampleItemLog> exampleListLog;
    private RecyclerView logRecycleView;
    private ExampleAdapterLog logRecycleAdapter;
    private RecyclerView.LayoutManager logLayoutManager;

    Button btnCurrentLog, btnHistoryLog;
    ImageView logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        myDB = new DatabaseHelper(this);
        exampleListLog = new ArrayList<>();

        btnCurrentLog = (Button)findViewById(R.id.btnCurrentLog);
        btnHistoryLog = (Button)findViewById(R.id.btnHistoryLog);

        logoImage = (ImageView)findViewById(R.id.logoLogActivity);



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


        //------//////////////////
        // function call: initialize list
        setOwnsTableList();
        //setFinishTableList will be call when history log button is clicked



        //function call
        clickCurrentLogButton();
        clickHistoryLogButton();

        homeLogoListener();


    }

    /**
     * Navigation view: appear when click
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
        super.onBackPressed();
    }

    /**
     * Navigation View: action listener for item on select
     * @param item - MenuItem
     * @return true
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch ( id ) {
            case R.id.nav_profile:
                intent = new Intent(LogActivity.this, ProfileActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_add:
                intent = new Intent(LogActivity.this, AddItemsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_log:
                intent = new Intent(LogActivity.this, LogActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_help:
                intent = new Intent(LogActivity.this, HelpActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_aboutus:
                intent = new Intent(LogActivity.this, AboutUsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_logout:
                intent = new Intent(LogActivity.this, MainActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_dashboard:
                intent = new Intent(LogActivity.this, DashboardActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Action listener of LogoImage
     */
    public void homeLogoListener() {
        logoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogActivity.this, DashboardActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
            }
        });
    }

    /**
     *  select the dynamic listing for the log activity when click on the 'on stock' tab
     */
    public void setOwnsTableList () {
        exampleListLog = new ArrayList<>();

        Calendar myCalender = Calendar.getInstance();
        String currentDate = (myCalender.get(Calendar.MONTH)+1) + "/" + myCalender.get(Calendar.DAY_OF_MONTH) + "/" + myCalender.get(Calendar.YEAR);

        //Update the Finish table before call Owns table
        boolean updateSuccess = myDB.insertAndUpdateFinish(getIntent().getStringExtra("username"), currentDate);

        Cursor res = myDB.getOwnsByUsername(getIntent().getStringExtra("username"));
        int index_foodname = 1;
        int index_quantity = 2;
        int index_expired = 4;
        int index_status = 5;
        int index_category = 6;

        while (res.moveToNext()) {
            if (res.getString(index_category).equals("PANTRY")) {
                exampleListLog.add(new ExampleItemLog(R.drawable.pantry_icon, res.getString(index_foodname),
                        res.getString(index_status), res.getString(index_expired), res.getString(index_quantity)));
            }
            else if (res.getString(index_category).equals("FREEZER")) {
                exampleListLog.add(new ExampleItemLog(R.drawable.freezer_icon, res.getString(index_foodname),
                        res.getString(index_status), res.getString(index_expired), res.getString(index_quantity)));
            }
            else {
                exampleListLog.add(new ExampleItemLog(R.drawable.fridge_icon, res.getString(index_foodname),
                        res.getString(index_status), res.getString(index_expired), res.getString(index_quantity)));
            }
        }
        res.close();

        // build recycle view////
        logRecycleView = (RecyclerView)findViewById(R.id.recyclerViewLog);
        logRecycleView.setHasFixedSize(true);
        logLayoutManager = new LinearLayoutManager(this);
        logRecycleAdapter = new ExampleAdapterLog(exampleListLog);

        logRecycleView.setLayoutManager(logLayoutManager);
        logRecycleView.setAdapter(logRecycleAdapter);

        logRecycleAdapter.setOnItemClickListener(new ExampleAdapterLog.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                exampleListLog.add(position, new ExampleItemLog());
//                logRecycleAdapter.notifyItemInserted(position);
            }

            @Override
            public void onDeleteClick(int position) {
                //remove item
                ExampleItemLog itemInfo = exampleListLog.get(position);
                myDB.deleteOwnsByUsernameFoodname(getIntent().getStringExtra("username").toString(), itemInfo.getCardFoodName());

                exampleListLog.remove(position);
                logRecycleAdapter.notifyItemRemoved(position);

            }
        });

    }

    /**
     *  select the dynamic listing for the log activity when click on the 'history' tab
     */
    public void setFinishTableList () {
        exampleListLog = new ArrayList<>();

        //set up list
        Cursor res = myDB.getFinishByUsername(getIntent().getStringExtra("username"));
        int index_foodname = 1;
        int index_quantity = 2;
        int index_expired = 3;
        int index_status = 4;
        int index_category = 5;
        //System.out.println(res.getCount());

        while (res.moveToNext()) {
            if (res.getString(index_category).equals("PANTRY")) {
                exampleListLog.add(new ExampleItemLog(R.drawable.pantry_icon, res.getString(index_foodname),
                        res.getString(index_status), res.getString(index_expired), res.getString(index_quantity)));
            }
            else if (res.getString(index_category).equals("FREEZER")) {
                exampleListLog.add(new ExampleItemLog(R.drawable.freezer_icon, res.getString(index_foodname),
                        res.getString(index_status), res.getString(index_expired), res.getString(index_quantity)));
            }
            else {
                exampleListLog.add(new ExampleItemLog(R.drawable.fridge_icon, res.getString(index_foodname),
                        res.getString(index_status), res.getString(index_expired), res.getString(index_quantity)));
            }
        }
        res.close();

        // build recycle view////
        logRecycleView = (RecyclerView)findViewById(R.id.recyclerViewLog);
        logRecycleView.setHasFixedSize(true);
        logLayoutManager = new LinearLayoutManager(this);
        logRecycleAdapter = new ExampleAdapterLog(exampleListLog);

        logRecycleView.setLayoutManager(logLayoutManager);
        logRecycleView.setAdapter(logRecycleAdapter);

        logRecycleAdapter.setOnItemClickListener(new ExampleAdapterLog.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                exampleListLog.add(position, new ExampleItemLog());
//                logRecycleAdapter.notifyItemInserted(position);
            }

            @Override
            public void onDeleteClick(int position) {
                //remove item
                ExampleItemLog itemInfo = exampleListLog.get(position);

                myDB.deleteFinishTableRow(getIntent().getStringExtra("username").toString(), itemInfo.getCardFoodName(),
                        itemInfo.getCardExpiredDate(), itemInfo.getCardFoodQuantity());

                exampleListLog.remove(position);
                logRecycleAdapter.notifyItemRemoved(position);

            }
        });
    }

    /**
     * On click listener for 'on stock' tab
     */
    public void clickCurrentLogButton() {
        btnCurrentLog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // enable other button
                        btnHistoryLog.setEnabled(true);
                        // change text color and background accordingly
                        btnHistoryLog.setTextColor(getResources().getColor(R.color.colorPrimary));
                        btnHistoryLog.setBackgroundResource(R.drawable.style_button_border);

                        // disable the clicked button
                        btnCurrentLog.setEnabled(false);
                        // change text color and background accordingly
                        btnCurrentLog.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        btnCurrentLog.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                        // call function to modify CardView contents
                        setOwnsTableList();

                    }
                }
        );
    }

    /**
     * On click listener for 'history' tab
     */
    public void clickHistoryLogButton() {
        btnHistoryLog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // enable other button
                        btnCurrentLog.setEnabled(true);
                        // change text color and background accordingly
                        btnCurrentLog.setTextColor(getResources().getColor(R.color.colorPrimary));
                        btnCurrentLog.setBackgroundResource(R.drawable.style_button_border);

                        // disable the clicked button
                        btnHistoryLog.setEnabled(false);
                        // change text color and background accordingly
                        btnHistoryLog.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        btnHistoryLog.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                        // call function to modify CardView contents
                        setFinishTableList();

                    }
                }
        );
    }


}
