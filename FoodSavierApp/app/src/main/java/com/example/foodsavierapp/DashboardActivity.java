package com.example.foodsavierapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DashboardActivity extends AppCompatActivity {
    //Intent
    //CURRENT: username
    //TRANSFER TO ADDITEMSACTIVITY: username
    //TRANSFER TO PROFILEACTIVITY: username
    //TRANSFER TO LOGACTIVITY: username
    CardView addOption, profileOption, logOption, helpOption, aboutUsOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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

        // onclicklistener for log option
        helpOption = (CardView) findViewById(R.id.helpViewDashboard);
        helpOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, helpActivity.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });

        // onclicklistener for log option
        aboutUsOption = (CardView) findViewById(R.id.aboutUsDashboard);
        aboutUsOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, aboutUsActivity.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });






    }
}
