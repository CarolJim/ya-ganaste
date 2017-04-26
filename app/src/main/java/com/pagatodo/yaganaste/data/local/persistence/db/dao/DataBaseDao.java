package com.pagatodo.yaganaste.data.local.persistence.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * @author jguerras on 10/01/2017.
 */

 interface DataBaseDao {

    void open();
    void close();
    Cursor rawQuery(String query);
    Cursor query(String table, String selection, String... selectionArgs);
    Cursor query(String table, String[] columns, String selection, String... selectionArgs);
    Cursor query(boolean distinct, String table, String[] columns, String selection, String... selectionArgs);
    int insert(String table, ContentValues contentValues);
    int delete(String table, String where);
    int delete(String table, String where, String... whereArgs);
    int update(String table, ContentValues contentValues, String where);
    int update(String table, ContentValues contentValues, String where, String... whereArgs);
    void updateDB(Context context);

}
