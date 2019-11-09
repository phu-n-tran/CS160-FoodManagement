package com.example.foodsavierapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile2Activity extends AppCompatActivity {
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

    public void changePasswordListener() {
        updateFirstNamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username").toString();
                String oldPassword = inputOldPassword.getText().toString();
                String newPassword = inputNewPassword.getText().toString();
                String confirmNewPassword = inputConfirmPassword.getText().toString();
                String currentPassword = null;

                Cursor res = myDb.getSingleAccount(username);

                if (res.getCount() == 1) {
                    res.moveToFirst();
                    currentPassword = res.getString(3).toUpperCase();
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





}
