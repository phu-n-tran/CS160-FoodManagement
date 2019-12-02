package com.example.foodsavierapp;

import androidx.appcompat.app.AppCompatActivity;

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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    //Intent
    //CURRENT: (NONE)
    //TRANSFER TO DASHBOARD ACTIVITY: username

    DatabaseHelper myDb;
    EditText editUsername, editPassword;
    Button btnLoginPage;
    TextView noAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize the database
        myDb = new DatabaseHelper(this);

        noAccount = (TextView)findViewById(R.id.textViewNoAccountLogin);

        // initialize the edit texts
        editUsername = (EditText)findViewById(R.id.editTextLoginEmail);
        editPassword = (EditText)findViewById(R.id.editTextLoginPassword);

        // add listeners to editTexts
        editUsername.addTextChangedListener(loginTextWatcher);
        editPassword.addTextChangedListener(loginTextWatcher);
        // initialize the button
        btnLoginPage = (Button) findViewById(R.id.btnLoginLogin);
        // method call: btnLoginPage button listener
        checkLoginAccount();


        noAccountListener();

    }

    /**
     * On click listener when user don't have an account yet
     */
    public void noAccountListener() {
        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Text listener: only activate the button if all fields is entered
     */
    public TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = editUsername.getText().toString();
            String passwordInput =  editPassword.getText().toString();

            btnLoginPage.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * On click listener: for login button
     */
    public void checkLoginAccount() {
        btnLoginPage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean failFlag = true;
                        Cursor res = null;
                        try {
                            res = myDb.getAllAccounts();

                            if (res.getCount() == 0) // no data exist
                                Toast.makeText(LoginActivity.this, "No Account exist in the database", Toast.LENGTH_LONG).show();
                            else {
                                String username = editUsername.getText().toString();
                                String password = md5(editPassword.getText().toString());
                                while (res.moveToNext()) {
                                    if (res.getString(2).equals(username) && res.getString(3).equals(password)) {
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                        failFlag = false;
                                        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                                        i.putExtra("username", editUsername.getText().toString());
                                        startActivity(i);
                                        break;
                                    }
                                }

                                if (failFlag)
                                    Toast.makeText(LoginActivity.this, "Incorrect Username/Password or no account exist", Toast.LENGTH_LONG).show();

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
