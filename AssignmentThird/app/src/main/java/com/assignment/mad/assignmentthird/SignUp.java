package com.assignment.mad.assignmentthird;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class SignUp extends AppCompatActivity {

    private static final String LOG_DB = "logmessage";
    SQLiteDatabase contactsDB = null;

    Button btnRegComplete;
    EditText textName,textEmail,textPassword,textRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnRegComplete = (Button) findViewById(R.id.buttonRegistered);

        textName = (EditText) findViewById(R.id.editTextName);
        textEmail = (EditText) findViewById(R.id.editTextEmailReg);
        textPassword = (EditText) findViewById(R.id.editTextPasswordReg);
        textRePassword = (EditText) findViewById(R.id.editTextRePassword);



    }

    public void onCreate(View view) {
        try{

            // Opens a current database or creates it
            // Pass the database name, designate that only this app can use it
            // and a DatabaseErrorHandler in the case of database corruption
            contactsDB = this.openOrCreateDatabase("MyDatabase2101", MODE_PRIVATE, null);

            // Execute an SQL statement that isn't select
            contactsDB.execSQL("CREATE TABLE IF NOT EXISTS register_user " +
                    "(id integer primary key not null, name VARCHAR, email VARCHAR, password VARCHAR);");
            //Toast.makeText(getApplicationContext(), "Database Created", Toast.LENGTH_SHORT).show();

            // The database on the file system
            File database = getApplicationContext().getDatabasePath("MyDatabase2101.db");

            // Check if the database exists
            if (database.exists()) {
                Toast.makeText(getApplicationContext(), "Database Created", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(getApplicationContext(), "Database Missing", Toast.LENGTH_SHORT).show();
            }

        }

        catch(Exception e){

            Log.e("CONTACTS ERROR", "Error Creating Database" + e);

        }

        String name = textName.getText().toString();
        String email = textEmail.getText().toString();
        String pass = textPassword.getText().toString();
        String repass = textRePassword.getText().toString();

        try {
            contactsDB.execSQL("INSERT INTO register_user (name, email, password) VALUES ('" +
                    name + "', '" + email + "','" + pass + "');");

            Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();

            Intent firstIntent = new Intent(getApplicationContext(), LoginActivity.class);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(firstIntent);

        }
        catch(Exception f)
        {
            Log.e("INSERT ERROR", "Error Inserting Data" + f);

        }
        }


    public void getContacts(View view) {

        // A Cursor provides read and write access to database results
        Cursor cursor = contactsDB.rawQuery("SELECT * FROM register_user", null);

        // Get the index for the column name provided
        int idColumn = cursor.getColumnIndex("id");
        int nameColumn = cursor.getColumnIndex("name");
        int emailColumn = cursor.getColumnIndex("email");
        int passColumn = cursor.getColumnIndex("password");

        // Move to the first row of results
        cursor.moveToFirst();
        // Verify that we have results
        if(cursor != null && (cursor.getCount() > 0)){

            do{
                // Get the results and store them in a String
                String id = cursor.getString(idColumn);
                String name = cursor.getString(nameColumn);
                String email = cursor.getString(emailColumn);
                String pass = cursor.getString(passColumn);

                Log.i(LOG_DB, "Data: " + id + ", " + name +", "
                + email + ", " + pass + " ... ");

                Toast.makeText(getApplicationContext(), "Data: " + id + ", " + name +", "
                        + email + ", " + pass + " ... ", Toast.LENGTH_SHORT).show();


                // Keep getting results as long as they exist
            }while(cursor.moveToNext());


        } else {

            Toast.makeText(this, "No Results to Show", Toast.LENGTH_SHORT).show();

        }

    }

}
    //                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


