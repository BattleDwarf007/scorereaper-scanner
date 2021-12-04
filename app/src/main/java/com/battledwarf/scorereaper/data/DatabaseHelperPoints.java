package com.battledwarf.scorereaper.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.battledwarf.scorereaper.util.Constants;

public class DatabaseHelperPoints extends SQLiteOpenHelper {

    //Constants for Database name, table name, and column names
    public static final String DB_NAME = "PointsDB";
    public static final String TABLE_NAME = "scans";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CAR = "car";
    public static final String COLUMN_TIME = "scantime";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_POINTS = "points";
    public static final String COLUMN_STATUS = "status";


    //Constructor
    public DatabaseHelperPoints(Context context) {
        super(context, DB_NAME, null, Constants.DB_VERSION);
    }

    //creating the database 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CAR + " VARCHAR, "
                + COLUMN_TIME + " DATETIME, "
                + COLUMN_LOCATION + " VARCHAR, "
                + COLUMN_USER + " VARCHAR, "
                + COLUMN_POINTS + " VARCHAR, "
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

    public void addScan(String car, String location, String user, String points, String timestamp, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CAR, car);
        contentValues.put(COLUMN_TIME, timestamp);
        contentValues.put(COLUMN_LOCATION, location);
        contentValues.put(COLUMN_USER, user);
        contentValues.put(COLUMN_POINTS, points);
        contentValues.put(COLUMN_STATUS, status);


        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public void updateScan(String car, String location, String user, String points, String timestamp, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CAR, car);
        contentValues.put(COLUMN_TIME, timestamp);
        contentValues.put(COLUMN_LOCATION, location);
        contentValues.put(COLUMN_USER, user);
        contentValues.put(COLUMN_POINTS, points);
        contentValues.put(COLUMN_STATUS, status);

        db.update(TABLE_NAME, contentValues, "car = ?", new String[]{car});
        db.close();
    }

    public void updateSyncStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
    }

    /*
     * this method will give us all the name stored in sqlite
     * */
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

    /*
     * this method is for getting all the unsynced name
     * so that we can sync it with database
     * */
    public Cursor getUnsyncedScans() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " = 0;";
        return db.rawQuery(sql, null);
    }

    public boolean CheckIfRecordExist(String car) {
        Cursor result;
        SQLiteDatabase db = this.getReadableDatabase();
        result = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_CAR + "='" + car + "'", null);

        if (result.getCount() <= 0){
            return false;
        }
        result.close();
        return true;
    }

}
