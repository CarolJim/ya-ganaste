package com.pagatodo.yaganaste.data.local.persistence.db.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jordan on 24/04/2017.
 */

public class DataBaseUtils {

    public static final String DB_PATH = "/data/data/com.pagatodo.yaganaste/databases/";

    /**
     * Ejecuta una consulta a la BD y devuelve la lista de resultados
     *
     * @param database
     * @param sql
     * @param selectionArgs
     * @return
     */
    public static Cursor rawQuery(SQLiteDatabase database, String sql, String[] selectionArgs) {
        return database.rawQuery(sql, selectionArgs);
    }

    /**
     * Ejecuta una consulta a la BD  sin esperar resultados
     *
     * @param database
     * @param sql
     */
    public static void execSQL(SQLiteDatabase database, String sql) {
        database.execSQL(sql);
    }

    /**
     * @param database
     * @param table
     * @param values
     */
    public static void insert(SQLiteDatabase database, String table, ContentValues values) {
        database.insert(table, null, values);
        System.out.println("OK");
    }

    public static long insertMD(SQLiteDatabase database, String table, ContentValues values) {
        long rowId = 0;
        rowId = database.insert(table, null, values);
        return rowId;
    }

}
