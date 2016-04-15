package com.assignment.mad.assignmentthird;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Hamza Khan Niaz on 4/7/2016.
 */
public class DBhandler extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "MyDatabase2101";
    private static final int DATABASE_VERSION = 2;
    public static final String STUDENT_TABLE_NAME = "table_student";
    public static final String STUDENT_COLUMN_ID = "_id";
    public static final String STUDENT_COLUMN_NAME = "student_name";
    public static final String STUDENT_COLUMN_DEGREE = "student_degree";
    public static final String STUDENT_COLUMN_DEPARTMENT = "student_department";
    public static final String STUDENT_COLUMN_EMAIL = "student_email";
    public static final String STUDENT_COLUMN_PHONE = "student_phone";



    public DBhandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

                db.execSQL("CREATE TABLE IF NOT EXISTS " + STUDENT_TABLE_NAME + "( " +
                        STUDENT_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                        STUDENT_COLUMN_NAME + " TEXT, " +
                        STUDENT_COLUMN_DEGREE + " TEXT, " +
                        STUDENT_COLUMN_DEPARTMENT + " TEXT, " +
                        STUDENT_COLUMN_EMAIL + " TEXT, " +
                        STUDENT_COLUMN_PHONE + " TEXT);");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME);
        onCreate(db);
    }

    /*
    To insert a new Person, we use the creatively named insertPerson() method.
    We use the SQLiteOpenHelper method getWritableDatabase() to get an SQLiteDatabase object
    reference to our already created database. The Person details are stored in
    a ContentValues object, with the appropriate column name as key,
    and corresponding data as value. We then call SQLiteDatabase’s insert method
    with the person table name, and the ContentValues object.
    NOTE that we left out the STUDENT_COLUMN_ID column,
    which was specified as a primary key.
    It automatically increments.


     */
    public boolean insertPerson(DBhandler handle,String name, String degree, String department, String email,String phone) {
        SQLiteDatabase db = handle.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENT_COLUMN_NAME, name);
        contentValues.put(STUDENT_COLUMN_DEGREE, degree);
        contentValues.put(STUDENT_COLUMN_DEPARTMENT, department);

        contentValues.put(STUDENT_COLUMN_EMAIL, email);

        contentValues.put(STUDENT_COLUMN_PHONE, phone);
        db.insert(STUDENT_TABLE_NAME, null, contentValues);



    return true;
    }

   /* public boolean updatePerson(Integer id, String name, String gender, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENT_COLUMN_NAME, name);
        contentValues.put(STUDENT_COLUMN_GENDER, gender);
        contentValues.put(STUDENT_COLUMN_AGE, age);11111111111111
        db.update(STUDENT_TABLE_NAME, contentValues, STUDENT_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
        return true;


    }*/

    public Cursor getPerson(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + STUDENT_TABLE_NAME + " WHERE " +
                STUDENT_COLUMN_ID + "=?", new String[]{Integer.toString(id)});

        return res;
    }

    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + STUDENT_TABLE_NAME, null);
        return res;
    }

    /*public Integer deletePerson(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(STUDENT_TABLE_NAME,
                STUDENT_COLUMN_ID + " = ? ",
                new String[]{Integer.toString(id)});
    }*/


}