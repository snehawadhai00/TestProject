package com.technocrate.testproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE = "Registration.db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "register";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Full_Name";
    public static final String COL_3 = "USERNAME";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "MOBILE_NO";
    public static final String COL_6 = "PASSWORD";
    public static final String COL_7 = "C_PASSWORD";

    public DBHelper(@Nullable Context context) {
        super(context,DATABASE,null,VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , Full_NAME TEXT ,USERNAME TEXT,MONILE_NO INTEGER,EMAIL TEXT," +
                "PASSWORD TEXT,C_PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String name, String uname, String email, String mobile, String pass, String cpass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, name);
        cv.put(COL_3, uname);
        cv.put(COL_4, email);
        cv.put(COL_5, mobile);
        cv.put(COL_6, pass);
        cv.put(COL_7, cpass);

        long success = db.insert(TABLE_NAME, null, cv);
        if (success == -1) {
            return false;
        }
        else {
            return true;
        }
    }

}
