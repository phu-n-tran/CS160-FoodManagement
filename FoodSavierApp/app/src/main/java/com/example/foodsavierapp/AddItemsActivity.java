package com.example.foodsavierapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddItemsActivity extends AppCompatActivity {
    final int FOOD_NAME_INDEX = 0;
    final int FOOD_CATEGORY_INDEX = 1;
    final int FOOD_EXPIRE_INDEX = 2;

    DatabaseHelper myDb;
    // CURRENT: username
    // AFTER TRANSFER: username, foodName, estimatedays, foodCategory
    Intent intent;

    Button btnFreezer, btnPantry, btnFridge;
    ImageButton btnSearchItem;
    EditText editTextSearchResult;
    CardView cardView1, cardView2, cardView3, cardView4, cardView5;
    TextView c1Title, c2Title, c3Title, c4Title, c5Title;
    TextView c1Expired, c2Expired, c3Expired, c4Expired, c5Expired;
    ImageView c1Image, c2Image, c3Image, c4Image, c5Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        // initialize the database
        myDb = new DatabaseHelper(this);
        intent = new Intent(AddItemsActivity.this, AddItems2Activity.class);
        intent.putExtra("username", getIntent().getStringExtra("username"));
        intent.putExtra("foodCategory", "Freezer");

        // initialize Button
        btnFreezer = (Button)findViewById(R.id.btnFreezerAddItems);
        btnPantry = (Button)findViewById(R.id.btnPantryAddItems);
        btnFridge = (Button)findViewById(R.id.btnFridgeAddItems);
        // ImageButton
        btnSearchItem = (ImageButton)findViewById(R.id.imageButtonSearchAddItems);
        // Edittext search result
        editTextSearchResult = (EditText)findViewById(R.id.editTextSearchResultAddItems);
        // CardView
        cardView1 = (CardView)findViewById(R.id.c1);
        cardView2 = (CardView)findViewById(R.id.c2);
        cardView3 = (CardView)findViewById(R.id.c3);
        cardView4 = (CardView)findViewById(R.id.c4);
        cardView5 = (CardView)findViewById(R.id.c5);
        // text view for title
        c1Title = (TextView)findViewById(R.id.c1FoodTitle);
        c2Title = (TextView)findViewById(R.id.c2FoodTitle);
        c3Title = (TextView)findViewById(R.id.c3FoodTitle);
        c4Title = (TextView)findViewById(R.id.c4FoodTitle);
        c5Title = (TextView)findViewById(R.id.c5FoodTitle);
        // textview for expires days
        c1Expired = (TextView)findViewById(R.id.c1ExpiredDays);
        c2Expired = (TextView)findViewById(R.id.c2ExpiredDays);
        c3Expired = (TextView)findViewById(R.id.c3ExpiredDays);
        c4Expired = (TextView)findViewById(R.id.c4ExpiredDays);
        c5Expired = (TextView)findViewById(R.id.c5ExpiredDays);
        // image view of cardview
        c1Image = (ImageView)findViewById(R.id.c1Image);
        c2Image = (ImageView)findViewById(R.id.c2Image);
        c3Image = (ImageView)findViewById(R.id.c3Image);
        c4Image = (ImageView)findViewById(R.id.c4Image);
        c5Image = (ImageView)findViewById(R.id.c5Image);


        //*****************
        // ACTION LISTENERS
        clickPantryButton();
        clickFreezerButton();
        clickFridgeButton();

        cardView1.setOnClickListener(cardViewListeners);
        cardView2.setOnClickListener(cardViewListeners);
        cardView3.setOnClickListener(cardViewListeners);
        cardView4.setOnClickListener(cardViewListeners);
        cardView5.setOnClickListener(cardViewListeners);

        clickSearchButton();

    }

    public void clickSearchButton() {
        btnSearchItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String searchResult = editTextSearchResult.getText().toString();
                        // iff input is empty
                        if (searchResult.isEmpty()) {
                            Toast.makeText(AddItemsActivity.this, "Please enter item name", Toast.LENGTH_LONG).show();
                        }
                        else {
                            intent.putExtra("foodname", searchResult);
                            startActivity(intent);
                        }
                    }
                }
        );
    }


    // listener for all the cardViews
    View.OnClickListener cardViewListeners = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.c1:
                    intent.putExtra("foodname", c1Title.getText().toString());
                    intent.putExtra("estimatedays", c1Expired.getText().toString().split(" ")[3]);
                    startActivity(intent);
                    break;
                case R.id.c2:
                    intent.putExtra("foodname", c2Title.getText().toString());
                    intent.putExtra("estimatedays", c2Expired.getText().toString().split(" ")[3]);
                    startActivity(intent);
                    break;
                case R.id.c3:
                    intent.putExtra("foodname", c3Title.getText().toString());
                    intent.putExtra("estimatedays", c3Expired.getText().toString().split(" ")[3]);
                    startActivity(intent);
                    break;
                case R.id.c4:
                    intent.putExtra("foodname", c4Title.getText().toString());
                    intent.putExtra("estimatedays", c4Expired.getText().toString().split(" ")[3]);
                    startActivity(intent);
                    break;
                case R.id.c5:
                    intent.putExtra("foodname", c5Title.getText().toString());
                    intent.putExtra("estimatedays", c5Expired.getText().toString().split(" ")[3]);
                    startActivity(intent);
                    break;
            }

        }
    };


    public void clickPantryButton() {
       btnPantry.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       // change intent 'foodCategory' value
                       intent.putExtra("foodCategory", "Pantry");

                       // enable other button
                       btnFreezer.setEnabled(true);
                       btnFridge.setEnabled(true);
                       // change text color and background accordingly
                       btnFreezer.setTextColor(getResources().getColor(R.color.colorPrimary));
                       btnFridge.setTextColor(getResources().getColor(R.color.colorPrimary));
                       btnFreezer.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_button_border));
                       btnFridge.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_button_border));

                       // disable the clicked button
                       btnPantry.setEnabled(false);
                       // change text color and background accordingly
                       btnPantry.setTextColor(getResources().getColor(R.color.cardview_light_background));
                       btnPantry.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                       // call function to modify CardView contents
                       changeCardViewInfo(myDb.getAllFoodsPantry(), R.drawable.pantry_icon);



                   }
               }
       );
    }

    public void clickFreezerButton() {
        btnFreezer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // change intent 'foodCategory' value
                        intent.putExtra("foodCategory", "Freezer");

                        // enable other button
                        btnPantry.setEnabled(true);
                        btnFridge.setEnabled(true);
                        // change text color and background accordingly
                        btnPantry.setTextColor(getResources().getColor(R.color.colorPrimary));
                        btnFridge.setTextColor(getResources().getColor(R.color.colorPrimary));
                        btnPantry.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_button_border));
                        btnFridge.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_button_border));

                        // disable the clicked button
                        btnFreezer.setEnabled(false);
                        // change text color and background accordingly
                        btnFreezer.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        btnFreezer.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                        // call function to modify Cardview contents
                        changeCardViewInfo(myDb.getAllFoodsFreezer(), R.drawable.freezer_icon);

                    }
                }
        );
    }

    public void clickFridgeButton() {
        btnFridge.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // change intent 'foodCategory' value
                        intent.putExtra("foodCategory", "Fridge");

                        // enable other button
                        btnPantry.setEnabled(true);
                        btnFreezer.setEnabled(true);
                        // change text color and background accordingly
                        btnPantry.setTextColor(getResources().getColor(R.color.colorPrimary));
                        btnFreezer.setTextColor(getResources().getColor(R.color.colorPrimary));
                        btnPantry.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_button_border));
                        btnFreezer.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_button_border));

                        // disable the clicked button
                        btnFridge.setEnabled(false);
                        // change text color and background accordingly
                        btnFridge.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        btnFridge.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                        //btnFridge.setBackgroundColor(Color.parseColor("#FFFFFF")); //white
                        // call function to modify Cardview contents
                        changeCardViewInfo(myDb.getAllFoodsFridge(), R.drawable.fridge_icon);

                    }
                }
        );
    }

    public void changeCardViewInfo(Cursor res, int r) {
        try {
            if (res.getCount() == 0) {
                Toast.makeText(AddItemsActivity.this, "No Food exist in the database", Toast.LENGTH_LONG).show();
            }
            else {
                // 1st element
                res.moveToNext();
                c1Title.setText(res.getString(FOOD_NAME_INDEX));
                c1Expired.setText("Expired in + " + res.getString(FOOD_EXPIRE_INDEX) + " days");
                c1Image.setImageResource(r);

                // 2nd element
                res.moveToNext();
                c2Title.setText(res.getString(FOOD_NAME_INDEX));
                c2Expired.setText("Expired in + " + res.getString(FOOD_EXPIRE_INDEX) + " days");
                c2Image.setImageResource(r);

                // 3rd element
                res.moveToNext();
                c3Title.setText(res.getString(FOOD_NAME_INDEX));
                c3Expired.setText("Expired in + " + res.getString(FOOD_EXPIRE_INDEX) + " days");
                c3Image.setImageResource(r);

                // 4th element
                res.moveToNext();
                c4Title.setText(res.getString(FOOD_NAME_INDEX));
                c4Expired.setText("Expired in + " + res.getString(FOOD_EXPIRE_INDEX) + " days");
                c4Image.setImageResource(r);

                // 5th element
                res.moveToNext();
                c5Title.setText(res.getString(FOOD_NAME_INDEX));
                c5Expired.setText("Expired in + " + res.getString(FOOD_EXPIRE_INDEX) + " days");
                c5Image.setImageResource(r);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (res != null) {
                res.close();
            }
        }
    }





}

