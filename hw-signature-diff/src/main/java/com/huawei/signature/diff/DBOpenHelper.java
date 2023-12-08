package com.huawei.signature.diff;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = DBOpenHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "app_list.db";
    public static final String DATABASE_APP_LIST_TABLE_NAME = "applist";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_APP_LIST_TABLE = "CREATE TABLE IF NOT EXISTS " + DATABASE_APP_LIST_TABLE_NAME
            + "(name VARCHAR(255) PRIMARY KEY, isDiff INTEGER CHECK(isDiff >= 0 and isDiff <= 1))";
    private final Context mContext;

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        db.execSQL(CREATE_APP_LIST_TABLE);
        initData(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        initData(db);
    }
    private void initData(SQLiteDatabase db) {
        String[] whiteList = mContext.getResources().getStringArray(R.array.white);
        String[] blackList = mContext.getResources().getStringArray(R.array.black);
        if (whiteList.length == 0 && blackList.length == 0) {
            return;
        }
        for (String whiteApp : whiteList) {
            db.insertWithOnConflict(DATABASE_APP_LIST_TABLE_NAME, null, generateValues(whiteApp, true), SQLiteDatabase.CONFLICT_REPLACE);
        }
        for (String blackApp : blackList) {
            db.insertWithOnConflict(DATABASE_APP_LIST_TABLE_NAME, null, generateValues(blackApp, false), SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    private ContentValues generateValues(String packageName, boolean isDiff) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", packageName);
        contentValues.put("isDiff", isDiff ? 1 : 0);
        return contentValues;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade o = " + oldVersion + " , n = " + newVersion);
    }
}