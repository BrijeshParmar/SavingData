package com.invisible.savingdata;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StudentsActivity extends AppCompatActivity {


    Button btnSave, btnRetrive, btnDelete;
    StudentDatabase myAppDatabase = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnRetrive = (Button) findViewById(R.id.btnRetrive);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        myAppDatabase = new StudentDatabase(getApplicationContext());

        btnRetrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData("Parshva");
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sdb = myAppDatabase.getReadableDatabase();
                sdb.execSQL("DELETE from " + TableConstants.USER_TABLE_NAME);
            }
        });

        addStudent();
        addSubs();
        addMarks();

    }

    public void addStudent() {
        SQLiteDatabase db = myAppDatabase.getWritableDatabase();

        //Save Parshva
        ContentValues values = new ContentValues();
        values.put(TableConstants.STU_NAME, "Parshva");
        values.put(TableConstants.STU_EMAIL, "new@gmail.com");
        values.put(TableConstants.STU_ENROLL, "GU123");

        long saveStatus = db.insert(TableConstants.STU_TABLE, null, values);
        System.out.println("saveStatus=" + saveStatus);

        //Save Gaurav
        values = new ContentValues();
        values.put(TableConstants.STU_NAME, "Gaurav");
        values.put(TableConstants.STU_EMAIL, "naew@gmail.com");
        values.put(TableConstants.STU_ENROLL, "GaU123");

        saveStatus = db.insert(TableConstants.STU_TABLE, null, values);
        System.out.println("saveStatus=" + saveStatus);

        db.close();
    }

    public void addSubs() {
        SQLiteDatabase db = myAppDatabase.getWritableDatabase();

        //Save Android
        ContentValues values = new ContentValues();
        values.put(TableConstants.SUB_NAME, "Android");

        long saveStatus = db.insert(TableConstants.SUB_TABLE, null, values);
        System.out.println("saveStatus=" + saveStatus);

        //Save iOS
        values = new ContentValues();
        values.put(TableConstants.SUB_NAME, "iOS");

        saveStatus = db.insert(TableConstants.SUB_TABLE, null, values);
        System.out.println("saveStatus=" + saveStatus);

        db.close();
    }

    public void addMarks() {
        SQLiteDatabase db = myAppDatabase.getWritableDatabase();

        int idP = getStuId("Parshva");
        int subId = getSubId("Android");

        //Add Marks for Parshva
        ContentValues values = new ContentValues();
        values.put(TableConstants.MARKS, "25");
        values.put(TableConstants.SUB_ID, subId);
        values.put(TableConstants.STU_ID, idP);

        long saveStatus = db.insert(TableConstants.MARKS_TABLE, null, values);
        System.out.println("saveStatus=" + saveStatus);

        db.close();
    }

    int getStuId(String Name) {
        SQLiteDatabase db = myAppDatabase.getReadableDatabase();

        String[] projection = {
                TableConstants.STU_ID};

        String selection = TableConstants.STU_NAME + " =  ? ";
        String[] selectionArgs = {Name};

        String sortOrder =
                TableConstants.STU_ID + " DESC";

        Cursor cursor = db.query(
                TableConstants.STU_TABLE,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        int id = -1;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex(TableConstants.STU_ID));
                    System.out.println("Main3Activity.fetchData==STU_ID==" + id);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }
        return id;
    }

    int getSubId(String Name) {
        SQLiteDatabase db = myAppDatabase.getReadableDatabase();

        String[] projection = {
                TableConstants.SUB_ID};

        String selection = TableConstants.SUB_NAME + " =  ? ";
        String[] selectionArgs = {Name};

        Cursor cursor = db.query(
                TableConstants.SUB_TABLE,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        int id = -1;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex(TableConstants.SUB_ID));
                    System.out.println("Main3Activity.fetchData==SUB_ID==" + id);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }
        return id;
    }


    private void fetchData(String name) {
        SQLiteDatabase sdb = myAppDatabase.getReadableDatabase();

        String query =
                "SELECT a." + TableConstants.STU_ENROLL +
                        ", a." + TableConstants.STU_NAME +
                        ", b." + TableConstants.SUB_NAME +
                        ", c." + TableConstants.MARKS +
                        " FROM " + TableConstants.STU_TABLE + " a, "
                        + TableConstants.SUB_TABLE + " b, "
                        + TableConstants.MARKS_TABLE + " c " +
                        " WHERE a." + TableConstants.STU_ID +
                        "=c." + TableConstants.STU_ID +
                        " AND b." + TableConstants.SUB_ID
                        + "=c." + TableConstants.SUB_ID +
                        " AND a." + TableConstants.STU_NAME + "='" + name+"'";

        System.out.println("query = " + query);

        Cursor cursor = sdb.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String STU_ENROLL = cursor.getString(cursor.getColumnIndex(TableConstants.STU_ENROLL));
                    String STU_NAME = cursor.getString(cursor.getColumnIndex(TableConstants.STU_NAME));
                    String SUB_NAME = cursor.getString(cursor.getColumnIndex(TableConstants.SUB_NAME));
                    String MARKS = cursor.getString(cursor.getColumnIndex(TableConstants.MARKS));

                    System.out.println(STU_ENROLL + " # " + STU_NAME + " # " + SUB_NAME + " # " + MARKS);

                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    private void saveData() {

        SQLiteDatabase db = myAppDatabase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableConstants.USER_NAME, "New");
        values.put(TableConstants.USER_EMAIL, "new@gmail.com");
        values.put(TableConstants.USER_PHONE, "784561230");
        values.put(TableConstants.USER_ADDRESS, "ADDRess is here");

        long saveStatus = db.insert(TableConstants.USER_TABLE_NAME, null, values);
        System.out.println("saveStatus=" + saveStatus);
    }
}
