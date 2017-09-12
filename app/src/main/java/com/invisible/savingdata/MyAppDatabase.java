package com.invisible.savingdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by punit on 11/9/17.
 */

public class MyAppDatabase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "MyApp.db";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TableConstants.USER_TABLE_NAME + " (" +
                    TableConstants.USER_ID + " INTEGER PRIMARY KEY," +
                    TableConstants.USER_NAME + " TEXT," +
                    TableConstants.USER_EMAIL + " TEXT," +
                    TableConstants.USER_PHONE + " TEXT)";

    private static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TableConstants.USER_TABLE_NAME;

    public MyAppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("ALTER TABLE " + TableConstants.USER_TABLE_NAME + " ADD " + TableConstants.USER_ADDRESS + " VARCHAR DEFAULT ''");
    }
}
