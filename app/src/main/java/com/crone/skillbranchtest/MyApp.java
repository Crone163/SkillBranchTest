package com.crone.skillbranchtest;

import android.app.Application;

import com.crone.skillbranchtest.models.DaoMaster;
import com.crone.skillbranchtest.models.DaoSession;

import org.greenrobot.greendao.database.Database;


public class MyApp extends Application {

    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "houses-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession () {
        return mDaoSession;
    }
}
