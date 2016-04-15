package com.assignment.mad.assignmentthird;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    String contactEmail, contactPassword;

    Button btnSign,btnRegister;
    EditText email,password;

    SQLiteDatabase contactsDB = null;
    CheckBox saveLoginCheckBox;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    private boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSign = (Button) findViewById(R.id.buttonLogIn);
        btnRegister = (Button) findViewById(R.id.buttonReg);

        email = (EditText) findViewById(R.id.editTextEmail_st);
        password = (EditText) findViewById(R.id.editTextPass);

        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();




        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        try {

            // Opens a current database or creates it
            // Pass the database name, designate that only this app can use it
            // and a DatabaseErrorHandler in the case of database corruption
            contactsDB = this.openOrCreateDatabase("MyDatabase2101", MODE_PRIVATE, null);

            // Execute an SQL statement that isn't select

        } catch (Exception e) {

            Log.e("CONTACTS ERROR", "Error Creating Database" + e);
            Toast.makeText(getApplicationContext(), "Error Creating Database", Toast.LENGTH_SHORT).show();

        }

        if (saveLogin == true) {
            email.setText(loginPreferences.getString("contactEmail", ""));
            password.setText(loginPreferences.getString("contactPassword", ""));
            saveLoginCheckBox.setChecked(true);
        }
    }

    public void SignIn(View view) {
        contactEmail = email.getText().toString();
        contactPassword = password.getText().toString();
        boolean value= false;

        // A Cursor provides read and write access to database results

        Cursor cursor = contactsDB.rawQuery("SELECT email,password FROM register_user", null);

        // Get the index for the column name provided
        //int idColumn = cursor.getColumnIndex("id");
        int emailColumn = cursor.getColumnIndex("email");
        int passColumn = cursor.getColumnIndex("password");

        // Move to the first row of results
        cursor.moveToFirst();


        // Verify that we have results
        if (cursor != null && (cursor.getCount() > 0)) {

            do {
                // Get the results and store them in a String
                //      String DBid = cursor.getString(idColumn);
                 String DBemail = cursor.getString(emailColumn);
                String DBpass = cursor.getString(passColumn);

                if (contactEmail.equals(DBemail) && contactPassword.equals(DBpass)) {
                    Toast.makeText(getApplicationContext(), "Login Success: " + DBemail + " " + DBpass + " ", Toast.LENGTH_SHORT).show();

                    value = true;
                }

                // Keep getting results as long as they exist
            } while (cursor.moveToNext());

            //contactListEditText.setText(contactList);

        } else {

            Toast.makeText(getApplicationContext(), "No Results to Show", Toast.LENGTH_SHORT).show();
            //contactListEditText.setText("");

        }

        if(value)
        {
            Intent j = new Intent(getApplicationContext(),StudentDetail.class);
            startActivity(j);

            //saving login detail, so that user doesn't have to enter his detail every time
            if (saveLoginCheckBox.isChecked()) {
                loginPrefsEditor.putBoolean("saveLogin", true);
                loginPrefsEditor.putString("contactEmail", contactEmail);
                loginPrefsEditor.putString("contactPassword", contactPassword);
                loginPrefsEditor.commit();
            } else {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
            }

            email.setText("");
            password.setText("");
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            password.setText("");
        }

    }

    public void OpenRegistration(View view) {
        Intent i = new Intent(getApplicationContext(),SignUp.class);
        startActivity(i);
    }
}