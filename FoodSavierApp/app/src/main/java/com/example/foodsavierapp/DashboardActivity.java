package com.example.foodsavierapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DashboardActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    ImageButton btnAdd, btnProfile, btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnAdd = (ImageButton) findViewById(R.id.btnAddDashboard);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, FoodAdditionActivity.class);
                startActivity(i);
            }
        });

        btnProfile = (ImageButton) findViewById(R.id.btnProfileDashboard);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        btnLog = (ImageButton) findViewById(R.id.btnLogDashboard);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, LogActivity.class);
                startActivity(i);
            }
        });



    }
}
