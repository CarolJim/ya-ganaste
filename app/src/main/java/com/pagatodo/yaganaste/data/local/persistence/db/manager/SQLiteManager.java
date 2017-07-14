package com.pagatodo.yaganaste.data.local.persistence.db.manager;

import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jordan on 24/04/2017.
 */

public class SQLiteManager {
    public static SQLiteManager instance;
    private static SQLiteHelper mSQLiteHelper;
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private SQLiteDatabase mDataBase;

    public static synchronized void intitializeInstance(SQLiteHelper helper) {
        if (instance == null) {
            instance = new SQLiteManager();
            mSQLiteHelper = helper;

            try {
                mSQLiteHelper.createTables();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized SQLiteManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(SQLiteManager.class.getSimpleName() +
                    "no inicializada, llamar primero intitializeInstance()");
        }

        return instance;
    }

    public synchronized SQLiteDatabase openDataBase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            mDataBase = mSQLiteHelper.getWritableDatabase();
        }
        return mDataBase;
    }

    public synchronized void closeDataBase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            mDataBase.close();
        }
    }
}
