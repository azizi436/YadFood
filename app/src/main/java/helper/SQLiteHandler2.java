package helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 24/01/2018.
 */

public class SQLiteHandler2 extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "android_api";
    //name jadval
    private static final String TABLE = "profile";
    // Setup Table Columns name****ijad soton hayeh jadid
    //private static final String KEY_UNI = "uni";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "desc";
    private static final String KEY_PASS = "pass";
    private static final String KEY_UNI = "uni";

    public SQLiteHandler2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // create table on call

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Query = "CREATE TABLE " + TABLE + " ("
                //  + KEY_UNI + " TEXT, "
                + KEY_TITLE + " TEXT, "
                + KEY_DESC + " TEXT, "
                + KEY_PASS + " TEXT, "
                + KEY_UNI + " TEXT"
                + ")";
        db.execSQL(Query);
        Log.d(TAG, "Database table created - onCreate");
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
                // + KEY_UNI + " TEXT, "
                + KEY_TITLE + " TEXT, "
                + KEY_DESC + " TEXT, "
                + KEY_PASS + " TEXT, "
                + KEY_UNI + " TEXT"
                + ")";
        db.execSQL(Query);
        db.close();
        Log.d(TAG, "Database table created - Manual");
    }

    //bargozari data
    public void addmember(String title, String desc, String pass, String uni) {
        SQLiteDatabase db = this.getWritableDatabase();

        //aval ersal mikonim
        title = "'" + title + "'";
        desc = "'" + desc + "'";
        // long temp = (Integer.valueOf(String.valueOf(time)));
        pass = "'" + pass + "'";
        uni = "'" + uni + "'";
        //String id = "'" + UUID.randomUUID().toString() + "'";
        //jaygozary mikonim
        String query = "INSERT OR REPLACE INTO " + TABLE + " ("
                //+ KEY_ID + ", "
                + KEY_TITLE + ", "
                + KEY_DESC + ", "
                + KEY_PASS + ", "
                + KEY_UNI

                + ") VALUES ("
                //+ id + ", "
                + title + ", "
                + desc + ", "
                + pass +", "
                + uni
                + ")";

        db.execSQL(query);
        db.close();
        Log.d(TAG, title + " inserted into database");
    }


    /*public void adduni(String uniname) {
        SQLiteDatabase db = this.getWritableDatabase();

        //aval ersal mikonim
        uniname = "'" + uniname + "'";
        //jaygozary mikonim
        String query = "INSERT OR REPLACE INTO " + TABLE + " ("
                //+ KEY_ID + ", "
                + KEY_UNI

                + ") VALUES ("
                //+ id + ", "
                + uniname
                + ")";

        db.execSQL(query);
        db.close();
        Log.d(TAG, "uni sabt shod dar data base" + " inserted into database");
    }*/


    //geraftan data
    public List<String> getmember() {
        List<String> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT * FROM " + TABLE;
        Cursor cursor = db.rawQuery(Query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                item.add(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                item.add(cursor.getString(cursor.getColumnIndex(KEY_DESC)));
                item.add(cursor.getString(cursor.getColumnIndex(KEY_PASS)));
                item.add(cursor.getString(cursor.getColumnIndex(KEY_UNI)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return item;
    }

   /* public void updateuni(String uniname) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE + " SET "
                + KEY_UNI + "=" + uniname;
        db.execSQL(query);
        db.close();
    }*/


    public void updateReach(String titile, String desc, String pass) {

        SQLiteDatabase db = this.getWritableDatabase();
        titile = "'" + titile + "'";
        desc = "'" + desc + "'";
        pass = "'" + pass + "'";

        String query = "UPDATE " + TABLE + " SET "
                + KEY_DESC + "=" + desc
                + KEY_PASS + "=" + pass
                + KEY_TITLE + "=" + titile
                + " WHERE " + KEY_TITLE + "=" + titile;
        db.execSQL(query);
        db.close();
    }

    //inja bayad fekr konam argoman bedim!!!!eroor
    public boolean deletemember(String title, String desc, String pass) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            title = "'" + title + "'";
            desc = "'" + desc + "'";
            pass = "'" + pass + "'";

            db.delete(TABLE, KEY_TITLE + "=" + title, null);
            db.delete(TABLE, KEY_DESC + "=" + desc, null);
            db.delete(TABLE, KEY_PASS + "=" + pass, null);
            db.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //hazfe hame
    public boolean deletemember2() {
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

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, null, null);

       /* db.execSQL("delete * from " + TABLE);
        db.execSQL("TRUNCATE table " + TABLE);*/
        db.close();
    }
}
