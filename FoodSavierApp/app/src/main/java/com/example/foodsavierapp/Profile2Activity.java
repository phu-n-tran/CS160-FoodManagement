package com.example.foodsavierapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Profile2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    //Intent
    //CURRENT: username
    //TRANSFER TO

    DatabaseHelper myDb;

    TextView textViewUsername;

    EditText inputLastName, inputFirstName;
    EditText inputOldPassword, inputNewPassword, inputConfirmPassword;

    Button updateLastNamebtn, updateFirstNamebtn, updatePasswordbtn;
    Button finishBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

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


        myDb = new DatabaseHelper(this);

        textViewUsername = (TextView)findViewById(R.id.usernameProfile2);

        //set username
        Cursor res = myDb.getSingleAccount(getIntent().getStringExtra("username").toString());
        if (res.getCount() != 0) {
            res.moveToFirst();
            textViewUsername.setText(res.getString(0).toUpperCase() + ", " + res.getString(1).toUpperCase());
        }
        else
            textViewUsername.setText("UNKNOWN USERNAME");


        inputLastName = (EditText)findViewById(R.id.changeLastNameProfile2);
        inputFirstName = (EditText)findViewById(R.id.changeFirstNameProfile2);
        inputOldPassword = (EditText)findViewById(R.id.changeOldPasswordProfile2);
        inputNewPassword = (EditText)findViewById(R.id.changeNewPasswordProfile2);
        inputConfirmPassword = (EditText)findViewById(R.id.changeConfirmPasswordProfile2);

        updateLastNamebtn = (Button)findViewById(R.id.btnUpdateLNameProfile2);
        updateFirstNamebtn = (Button)findViewById(R.id.btnUpdateFNameProfile2);
        updatePasswordbtn = (Button)findViewById(R.id.btnUpdatePasswordProfile2);

        finishBtn = (Button)findViewById(R.id.btnFinishProfile2);


        // actionListener
        inputLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {       }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String lastname = inputLastName.getText().toString();

                updateLastNamebtn.setEnabled(!lastname.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {       }
        });

        inputFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {       }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String firstname = inputFirstName.getText().toString();

                updateFirstNamebtn.setEnabled(!firstname.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {           }
        });

        inputNewPassword.addTextChangedListener(passwordChangeTextWatcher);
        inputOldPassword.addTextChangedListener(passwordChangeTextWatcher);
        inputConfirmPassword.addTextChangedListener(passwordChangeTextWatcher);

        changeLastNameListener();
        changeFirstNameListener();
        changePasswordListener();

        finishBtnListener();

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
                intent = new Intent(Profile2Activity.this, ProfileActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_add:
                intent = new Intent(Profile2Activity.this, AddItemsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_log:
                intent = new Intent(Profile2Activity.this, LogActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_help:
                intent = new Intent(Profile2Activity.this, HelpActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_aboutus:
                intent = new Intent(Profile2Activity.this, AboutUsActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_logout:
                intent = new Intent(Profile2Activity.this, MainActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
            case R.id.nav_dashboard:
                intent = new Intent(Profile2Activity.this, DashboardActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Text listener: only activate the button if all fields is entered
     */
    public TextWatcher passwordChangeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {       }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String oldPassword = inputOldPassword.getText().toString();
            String newPassword =  inputNewPassword.getText().toString();
            String confirmNewPassord =  inputConfirmPassword.getText().toString();

            updatePasswordbtn.setEnabled(!oldPassword.isEmpty() && !newPassword.isEmpty() && !confirmNewPassord.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {      }
    };

    /**
     * On click listener for update last name button
     */
    public void changeLastNameListener() {
        updateLastNamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username").toString();
                String lastName = inputLastName.getText().toString();

                boolean isUpdated = myDb.updateLastnameAccount(username, lastName);


                if (isUpdated) {
                    Toast.makeText(Profile2Activity.this, "Update Last Name Successfully", Toast.LENGTH_LONG).show();

                    //set username new name on screen
                    Cursor res = myDb.getSingleAccount(username);
                    if (res.getCount() != 0) {
                        res.moveToFirst();
                        textViewUsername.setText(res.getString(0).toUpperCase() + ", " + res.getString(1).toUpperCase());
                    } else
                        textViewUsername.setText("UNKNOWN USERNAME");
                }
                else
                    Toast.makeText(Profile2Activity.this, "Update Last Name Fail", Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * On click listener for update first name button
     */
    public void changeFirstNameListener() {
        updateFirstNamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username").toString();
                String firstName = inputFirstName.getText().toString();

                boolean isUpdated = myDb.updateFirstnameAccount(username, firstName);

                if (isUpdated) {
                    Toast.makeText(Profile2Activity.this, "Update First Name Successfully", Toast.LENGTH_LONG).show();

                    //set username new name on screen
                    Cursor res = myDb.getSingleAccount(username);
                    if (res.getCount() != 0) {
                        res.moveToFirst();
                        textViewUsername.setText(res.getString(0).toUpperCase() + ", " + res.getString(1).toUpperCase());
                    } else
                        textViewUsername.setText("UNKNOWN USERNAME");
                }
                else
                    Toast.makeText(Profile2Activity.this, "Update First Name Fail", Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * On click listener for update password button
     */
    public void changePasswordListener() {
        updatePasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username").toString();
                String oldPassword = md5(inputOldPassword.getText().toString());
                String newPassword = md5(inputNewPassword.getText().toString());
                String confirmNewPassword = md5(inputConfirmPassword.getText().toString());
                String currentPassword = null;

                Cursor res = myDb.getSingleAccount(username);

                if (res.getCount() == 1) {
                    res.moveToFirst();
                    currentPassword = res.getString(3);
                }
                else
                    Toast.makeText(Profile2Activity.this, "Unable to retrieve data", Toast.LENGTH_LONG).show();


                if (currentPassword != null && newPassword.equals(confirmNewPassword) && currentPassword.equals(oldPassword)) {
                    boolean isUpdated = myDb.updatePasswordAccount(username, newPassword);

                    if (isUpdated)
                        Toast.makeText(Profile2Activity.this, "Update Password successfully", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Profile2Activity.this, "Update Password fail", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(Profile2Activity.this, "Either New/Old Password do not match", Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * On click listener for update finish button
     */
    public void finishBtnListener() {
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile2Activity.this, ProfileActivity.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });
    }

    /**
     * MD5 method
     * @param s - password that want to be hash
     * @return encrypted password
     */
    public static final String md5(String s) {
        final String MD5 = "MD5";
        s = "salt"+s+"md5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
