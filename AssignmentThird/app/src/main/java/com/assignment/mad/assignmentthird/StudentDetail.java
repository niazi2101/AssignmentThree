package com.assignment.mad.assignmentthird;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class StudentDetail extends AppCompatActivity {

    //SQLiteDatabase contactsDB = null;
    DBhandler handler ;
    int backButtonCount = 0;

    EditText st_name,st_degree,st_department,st_phone,st_email;

    Button btnView,btnSave;

    String name,degree,dep,email,phone;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        btnView = (Button) findViewById(R.id.buttonViewData);
        btnSave = (Button) findViewById(R.id.buttonEnterData);

        st_name = (EditText) findViewById(R.id.editTextName_st);
        //st_reg = (EditText) findViewById(R.id.editTextReg_st);
        st_degree = (EditText) findViewById(R.id.editTextDegree_st);
        st_department = (EditText) findViewById(R.id.editTextDepartment_st);
        st_phone = (EditText) findViewById(R.id.editTextPhone_st);
        st_email = (EditText) findViewById(R.id.editTextEmail_st);

        handler = new DBhandler(context);
        //handler.onCreate();
        //handler.onCreate();
    }

    public void ViewData_Method(View view) {
        Intent firstIntent = new Intent(getApplicationContext(), StudentList.class);
        //firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(firstIntent);

    }

    public void SaveData_Method(View view) {

        name = st_name.getText().toString();
        //String reg = st_reg.getText().toString();
        degree = st_degree.getText().toString();
        dep = st_department.getText().toString();
        phone = st_phone.getText().toString();
        email = st_email.getText().toString();

        try {
            handler.insertPerson(handler,name,degree,dep,email,phone);

            Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();

            st_name.setText("");
            st_email.setText("");
        }
        catch(Exception f)
        {
            Log.e("CONTACTS ERROR", "Error Inserting Data" + f);

        }

    }
    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            return;
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}
