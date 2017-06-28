package com.pagatodo.yaganaste.data.local.persistence.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pagatodo.yaganaste.data.local.persistence.db.AbstractEntity;

import java.util.List;

import static com.pagatodo.yaganaste.data.local.persistence.db.utils.ReflectionUtils.cursorToEntity;
import static com.pagatodo.yaganaste.data.local.persistence.db.utils.ReflectionUtils.cursorToList;
import static com.pagatodo.yaganaste.data.local.persistence.db.utils.ReflectionUtils.getContentValues;
import static com.pagatodo.yaganaste.data.local.persistence.db.utils.ReflectionUtils.getTableName;
import static com.pagatodo.yaganaste.data.local.persistence.db.utils.ReflectionUtils.getUpdateWhere;

/**
 * @author Juan Guerra
 * @since 10/01/2017
 */

public class GenericDao extends AbstractDao {

    private GenericDao(@NonNull Context context) {
        super(context);
    }

    public static GenericDao getNewInstance(Context context) {
        return new GenericDao(context);
    }

    public <T extends AbstractEntity> int insert(@NonNull T toInsert) {
        return insert(getTableName(toInsert), getContentValues(toInsert));
    }

    /**
     * Regresa una lista de objetos de la entidad especificada
     *
     * @param classToReturn Objeto de tipo Class que denota el tipo de objeto que se desea obtener
     * @return Lista de elementos del tipo de objeto solicitado
     */
    public <T extends AbstractEntity> List<T> getAll(@NonNull Class<T> classToReturn) {
        return getListByQuery(classToReturn, null);
    }

    public <T extends AbstractEntity> T getByQuery(Class<T> classToReturn, String query) {
        T toReturn;
        Cursor queryResult = query(getTableName(classToReturn), query);
        toReturn = cursorToEntity(queryResult, classToReturn);
        return toReturn;
    }

    public <T extends AbstractEntity> List<T> getListByQuery(@NonNull Class<T> classToReturn, @Nullable String query) {
        List<T> toReturn;
        Cursor queryResult = query(getTableName(classToReturn), query);
        toReturn = cursorToList(queryResult, classToReturn);
        return toReturn;
    }

    public <T extends AbstractEntity> List<T>getListByQueryOrderBy(@NonNull Class<T> classToReturn, @Nullable String query, @Nullable String orderBy){
        List<T> toReturn;
        Cursor queryResult = query(getTableName(classToReturn), query, orderBy, null);
        toReturn = cursorToList(queryResult, classToReturn);
        return toReturn;
    }

    public <T extends AbstractEntity> void update(@NonNull T toUpdate) {
        update(getTableName(toUpdate), getContentValues(toUpdate), getUpdateWhere(toUpdate));
    }

    public <T extends AbstractEntity> void update(@NonNull T toUpdate, @Nullable String where) {
        update(getTableName(toUpdate), getContentValues(toUpdate), where);
    }

    @Deprecated
    public <T extends AbstractEntity> void delete(@NonNull T toDelete, @Nullable String where) {
        delete(getTableName(toDelete), where);
    }

    @Deprecated
    public <T extends AbstractEntity> void delete(@NonNull T toDelete) {
        delete(getTableName(toDelete), getUpdateWhere(toDelete));
    }

    @Deprecated
    public void deleteAll(@NonNull Class<? extends AbstractEntity> tableToDelete) {
        delete(getTableName(tableToDelete), null);
    }

    public <T extends AbstractEntity> List<T> getByPosition(@NonNull Class<T> where, @Nullable String whereClause, int from, int to) {
        List<T> toReturn;
        from -= 1;
        int limit = to - from;
        Cursor queryResult = rawQuery("SELECT * FROM " + getTableName(where) +
                (whereClause != null ? " WHERE " + whereClause : "") + " LIMIT " + limit + " OFFSET " + (from));
        toReturn = cursorToList(queryResult, where);
        return toReturn;
    }


    public boolean isEmpty(@NonNull Class<? extends AbstractEntity> where) {
        boolean empty = true;
        Cursor queryResponse = rawQuery("SELECT COUNT(*) FROM " + getTableName(where));
        if (queryResponse.moveToFirst()) {
            empty = queryResponse.getInt(0) == 0;
            queryResponse.close();
        }
        return empty;
    }

    public int getRowsCount(@NonNull Class<? extends AbstractEntity> from, @Nullable String where) {
        Cursor queryResponse = rawQuery("SELECT COUNT(*) FROM " + getTableName(from) +
                (where != null ? " WHERE " + where : ""));
        if (queryResponse != null) {
            if (queryResponse.moveToFirst()) {
                return queryResponse.getInt(0);
            }
            queryResponse.close();
        }
        return 0;
    }

    public long getMax(String query) {
        long max = 0;
        Cursor queryResponse = rawQuery(query);
        if (queryResponse != null && queryResponse.moveToFirst()) {
            max = queryResponse.getLong(0);
            queryResponse.close();
        }
        return max;
    }

}
