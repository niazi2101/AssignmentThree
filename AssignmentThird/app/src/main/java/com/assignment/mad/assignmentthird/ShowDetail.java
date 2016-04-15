package com.assignment.mad.assignmentthird;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowDetail extends AppCompatActivity {

    TextView Treg,Tname,Tdeg,Tdep,Temail,Tphone;
    String studentName,studentDeg,studentDep,studentEmail,studentPhone;

    int studentID;

    DBhandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        studentID = getIntent().getIntExtra(StudentList.KEY_EXTRA_CONTACT_ID, 0);

        Treg = (TextView) findViewById(R.id.textViewRegistration);
        Tname = (TextView) findViewById(R.id.textViewName);
        Tdeg = (TextView) findViewById(R.id.textViewDegree);
        Tdep = (TextView) findViewById(R.id.textViewDepartment);
        Temail = (TextView) findViewById(R.id.textViewEmail);
        Tphone = (TextView) findViewById(R.id.textViewPhone);

        handler = new DBhandler(this);

        if(studentID > 0) {
            Cursor rs = handler.getPerson(studentID);

            rs.moveToFirst();
            studentName = rs.getString(rs.getColumnIndex(handler.STUDENT_COLUMN_NAME));
            studentDeg = rs.getString(rs.getColumnIndex(handler.STUDENT_COLUMN_DEGREE));
            studentDep = rs.getString(rs.getColumnIndex(handler.STUDENT_COLUMN_DEPARTMENT));
            studentEmail = rs.getString(rs.getColumnIndex(handler.STUDENT_COLUMN_EMAIL));
            studentPhone = rs.getString(rs.getColumnIndex(handler.STUDENT_COLUMN_PHONE));

             if (!rs.isClosed()) {
                rs.close();
            }

            Treg.setText("Reg #: " + studentID);
            Tname.setText("Name : " + studentName);
            Tdeg.setText("Degree : " + studentDeg);
            Tdep.setText("Department : " + studentDep);
            Temail.setText("Email : " + studentEmail);
            Tphone.setText("Phone : " + studentPhone);


        }


    }
}
