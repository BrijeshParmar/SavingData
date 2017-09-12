package com.invisible.savingdata;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {


    Button btnSave, btnRetrive, btnDelete;
    MyAppDatabase myAppDatabase = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnRetrive = (Button) findViewById(R.id.btnRetrive);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        myAppDatabase = new MyAppDatabase(getApplicationContext());

        btnRetrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAll();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sdb = myAppDatabase.getReadableDatabase();
                sdb.execSQL("DELETE from " + TableConstants.USER_TABLE_NAME);
            }
        });
    }

    private void fetchData() {
        SQLiteDatabase db = myAppDatabase.getReadableDatabase();

        String[] projection = {
                TableConstants.USER_ID,
                TableConstants.USER_NAME,
                TableConstants.USER_EMAIL
        };

        String selection = TableConstants.USER_PHONE + " LIKE  '%789456%' ";
        String[] selectionArgs = {"789456"};

        String sortOrder =
                TableConstants.USER_ID + " DESC";

        Cursor cursor = db.query(
                TableConstants.USER_TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                null/*selectionArgs*/,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(TableConstants.USER_NAME));
                    String email = cursor.getString(cursor.getColumnIndex(TableConstants.USER_EMAIL));
                    int id = cursor.getInt(cursor.getColumnIndex(TableConstants.USER_ID));

                    System.out.println("Main3Activity.fetchData==" + (id + "==" + name + "==" + email));
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }


    }

    private void selectAll() {
        SQLiteDatabase sdb = myAppDatabase.getReadableDatabase();

        Cursor cursor = sdb.rawQuery("SELECT * FROM " + TableConstants.USER_TABLE_NAME, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(TableConstants.USER_NAME));
                    String email = cursor.getString(cursor.getColumnIndex(TableConstants.USER_EMAIL));
                    String phone = cursor.getString(cursor.getColumnIndex(TableConstants.USER_PHONE));
                    String address = cursor.getString(cursor.getColumnIndex(TableConstants.USER_ADDRESS));
                    int id = cursor.getInt(cursor.getColumnIndex(TableConstants.USER_ADDRESS));

                    System.out.println("Main3Activity.SelectAll==" + (id + "==" + name + "==" + phone + "==" + email + "==" + address));
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
