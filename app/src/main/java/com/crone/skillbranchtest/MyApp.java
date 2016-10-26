package com.crone.skillbranchtest;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.crone.skillbranchtest.data.storage.models.DaoMaster;
import com.crone.skillbranchtest.data.storage.models.DaoSession;

import org.greenrobot.greendao.database.Database;


public class MyApp extends Application {

    private static DaoSession mDaoSession;
    public static SharedPreferences mSharedPreferences;


    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "houses-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }
}
