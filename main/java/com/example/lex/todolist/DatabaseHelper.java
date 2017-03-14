package com.example.lex.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lex on 3/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "TODOLISTDATABASE";
    private static final int DB_VERSION = 1;

    public static final String _ID = "_id";
    public static final String TABLE_NAME = "TODOLIST";
    public static final String SUBJECT = "subject";

    // create query table
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT NOT NULL);";

    // constructor
    public DatabaseHelper(Context context) {super(context, DB_NAME, null, DB_VERSION);}

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
