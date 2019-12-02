package com.example.foodsavierapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    //Intent
    //CURRENT: username
    //TRANSFER TO LOGACTIVITY: username
    //TRANSFER TO ADDITEMSACTIVITY: username
    //TRANSFER TO ABOUTUSACTIVITY: username
    //TRANSFER TO HELPACTIVITY: username
    //TRANSFER TO PROFILE2ACTIVITY: username
    //TRANSFER TO MAINACTIVITY: ___

    DatabaseHelper myDb;

    TextView textViewUsername;
    CardView viewLogOption, addItemOption, aboutUsOption, helpOption, editInfoOption, logoutOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myDb = new DatabaseHelper(this);


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



        textViewUsername = (TextView)findViewById(R.id.usernameProfile);

        // set username
        Cursor res = myDb.getSingleAccount(getIntent().getStringExtra("username").toString());
        if (res.getCount() != 0) {
            res.moveToFirst();
            textViewUsername.setText(res.getString(0).toUpperCase() + ", " + res.getString(1).toUpperCase());
        }
        else
            textViewUsername.setText("UNKNOWN USERNAME");

        viewLogOption = (CardView)findViewById(R.id.viewLogProfile);
        viewLogOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LogActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
            }
        });


        addItemOption = (CardView)findViewById(R.id.addOptionProfile);
        addItemOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AddItemsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
            }
        });


        aboutUsOption = (CardView)findViewById(R.id.aboutUsProfile);
        aboutUsOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AboutUsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
            }
        });


        helpOption = (CardView)findViewById(R.id.helpOptionProfile);
        helpOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HelpActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
            }
        });


        editInfoOption = (CardView)findViewById(R.id.editOptionProfile);
        editInfoOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, Profile2Activity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
            }
        });


        logoutOption = (CardView)findViewById(R.id.logoutOptionProfile);
        logoutOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                Toast.makeText(ProfileActivity.this, "Logging Out Successfully", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

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
                intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_add:
                intent = new Intent(ProfileActivity.this, AddItemsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_log:
                intent = new Intent(ProfileActivity.this, LogActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_help:
                intent = new Intent(ProfileActivity.this, HelpActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_aboutus:
                intent = new Intent(ProfileActivity.this, AboutUsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_logout:
                intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_dashboard:
                intent = new Intent(ProfileActivity.this, DashboardActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
