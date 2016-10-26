package com.crone.skillbranchtest.data.managers;

import android.content.SharedPreferences;

import com.crone.skillbranchtest.MyApp;
import com.crone.skillbranchtest.utils.MyConfig;

class PreferencesManager {
    private SharedPreferences mSharedPreferences;
    private static PreferencesManager INSTANCE = null;

    private PreferencesManager() {
        this.mSharedPreferences = MyApp.getSharedPreferences();
    }

    static PreferencesManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PreferencesManager();
        }
        return INSTANCE;
    }

    void firstComplete(boolean save) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(MyConfig.ALREADY_SAVED, save);
        editor.apply();
    }

    boolean alreadySaved() {
        return mSharedPreferences.getBoolean(MyConfig.ALREADY_SAVED, false);
    }


}