package com.example.foodsavierapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class AddItemsActivity extends AppCompatActivity {
   // ListView listViewAddFreezer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        /*
        listViewAddFreezer = (ListView)findViewById(R.id.addFreezerListView);

        final ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("this");
        arrayList.add("is");
        arrayList.add("what");
        arrayList.add("I");
        arrayList.add("plan");
        arrayList.add("to");
        arrayList.add("do");
        arrayList.add("from");
        arrayList.add("the");
        arrayList.add("start");
        arrayList.add("is");
        arrayList.add("what");
        arrayList.add("I");
        arrayList.add("plan");
        arrayList.add("to");
        arrayList.add("do");
        arrayList.add("from");
        arrayList.add("the");
        arrayList.add("start");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listViewAddFreezer.setAdapter(arrayAdapter);

        listViewAddFreezer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddItemsActivity.this, "click item: " +position+ "  "+arrayList.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

         */


    }
}
