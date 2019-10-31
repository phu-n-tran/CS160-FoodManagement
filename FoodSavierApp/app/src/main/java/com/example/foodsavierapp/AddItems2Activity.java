package com.example.foodsavierapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddItems2Activity extends AppCompatActivity {
    DatabaseHelper myDb;
    // CURRENT: username, foodname, estimatedays, foodCategory
    // AFTER TRANSFER:
    Intent intent;


    Spinner spinner;

    TextView textViewDisplayDate;
    TextView textViewFoodName;
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items2);


        // define elements
        spinner = (Spinner)findViewById(R.id.spinnerCategoryAddItems2);


        textViewDisplayDate = (TextView)findViewById(R.id.textViewExpiredDateAddItems2);
        textViewFoodName = (TextView)findViewById(R.id.textViewFoodNameAddItems2);



//********* initialize
        textViewFoodName.setText(getIntent().getStringExtra("foodname"));


        // setting up element
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
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
        textViewDisplayDate.setText(month + "/" + day + "/" + year);



        // ACTION LISTENER
        spinnerCategoryActionListener();

        addDateListener();






        System.out.println(getIntent().getStringExtra("username"));
        System.out.println(getIntent().getStringExtra("foodname"));
        System.out.println(getIntent().getStringExtra("estimatedays"));
        System.out.println(getIntent().getStringExtra("foodCategory"));


    }


    public void addDateListener() {
        textViewDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddItems2Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Jan is 0 so increment it
                month = month + 1;
                //Log.d("AddItems2Activity", "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);
                String date = month + "/" + dayOfMonth + "/" + year;
                textViewDisplayDate.setText(date);
            }
        };

    }

    public void spinnerCategoryActionListener() {
        // set options list
        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("Freezer");
        spinnerList.add("Pantry");
        spinnerList.add("Fridge");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        // set the default items
        if (getIntent().getStringExtra("foodCategory").equals("Freezer"))
            spinner.setSelection(0);
        else if (getIntent().getStringExtra("foodCategory").equals("Pantry"))
            spinner.setSelection(1);
        else
            spinner.setSelection(2);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
