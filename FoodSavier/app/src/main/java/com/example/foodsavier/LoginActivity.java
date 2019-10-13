package com.example.foodsavier;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editUsername, editPassword;
    Button btnLoginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize the database
        myDb = new DatabaseHelper(this);

        // initialize the edit texts
        editUsername = (EditText)findViewById(R.id.editTextLoginEmail);
        editPassword = (EditText)findViewById(R.id.editTextLoginPassword);

        btnLoginPage = (Button) findViewById(R.id.btnLoginLogin);

    }
/*
    public void checkLoginAccount() {
        btnLoginPage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = null;
                        try {
                            res = myDb.getAllAccounts();

                            if (res.getCount() == 0) // no data exist
                                Toast.makeText(LoginActivity.this, "No Account exist in the database", Toast.LENGTH_LONG).show();
                            else {
                                String username = editUsername.getText().toString();
                                String password = editPassword.getText().toString();
                                while (res.moveToNext()) {
                                    if (res.getString(2) == username && res.getString(3) == password) {
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                        //Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                                        //startActivity(i);
                                    }
                                }
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


 */
}
