package com.pagatodo.yaganaste.data.local.persistence.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pagatodo.yaganaste.data.local.persistence.db.manager.SQLiteHelper;

import java.io.IOException;

/**
 * @author jguerras on 10/01/2017.
 */

public abstract class AbstractDao implements DataBaseDao {
    private SQLiteDatabase db;
    private SQLiteHelper SQLiteHelper;
    private Context context;

    public AbstractDao(Context context) {
        this.SQLiteHelper = new SQLiteHelper(context);
        this.context = context;
    }


    /************************** Start delegated methods **************************/
    @Override
    public void open() {
        /*if (App.getInstance().getPrefs().loadDataBool(Recursos.IS_DB_UPDATE_REQUIRED)) {
            updateDB(context);
            App.getInstance().getPrefs().saveDataBool(Recursos.IS_DB_UPDATE_REQUIRED, false);
        }
        if (App.getInstance().getPrefs().loadDataBool(Recursos.IS_BACK_REQUIRED)) {
            makeBack();
            App.getInstance().getPrefs().saveDataBool(Recursos.IS_BACK_REQUIRED, false);
        }*/
        db = SQLiteHelper.getReadableDatabase();
    }

    @Override
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    @Override
    public Cursor rawQuery(String query) {
        return db.rawQuery(query, null);
    }


    @Override
    public Cursor query(String table, String selection, String... selectionArgs) {
        return db.query(table, null, selection, selectionArgs, null, null, null);
    }

    @Override
    public Cursor query(String table, String[] columns, String selection, String... selectionArgs) {
        return db.query(table, columns, selection, selectionArgs, null, null, null);
    }

    @Override
    public Cursor query(boolean distinct, String table, String[] columns, String selection, String... selectionArgs) {
        return db.query(distinct, table, columns, selection, selectionArgs, null, null, null, null);
    }

    @Override
    public Cursor query(String table, String selection, String orderBy, String... selectionArgs){
        return db.query(table, null, selection, selectionArgs, null, null, orderBy);
    }

    @Override
    public int insert(String table, ContentValues contentValues) {
        return (int) db.insert(table, null, contentValues);
    }

    @Override
    @Deprecated
    public int delete(String table, String where) {
        return db.delete(table, where, null);
    }

    @Override
    @Deprecated
    public int delete(String table, String where, String... whereArgs) {
        return db.delete(table, where, whereArgs);
    }

    @Override
    public int update(String table, ContentValues contentValues, String where) {
        return db.update(table, contentValues, where, null);
    }

    @Override
    public int update(String table, ContentValues contentValues, String where, String... whereArgs) {
        return db.update(table, contentValues, where, whereArgs);
    }

    @Override
    public void updateDB(Context context) {
        //SQLiteHelper.copyDataBase(context);
    }




    /************************** End delegated methods **************************/

}
