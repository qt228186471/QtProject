package com.android.datebase;

import android.database.sqlite.SQLiteDatabase;

/**
 * qt
 * 2019-10-18
 */
public class DBManager {
    private DBManager dbManager;
    private DaoMaster daoMaster;
    private DaoMaster.DevOpenHelper devOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private DaoSession daoSession;
    private DBDemoDao dbDemoDao;

    private DBManager(){
    }
}
