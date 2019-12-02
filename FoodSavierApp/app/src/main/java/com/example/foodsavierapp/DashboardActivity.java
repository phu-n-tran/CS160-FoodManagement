package com.example.foodsavierapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Intent
    //CURRENT: username
    //TRANSFER TO ADDITEMSACTIVITY: username
    //TRANSFER TO PROFILEACTIVITY: username
    //TRANSFER TO LOGACTIVITY: username
    CardView addOption, profileOption, logOption, helpOption, aboutUsOption;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


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




        // onclicklistener for add option
        addOption = (CardView) findViewById(R.id.addItemDashboard);
        addOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, AddItemsActivity.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });

        // onclicklistener for profile option
        profileOption = (CardView) findViewById(R.id.profileViewDashboard);
        profileOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, ProfileActivity.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });

        // onclicklistener for log option
        logOption = (CardView) findViewById(R.id.logItemDashboard);
        logOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, LogActivity.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });

        // onclicklistener for help option
        helpOption = (CardView) findViewById(R.id.helpViewDashboard);
        helpOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, HelpActivity.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });

        // onclicklistener for about us option
        aboutUsOption = (CardView) findViewById(R.id.aboutUsDashboard);
        aboutUsOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, AboutUsActivity.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });



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
                intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_add:
                intent = new Intent(DashboardActivity.this, AddItemsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_log:
                intent = new Intent(DashboardActivity.this, LogActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_help:
                intent = new Intent(DashboardActivity.this, HelpActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_aboutus:
                intent = new Intent(DashboardActivity.this, AboutUsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_logout:
                intent = new Intent(DashboardActivity.this, MainActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_dashboard:
                intent = new Intent(DashboardActivity.this, DashboardActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
