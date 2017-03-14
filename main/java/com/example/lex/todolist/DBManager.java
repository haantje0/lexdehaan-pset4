package com.example.lex.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by lex on 3/6/2017.
 */

public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    //constructor
    public DBManager(Context c) {context = c;}

    // opening the database
    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    // closing the database
    public void close() {dbHelper.close();}

    // insert an item in the database
    public void insert(String name) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SUBJECT, name);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
        fetch();
    }

    public Cursor fetch() {
        String[] columns = new String[] {DatabaseHelper._ID, DatabaseHelper.SUBJECT};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Log.d("cursor", Integer.toString(cursor.getCount()));
        return cursor;
    }

    // update the database
    public int update(long id, String name) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SUBJECT, name);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValue, DatabaseHelper._ID + "=" + id, null);
        return i;
    }

    public void delete(long id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + id, null);
    }
}
