/*
 * Copyright (c) 2017 - All Rights Reserved - Arash Hatami
 */

package helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "android_api";
    //name jadval
    private static final String TABLE = "alarm";
    // Setup Table Columns name****ijad soton hayeh jadid
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "desc";
    private static final String KEY_TIME = "time";
    private static final String KEY_REACH = "reach";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // create table on call

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Query = "CREATE TABLE " + TABLE + " ("
                + KEY_ID + " TEXT, "
                + KEY_TITLE + " TEXT, "
                + KEY_DESC + " TEXT, "
                + KEY_TIME + " TEXT, "
                + KEY_REACH + " TEXT"
                + ")";
        db.execSQL(Query);
    }

    // drop and recreate table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    // create table if onCreate can't do that
    public void CreateTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "CREATE TABLE IF NOT EXISTS " + TABLE + " ("
                + KEY_ID + " TEXT, "
                + KEY_TITLE + " TEXT, "
                + KEY_DESC + " TEXT, "
                + KEY_TIME + " TEXT,"
                + KEY_REACH + " TEXT"
                + ")";
        db.execSQL(Query);
        db.close();
    }

    //bargozari data
    public void addAlarm(String title, String desc, String time, String reach) {
        SQLiteDatabase db = this.getWritableDatabase();
        //aval ersal mikonim
        title = "'" + title + "'";
        desc = "'" + desc + "'";
       // long temp = (Integer.valueOf(String.valueOf(time)));
        time = "'" + time + "'";
        reach = "'" + reach + "'";
        String id = "'" + UUID.randomUUID().toString() + "'";
        //jaygozary mikonim
        String query = "INSERT OR REPLACE INTO " + TABLE + " ("
                + KEY_ID + ", "
                + KEY_TITLE + ", "
                + KEY_DESC + ", "
                + KEY_TIME + ","
                + KEY_REACH
                + ") VALUES ("
                + id + ", "
                + title + ", "
                + desc + ", "
                + time + ","
                + reach
                + ")";
        db.execSQL(query);
        db.close();
    }

    //geraftan data
    public List<String> getAlarm() {
        List<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT * FROM " + TABLE;
        Cursor cursor = db.rawQuery(Query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                item.add(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                item.add(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                item.add(cursor.getString(cursor.getColumnIndex(KEY_DESC)));
                item.add(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
                item.add(cursor.getString(cursor.getColumnIndex(KEY_REACH)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return item;
    }

    public void updateReach(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        id = "'" + id + "'";
        String query = "UPDATE " + TABLE + " SET "
                + KEY_REACH + "=" + '0'
                + " WHERE " + KEY_ID + "=" + id;
        db.execSQL(query);
        db.close();
    }

    public boolean deleteAlarm(String id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            id = "'" + id + "'";
            db.delete(TABLE, KEY_ID + "=" + id, null);
            db.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteAlarms() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE, null, null);
            db.close();
            CreateTable();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}