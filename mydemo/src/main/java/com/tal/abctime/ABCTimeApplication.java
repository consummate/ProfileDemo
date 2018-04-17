package com.tal.abctime;

import android.app.Application;

/**
 * Created by irene on 2018/4/16.
 */

public class ABCTimeApplication extends Application {
//    private DaoMaster.DevOpenHelper mHelper;
//    private SQLiteDatabase mDatabase;
//    private DaoMaster mDaoMaster;
//    private DaoSession mDaoSession;

    public static ABCTimeApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
//        setDatabase();
    }

//    private void setDatabase() {
//        mHelper = new DaoMaster.DevOpenHelper(this, "userinfo.db");
//        mDatabase = mHelper.getWritableDatabase();
//
//        mDaoMaster = new DaoMaster(mDatabase);
//        mDaoSession = mDaoMaster.newSession();
//    }
//
//    public DaoSession getDaoSession() {
//        if (null == mDaoSession) {
//            mDaoSession = mDaoMaster.newSession();
//        }
//        return mDaoSession;
//    }
//
//    public SQLiteDatabase getDatabase() {
//        return mDatabase;
//    }

}
