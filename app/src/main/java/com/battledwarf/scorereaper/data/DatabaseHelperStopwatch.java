package com.battledwarf.scorereaper.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.battledwarf.scorereaper.util.Constants;


public class DatabaseHelperStopwatch extends SQLiteOpenHelper {

    //Constants for Database name, table name, and column names
    public static final String DB_NAME = "StopwatchDB";
    public static final String TABLE_NAME = "scans";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CAR = "car";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_LAP_TIME = "lap_time";
    public static final String COLUMN_STATUS = "status";


    //Constructor
    public DatabaseHelperStopwatch(Context context) {
        super(context, DB_NAME, null, Constants.DB_VERSION);
    }

    //creating the database 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CAR + " VARCHAR, "
                + COLUMN_LOCATION + " VARCHAR, "
                + COLUMN_USER + " VARCHAR, "
                + COLUMN_LAP_TIME + " LONG, "
                + COLUMN_STATUS + " TINYINT);";

        db.execSQL(sql);
    }

    //upgrading the database 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS scans";
        db.execSQL(sql);
        onCreate(db);
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DROP TABLE IF EXISTS scans";
        db.execSQL(sql);
        onCreate(db);
    }

    public void addLapStartScan(String car, String location, String user, Long lap_time, int status) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CAR, car);
        contentValues.put(COLUMN_LOCATION, location);
        contentValues.put(COLUMN_USER, user);
        contentValues.put(COLUMN_LAP_TIME, lap_time);
        contentValues.put(COLUMN_STATUS, status);


        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public void updateSyncStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
    }

    public Cursor getScans() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC;";
        return db.rawQuery(sql, null);
    }

    public Cursor getLastScan() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1;";
        return db.rawQuery(sql, null);
    }

    public Cursor getUnsyncedScans() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " = 0;";
        return db.rawQuery(sql, null);
    }

}
