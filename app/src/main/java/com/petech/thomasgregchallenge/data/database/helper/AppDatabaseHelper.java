package com.petech.thomasgregchallenge.data.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    private static final String BANK_NAME = "user";
    private static final int BANK_VERSION = 1;
    public static final String TAG = "AppDatabaseHelper_TAG: ";
    public static final String USER_TABLE_NAME = "user";

    public AppDatabaseHelper(Context context) {
        super(context, BANK_NAME, null, BANK_VERSION);
        Log.i(TAG, "Init");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableUser = "CREATE TABLE IF NOT EXISTS " + BANK_NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(255)," +
                "nick_name VARCHAR(255)," +
                "password VARCHAR(255)," +
                "user_image VARCHAR(255)," +
                "address VARCHAR(255)," +
                "email VARCHAR(255)," +
                "born_date VARCHAR(23)," +
                "gender BOOLEAN," +
                "cpfcnpj VARCHAR(14)" +
                ");";

        sqLiteDatabase.execSQL(createTableUser);

        this.onUpgrade(sqLiteDatabase, 1, BANK_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "onUpgrade:");
    }
}