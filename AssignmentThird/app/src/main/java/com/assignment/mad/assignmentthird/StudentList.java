package com.assignment.mad.assignmentthird;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;

public class StudentList extends AppCompatActivity {


    public final static String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_CONTACT_ID";

    DBhandler handler;
    Cursor cursor;
    AlertDialog dialog;

    TodoCursorAdaptor todoAdaptor;
    ListView listView;

    Context context = this;

    private static final int DIALOG_ALERT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        try {
            handler = new DBhandler(this);

            cursor = handler.getAllStudents();
            Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
            /*String[] columns = new String[]{
                    handler.STUDENT_COLUMN_ID,
                    handler.STUDENT_COLUMN_NAME


            };*/
        /*int[] widgets = new int[]{
                R.id.studentReg,
                R.id.studentName
        };*/

        /*SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.list_item,
                cursor, columns, widgets, 0);*/
            listView = (ListView) findViewById(R.id.listView);

            todoAdaptor = new TodoCursorAdaptor(context, cursor, 0);
            listView.setAdapter(todoAdaptor);

            // Show The Dialog with Selected SMS
            dialog = new AlertDialog.Builder(getApplicationContext()).create();
            dialog.setTitle("Item Clicked");
            dialog.setIcon(android.R.drawable.ic_dialog_info);
            dialog.setMessage("You have clicked a list item");
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            return;
                        }
                    });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView listView, View view,
                                        int position, long id) {
                Cursor itemCursor = (Cursor) StudentList.this.listView.getItemAtPosition(position);
                int studentID = itemCursor.getInt(itemCursor.getColumnIndex(DBhandler.STUDENT_COLUMN_ID));



                    Toast.makeText(getApplicationContext(),"Roll Num clicked: " + studentID,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ShowDetail.class);
                    intent.putExtra(KEY_EXTRA_CONTACT_ID, studentID);
                    startActivity(intent);

                }
            });
        }catch(Exception e)
        {
            Log.e("ERROR","UNABLE TO DISPLAY STUDENT LIST: "+e);
            Log.e("ERROR","UNABLE TO DISPLAY STUDENT LIST: "+cursor.getColumnNames().toString());
        }

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("This will end the activity");
                builder.setCancelable(true);
                builder.setPositiveButton("I agree", new OkOnClickListener());
                builder.setNegativeButton("No, no", new CancelOnClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        return super.onCreateDialog(id);
    }

    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getApplicationContext(), "Activity will continue", Toast.LENGTH_LONG).show();
        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getApplicationContext(), "Just kidding", Toast.LENGTH_LONG).show();
        }
    }


}
