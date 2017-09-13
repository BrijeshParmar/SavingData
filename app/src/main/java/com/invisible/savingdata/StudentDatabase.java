package com.invisible.savingdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by punit on 11/9/17.
 */

public class StudentDatabase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Student.db";

    private static final String CREATE_STU_TABLE =
            "CREATE TABLE " + TableConstants.STU_TABLE + " (" +
                    TableConstants.STU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TableConstants.STU_NAME + " TEXT," +
                    TableConstants.STU_ENROLL + " TEXT," +
                    TableConstants.STU_EMAIL + " TEXT)";

    private static final String CREATE_SUB_TABLE =
            "CREATE TABLE " + TableConstants.SUB_TABLE + " (" +
                    TableConstants.SUB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TableConstants.SUB_NAME + " TEXT)";

    private static final String CREATE_MARKS_TABLE =
            "CREATE TABLE " + TableConstants.MARKS_TABLE + " (" +
                    TableConstants.MARKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TableConstants.MARKS + " TEXT," +
                    TableConstants.STU_ID + " INTEGER," +
                    TableConstants.SUB_ID + " INTEGER)";


    public StudentDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_STU_TABLE);
        sqLiteDatabase.execSQL(CREATE_SUB_TABLE);
        sqLiteDatabase.execSQL(CREATE_MARKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
