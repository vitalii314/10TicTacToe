package com.vitalii.s.a10tictactoe;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by user on 12.08.2017.
 */
public class MyApplication extends Application {

    public static SharedPreferences preferences;


    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences(getPackageName()+"_preferences",MODE_PRIVATE);
    }
}
