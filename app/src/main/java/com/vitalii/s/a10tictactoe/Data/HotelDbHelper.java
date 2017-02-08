package com.vitalii.s.a10tictactoe.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 30.01.2017.
 */
public class HotelDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = HotelDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "hotel.db";
    private static final int DATABASE_VERSION = 1;



    public HotelDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Вызывается при создании базы данных
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE " + HoteContract.BestScore.TABLE_NAME + " ("
                + HoteContract.BestScore._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HoteContract.BestScore.COLUMN_NAME + " TEXT NOT NULL, "
                + HoteContract.BestScore.COLUMN_SCORE + " INTEGER NOT NULL DEFAULT 0);";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_GUESTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }
}
