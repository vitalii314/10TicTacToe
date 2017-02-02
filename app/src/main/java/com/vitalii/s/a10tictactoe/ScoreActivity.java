package com.vitalii.s.a10tictactoe;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.vitalii.s.a10tictactoe.Data.HoteContract;
import com.vitalii.s.a10tictactoe.Data.HotelDbHelper;

import org.w3c.dom.Text;

public class ScoreActivity extends AppCompatActivity {

    private HotelDbHelper mDbHelper;
    private TextView mName;
    private TextView mScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        mDbHelper = new HotelDbHelper(this);
        mName = (TextView)findViewById(R.id.textName);
        mScore = (TextView)findViewById(R.id.textScore);


    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }


    public void displayDatabaseInfo() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                HoteContract.BestScore.COLUMN_NAME};
        String selection = HoteContract.BestScore._ID+">?";
        String[] selectionArgs = {"1"};


        // Делаем запрос
        Cursor cursor = db.query(
                HoteContract.BestScore.TABLE_NAME,   // таблица
                projection,            // столбцы
                selection,                  // столбцы для условия WHERE
                selectionArgs,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                HoteContract.BestScore.COLUMN_SCORE + " DESC");// порядок сортировки


        try {
            mName.setText("Таблица содержит " + cursor.getCount() + " чемпионов.\n\n");
            mName.append(HoteContract.BestScore._ID + " - " +
                    HoteContract.BestScore.COLUMN_NAME + " - " +
                    HoteContract.BestScore.COLUMN_SCORE  + "\n");

            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(HoteContract.BestScore._ID);
            int nameColumnIndex = cursor.getColumnIndex(HoteContract.BestScore.COLUMN_NAME);
            int scoreColumnIndex = cursor.getColumnIndex(HoteContract.BestScore.COLUMN_SCORE);


            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentScore = cursor.getString(scoreColumnIndex);

                // Выводим значения каждого столбца
                mName.append("\n" + currentID + " - " +
                        currentName + " - " +
                        currentScore );
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }
}








