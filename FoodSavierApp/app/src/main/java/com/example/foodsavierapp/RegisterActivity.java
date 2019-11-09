package com.example.foodsavierapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    //Intent (none)

    DatabaseHelper myDb;
    EditText editFname, editLname, editUsername, editPassword;
    Button btnRegPage;
    TextView accountExist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initialize the database
        myDb = new DatabaseHelper(this);

        accountExist = (TextView)findViewById(R.id.textViewExistAccountRegister);

        // initialize the edit texts
        editFname = (EditText)findViewById(R.id.editTextRegFirstName);
        editLname = (EditText)findViewById(R.id.editTextRegLastName);
        editUsername = (EditText)findViewById(R.id.editTextRegUsername);
        editPassword = (EditText)findViewById(R.id.editTextRegPassword);

        // add listeners to editTexts
        editFname.addTextChangedListener(registerTextWatcher);
        editLname.addTextChangedListener(registerTextWatcher);
        editUsername.addTextChangedListener(registerTextWatcher);
        editPassword.addTextChangedListener(registerTextWatcher);

        // initialize the button
        btnRegPage = (Button)findViewById(R.id.btnRegRegister);
        // call method: btnRegPage button listener
        createAccount();

        noAccountListener();

    }

    public void noAccountListener() {
        accountExist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public TextWatcher registerTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String lname = editLname.getText().toString();
            String fname = editFname.getText().toString();
            String username = editUsername.getText().toString();
            String password =  editPassword.getText().toString();

            btnRegPage.setEnabled(!lname.isEmpty() && !fname.isEmpty() &&
                    !username.isEmpty() && !password.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void createAccount() {
        btnRegPage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = null;
                        try {
                            res = myDb.getAllAccounts();

                            // if no data exist
                            if(res.getCount() == 0) {
                                boolean isInserted = myDb.insertAccount(editLname.getText().toString(),
                                        editFname.getText().toString(), editUsername.getText().toString(),
                                        editPassword.getText().toString());

                                if(isInserted) {
                                    Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(i);
                                }
                                else
                                    Toast.makeText(RegisterActivity.this, "Fail: Create Account Fail", Toast.LENGTH_LONG).show();
                            }
                            else {
                                if(myDb.searchExistAccount(editUsername.getText().toString()))
                                    Toast.makeText(RegisterActivity.this, "Fail: Account already exist", Toast.LENGTH_LONG).show();
                                else {
                                    boolean isInserted = myDb.insertAccount(editLname.getText().toString(),
                                            editFname.getText().toString(), editUsername.getText().toString(),
                                            editPassword.getText().toString());

                                    if(isInserted) {
                                        Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(i);
                                    }
                                    else
                                        Toast.makeText(RegisterActivity.this, "Fail: Create Account Fail", Toast.LENGTH_LONG).show();
                                }
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
        );
    }

}
