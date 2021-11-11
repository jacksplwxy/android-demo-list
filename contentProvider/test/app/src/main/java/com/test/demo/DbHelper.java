package com.test.demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author feng
 * @desc
 * @time 2018/4/18 23:34
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "userInfo.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_USER_INFO = "userInfo";
    public static final String TEL_COLUMN = "tel_num";
    public static final String DESC_COLUMN = "desc";
    public static final String COMP_ID_COLUMN = "comp_id";

    private static final String USERINFO_TABLE_SQL = "CREATE TABLE " + TABLE_USER_INFO
            + "(" + TEL_COLUMN + " TEXT ,"
            + DESC_COLUMN + "TEXT ,"
            + COMP_ID_COLUMN + " TEXT" + ")";
    private static final String INSERT_INFO_SQL = "insert into " + TABLE_USER_INFO + " values( '15618380859', 'phoneNum', '8')";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERINFO_TABLE_SQL);
        db.execSQL(INSERT_INFO_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
