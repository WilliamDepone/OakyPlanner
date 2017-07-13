package com.oneoakatatime.www.oakyplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.content.Context;

/** Does all the database related things, table_name1 is for the events that the user puts in his planner
 * table_name2 is for his account information.
 */


public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "data.db";
    public static final String TABLE_NAME_1 = "events_table";
    public static final String TABLE_NAME_2 = "account_table";
    public static final String ID_1 = "ID_1";
    public static final String ID_2 = "ID_2";
    public static final String YEAR = "YEAR";
    public static final String MONTH = "MONTH";
    public static final String WEEK = "WEEK";
    public static final String DAYOFWEEK = "DAYOFWEEK";
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

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
try{
    db.execSQL("create table " + TABLE_NAME_1 + "(ID_1 INTEGER PRIMARY KEY AUTOINCREMENT,YEAR INTEGER,MONTH INTEGER,WEEK INTEGER,DAYOFWEEK INTEGER,DAY INTEGER,HOUR INTEGER,MINUTE INTEGER,EVENT_DESCRIPTION TEXT,PLACE TEXT)");
        db.execSQL("create table " + TABLE_NAME_2 +"(ID_2 INTEGER PRIMARY KEY AUTOINCREMENT,NICKNAME TEXT,PASSWORD TEXT,AGE INTEGER,GENDER TEXT)");
}
catch(Exception e){

        }

    }

    @Override
    /* warning could drop both tables if one is upgraded */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_1);
       db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_2);
        onCreate(db);
    }
    public boolean insertData1(int year, int month,int week,int dayofweek, int day, int hour, int minute,String event_description,String place ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(YEAR,year);
        contentValues.put(MONTH,month);
        contentValues.put(WEEK,week);
        contentValues.put(DAYOFWEEK,dayofweek);
        contentValues.put(DAY,day);
        contentValues.put(HOUR,hour);
        contentValues.put(MINUTE,minute);
        contentValues.put(EVENT_DESCRIPTION,event_description);
        contentValues.put(PLACE,place);
         long result = db.insert(TABLE_NAME_1,null,contentValues);

        if (result == -1)
        return false;
        else
            return true;



    }
    public void deleteEvent(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME_1+ " WHERE " + ID_1 +"='" + id +  "'");
        db.close();
    }




  public Cursor getAllRows(int year, int month, int day) {

      SQLiteDatabase db = open();
      String[] columns = {"rowid _id",HOUR,MINUTE,PLACE,EVENT_DESCRIPTION,ID_1};
      Cursor c = 	db.query(TABLE_NAME_1,columns,YEAR+"='"+year+"'"+" AND "+MONTH+"='"+month+"'"+" AND "+DAY+"='"+day+"'",null,null,null,null);

      if (c != null) {
          c.moveToFirst();

      }

      closeDB();
      return c;
  }
    public Cursor getAllWeeklyRows(int year, int month, int week) {
        SQLiteDatabase db = open();

        String[] columns = {"rowid _id",DAY,HOUR,MINUTE,EVENT_DESCRIPTION,PLACE,DAYOFWEEK,ID_1};
        Cursor c = 	db.query(TABLE_NAME_1,columns,YEAR+"='"+year+"'"+" AND "+MONTH+"='"+month+"'"+" AND "+WEEK+"='"+week+"'",null,null,null,null);

        if (c != null) {
            c.moveToFirst();

        }
        closeDB();
        return c;
    }
  public Cursor getEventInfo(Long id){
      SQLiteDatabase db = open();
      String[] columns = {ID_1,HOUR,MINUTE,PLACE,EVENT_DESCRIPTION};
      Cursor c = db.query(TABLE_NAME_1,columns,ID_1+"='"+id+"'",null,null,null,null);
      if (c!= null){
          c.moveToFirst();
      }
      closeDB();
      return c;

  }

  public SQLiteDatabase open() throws SQLiteException{
      close();
      SQLiteDatabase db = this.getWritableDatabase();
      return db;
  }
  public void closeDB(){
      SQLiteDatabase db = this.getWritableDatabase();
      if(db != null && db.isOpen()){
          db.close();
      }
  }

}
