package com.vitalii.s.a10tictactoe.Data;

import android.provider.BaseColumns;

/**
 * Created by user on 30.01.2017.
 */
public class HoteContract {

    public HoteContract() {

    }
  //таблица
    public static final class BestScore implements BaseColumns {

        public final static String TABLE_NAME = "bestScore";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_SCORE = "score";

    }
}



