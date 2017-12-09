package com.vitalii.s.a10tictactoe.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 30.01.2017.
 */
public class HotelDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hotel.db";
    private static final int DATABASE_VERSION = 15;



    public HotelDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Вызывается при создании базы данных
     */
    @Override

    //creating table 1
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_DIFF_EASY_CROSS_TABLE = "CREATE TABLE " + HoteContract.BestScore.TABLE_NAME_DIFF_EASY_CROSS + " ("
                + HoteContract.BestScore._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                //+ HoteContract.BestScore.COLUMN_NAME + " TEXT NOT NULL, "
                + HoteContract.BestScore.COLUMN_SCORE + " INTEGER NOT NULL DEFAULT 0);";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_DIFF_EASY_CROSS_TABLE);
        System.out.println("DATA 1 CREATED");


        ContentValues values = new ContentValues();
        values.put(HoteContract.BestScore.COLUMN_SCORE, 0);

        long newRowId = db.insert(HoteContract.BestScore.TABLE_NAME_DIFF_EASY_CROSS, null, values);


        // creating table 2
        String SQL_CREATE_DIFF_HARD_CROSS_TABLE = "CREATE TABLE " + HoteContract.BestScore.TABLE_NAME_DIFF_HARD_CROSS + " ("
                + HoteContract.BestScore._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                //+ HoteContract.BestScore.COLUMN_NAME + " TEXT NOT NULL, "
                + HoteContract.BestScore.COLUMN_SCORE + " INTEGER NOT NULL DEFAULT 0);";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_DIFF_HARD_CROSS_TABLE);
        System.out.println("DATA 2 CREATED");


        long newRowId2 = db.insert(HoteContract.BestScore.TABLE_NAME_DIFF_HARD_CROSS, null, values);

        // creating table 3
        String SQL_CREATE_DIFF_EASY_NOUGHT_TABLE = "CREATE TABLE " + HoteContract.BestScore.TABLE_NAME_DIFF_EASY_NOUGHT + " ("
                + HoteContract.BestScore._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                //+ HoteContract.BestScore.COLUMN_NAME + " TEXT NOT NULL, "
                + HoteContract.BestScore.COLUMN_SCORE + " INTEGER NOT NULL DEFAULT 0);";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_DIFF_EASY_NOUGHT_TABLE);
        System.out.println("DATA 3 CREATED");


        long newRowId3 = db.insert(HoteContract.BestScore.TABLE_NAME_DIFF_EASY_NOUGHT, null, values);

        // creating table 4
        String SQL_CREATE_DIFF_HARD_NOUGHT_TABLE = "CREATE TABLE " + HoteContract.BestScore.TABLE_NAME_DIFF_HARD_NOUGHT + " ("
                + HoteContract.BestScore._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                //+ HoteContract.BestScore.COLUMN_NAME + " TEXT NOT NULL, "
                + HoteContract.BestScore.COLUMN_SCORE + " INTEGER NOT NULL DEFAULT 0);";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_DIFF_HARD_NOUGHT_TABLE);
        System.out.println("DATA 4 CREATED");


        long newRowId4 = db.insert(HoteContract.BestScore.TABLE_NAME_DIFF_HARD_NOUGHT, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // table 1
        // Удаляем старую таблицу и создаём новую
        db.execSQL("DROP TABLE IF EXISTS " + HoteContract.BestScore.TABLE_NAME_DIFF_EASY_CROSS);
        // Создаём новую таблицу

        //table 2
        db.execSQL("DROP TABLE IF EXISTS " + HoteContract.BestScore.TABLE_NAME_DIFF_HARD_CROSS);

        //table 3
        db.execSQL("DROP TABLE IF EXISTS " + HoteContract.BestScore.TABLE_NAME_DIFF_EASY_NOUGHT);

        //table 4
        db.execSQL("DROP TABLE IF EXISTS " + HoteContract.BestScore.TABLE_NAME_DIFF_HARD_NOUGHT);

        onCreate(db);


    }
}
