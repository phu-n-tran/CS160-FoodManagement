package com.example.foodsavierapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
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
                Intent intent = new Intent(ProfileActivity.this, aboutUsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
            }
        });


        helpOption = (CardView)findViewById(R.id.helpOptionProfile);
        helpOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, helpActivity.class);
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
}
