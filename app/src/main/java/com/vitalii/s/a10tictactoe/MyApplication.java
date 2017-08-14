package com.vitalii.s.a10tictactoe;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by user on 12.08.2017.
 */
public class MyApplication extends Application {

    public static SharedPreferences preferences;
    public static boolean isNormalRun;


    @Override
    public void onCreate() {
        super.onCreate();
        checkFirstRun();
        //preferences = getSharedPreferences(getPackageName()+"_preferences",MODE_PRIVATE);
    }

    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;
        //int currentVersionCode = 7;

        // Get saved version code
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = preferences.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            isNormalRun = true;
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {
            isNormalRun = false;
            // TODO This is a new install (or the user cleared the shared preferences)

        } else if (currentVersionCode > savedVersionCode) {
            isNormalRun = false;
            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        preferences.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }
}
