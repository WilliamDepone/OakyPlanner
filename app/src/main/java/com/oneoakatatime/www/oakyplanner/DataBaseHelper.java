package com.oneoakatatime.www.oakyplanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 5/6/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "data.db";
    public static final String TABLE_NAME_1 = "events_table";
    public static final String TABLE_NAME_2 = "account_table";
    public static final String ID_1 = "ID_1";
    public static final String ID_2 = "ID_2";
    public static final String YEAR = "YEAR";
    public static final String MONTH = "MONTH";
    public static final String DAY = "DAY";
    public static final String HOUR = "HOUR";
    public static final String MINUTE = "MINUTE";
    public static final String EVENT_DESCRIPTION = "EVENT_DESCRIPTION";
    public static final String PLACE = "PLACE";
    public static final String NICKNAME = "NICKNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String AGE = "AGE";
    public static final String GENDER = "GENDER";

    public DataBaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
try{
    db.execSQL("create table " + TABLE_NAME_1 + "(ID_1 INTEGER PRIMARY KEY AUTOINCREMENT,YEAR INTEGER,MONTH INTEGER,DAY INTEGER,HOUR INTEGER,MINUTE INTEGER,EVENT_DESCRIPTION TEXT,PLACE TEXT)");
        db.execSQL("create table " + TABLE_NAME_2 +"(ID_2 INTEGER PRIMARY KEY AUTOINCREMENT,NICKNAME TEXT,PASSWORD TEXT,AGE INTEGER,GENDER TEXT)");
}
catch(Exception e){

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_1);
       db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_2);
        onCreate(db);
    }
}
