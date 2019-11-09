package com.example.foodsavierapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;

public class LogActivity extends AppCompatActivity {
    //Intent
    //CURRENT: username
    //TRANSFER TO

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



        //------//////////////////
        // function call: initialize list
        setOwnsTableList();
        //setFinishTableList will be call when history log button is clicked



        //function call
        clickCurrentLogButton();
        clickHistoryLogButton();

        homeLogoListener();


    }

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



    public void setOwnsTableList () {
        exampleListLog = new ArrayList<>();

        Calendar myCalender = Calendar.getInstance();
        String currentDate = (myCalender.get(Calendar.MONTH)+1) + "/" + myCalender.get(Calendar.DAY_OF_MONTH) + "/" + myCalender.get(Calendar.YEAR);

        //Update the Finish table before call Owns table
        boolean updateSuccess = myDB.insertAndUpdateFinish(getIntent().getStringExtra("username"), currentDate);

        //checking purpose
//        if(updateSuccess)
//            System.out.println("update FINISH TABLE successfully!! (at Log)");
//        else
//            System.out.println("update FINISH TABLE fail!! (at Log)");

        Cursor res = myDB.getOwnsByUsername(getIntent().getStringExtra("username"));
        int index_foodname = 1;
        int index_quantity = 2;
        int index_expired = 4;
        int index_status = 5;
        int index_category = 6;
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
                myDB.deleteOwnsByUsernameFoodname(getIntent().getStringExtra("username").toString(), itemInfo.getCardFoodName());

                exampleListLog.remove(position);
                logRecycleAdapter.notifyItemRemoved(position);

            }
        });

    }


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
