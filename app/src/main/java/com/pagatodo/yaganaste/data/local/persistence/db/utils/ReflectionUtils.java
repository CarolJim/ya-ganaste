package com.pagatodo.yaganaste.data.local.persistence.db.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pagatodo.yaganaste.data.local.persistence.db.AbstractEntity;
import com.pagatodo.yaganaste.data.local.persistence.db.exceptions.AbstractEntityException;
import com.pagatodo.yaganaste.data.local.persistence.db.exceptions.NoEmptyConstructorException;
import com.pagatodo.yaganaste.data.local.persistence.db.exceptions.NoFieldFoundException;
import com.pagatodo.yaganaste.data.local.persistence.db.exceptions.NotExpectedException;
import com.pagatodo.yaganaste.data.local.persistence.db.logging.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Juan Guerra
 * @version 0.1
 * @since 10/01/2017.
 */

public class ReflectionUtils {

    public static <T extends AbstractEntity> ContentValues getContentValues(T entity) {
        ContentValues contentValues = new ContentValues();
        List<Field> fields = getCleanFields(entity.getClass());
        Map<String, Object> values = new HashMap<>();
        try {
            Field fValues = ContentValues.class.getDeclaredField("mValues");
            fValues.setAccessible(true);
            values = values.getClass().cast(fValues.get(contentValues));
            for (Field field : fields) {
                if (!isAutoIncrement(field)) {
                    values.put(getColumnName(field), getFieldValue(entity, field));
                }
            }
            fValues.set(contentValues, values);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Logger.throwException(new NotExpectedException(e));
        }
        return contentValues;
    }

    public static List<Field> getCleanFields(Class<? extends AbstractEntity> from) {
        Field[] fields = from.getDeclaredFields();
        List<Field> cleanFields = new ArrayList<>();
        for (Field field : fields) {
            if (!(field.isSynthetic() || Modifier.isStatic(field.getModifiers())) && field.getAnnotation(Ignore.class) == null) {
                cleanFields.add(field);
            }
        }
        Class parent = from.getSuperclass();
        if (AbstractEntity.class.isAssignableFrom(parent)) {
            cleanFields.addAll(getCleanFields(parent));
        }
        return cleanFields;
    }

    public static String getColumnName(Field columnField) {
        FieldName fieldName = columnField.getAnnotation(FieldName.class);
        return fieldName != null ? fieldName.value() : columnField.getName();
    }

    private static Object getFieldValue(Object entity, Field field) {
        field.setAccessible(true);
        try {
            Object value = field.get(entity);
            if (value != null) {
                return value;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTableName(Class<? extends AbstractEntity> element) {
        TableName tableName = element.getAnnotation(TableName.class);
        return tableName != null ? tableName.value() : element.getSimpleName();
    }

    public static <T extends AbstractEntity> String getTableName(T element) {
        return getTableName(element.getClass());
    }

    private static Map<String, Field> getEntityDBRelation(Class<? extends AbstractEntity> classToGet) {
        Map<String, Field> entityAttrs = new HashMap<>();
        FieldName fieldName;
        List<Field> fields = getCleanFields(classToGet);
        for (Field singleField : fields) {
            singleField.setAccessible(true);
            fieldName = singleField.getAnnotation(FieldName.class);
            entityAttrs.put(fieldName != null ? fieldName.value() : singleField.getName(), singleField);
        }
        return entityAttrs;
    }

    @NonNull
    private static <T extends AbstractEntity> Constructor<T> getConstructor(@NonNull Class<T> from) {
        try {
            return from.getConstructor();
        } catch (NoSuchMethodException e) {
            Logger.throwException(new NoEmptyConstructorException("La entidad" + from.getSimpleName()
                    + " debe tener un constructor publico vacio"));
            return null;

        }
    }

    private static <T extends AbstractEntity> T createInstance(Constructor<T> constructor) {
        if (Modifier.isAbstract(constructor.getDeclaringClass().getModifiers())) {
            Logger.throwException(new AbstractEntityException("La entidad " +
                    constructor.getDeclaringClass().getSimpleName() + " no puede ser Abstracta"));
        }
        try {
            return constructor.newInstance();
            //IllegalAccessException -> Nunca se presenta ya que el metodo getConstructor retorna un
            //constructor publico
            //IllegalArgumentException al pedir un constructor vacio no hay argumentos, esta
            // excepción nunca debe pasar
            //InstantiationException esta excepcion se trata en el if anterior, en su lugar se lanza
            //AbstractEntityException
            //InvocationTargetException al ser un constructor que no inicializa nada tampoco aplica
        } catch (Exception e) {
            Logger.throwException(new NotExpectedException(e));
        }
        return null;
    }

    public static <T extends AbstractEntity> List<T> cursorToList(Cursor from, @NonNull Class<T> toReturn) {
        List<T> listToReturn = new ArrayList<>();
        if (from.moveToFirst()) {
            String columns[] = from.getColumnNames();
            Map<String, Field> entityAttrs = getEntityDBRelation(toReturn);
            //validateEntityWithCursor(columns, entityAttrs);
            do {
                listToReturn.add(getFromCursor(from, entityAttrs, columns, createInstance(getConstructor(toReturn))));
            } while (from.moveToNext());
        }
        return listToReturn;
    }

    public static <T extends AbstractEntity> T cursorToEntity(Cursor from, Class<T> toReturn) {
        if (from.moveToFirst()) {
            String columns[] = from.getColumnNames();
            Map<String, Field> entityAttrs = getEntityDBRelation(toReturn);
            //validateEntityWithCursor(columns, entityAttrs);
            return getFromCursor(from, entityAttrs, columns, createInstance(getConstructor(toReturn)));
        }
        return null;
    }

    private static void validateEntityWithCursor(String[] columns, Map<String, Field> entityAttrs) {
        for (String currentColumn : columns) {
            if (!entityAttrs.containsKey(currentColumn)) {
                Logger.throwException(new NoFieldFoundException());
            }
        }
    }

    private static <T extends AbstractEntity> T getFromCursor(Cursor from, Map<String, Field> entityAttrs,
                                                              String columns[], T elementToFill) {
        for (String currentColumn : columns) {
            try {
                entityAttrs.get(currentColumn).set(elementToFill,
                        getValueFromCursor(entityAttrs.get(currentColumn), currentColumn, from));
            } catch (IllegalAccessException e) {
                Logger.throwException(new NotExpectedException(e));
            }
        }
        return elementToFill;
    }

    private static Object getValueFromCursor(Field toGet, String currentColumn, Cursor cursor) {
        String name = toGet.getType().getSimpleName();
        if (name.equals(int.class.getSimpleName()) || name.equals(Integer.class.getSimpleName())) {
            return cursor.getInt(cursor.getColumnIndex(currentColumn));
        } else if (name.equals(float.class.getSimpleName()) || name.equals(Float.class.getSimpleName())) {
            return cursor.getFloat(cursor.getColumnIndex(currentColumn));
        } else if (name.equals(double.class.getSimpleName()) || name.equals(Double.class.getSimpleName())) {
            return Double.valueOf(cursor.getString(cursor.getColumnIndex(currentColumn)));
        } else {
            return cursor.getString(cursor.getColumnIndex(currentColumn));
        }
    }

    public static String getSqliteType(Field toGet) {
        String name = toGet.getType().getSimpleName();
        if (name.equals(int.class.getSimpleName()) || name.equals(Integer.class.getSimpleName())) {
            return "INTEGER";
        } else if (name.equals(float.class.getSimpleName()) || name.equals(Float.class.getSimpleName())) {
            return "TEXT";
        } else if (name.equals(double.class.getSimpleName()) || name.equals(Double.class.getSimpleName())) {
            return "TEXT";
        } else {
            return "TEXT";
        }
    }

    private static boolean isAutoIncrement(Field columnField) {
        FieldName fieldName = columnField.getAnnotation(FieldName.class);
        return fieldName != null && fieldName.autoIncrement();
    }

    public static <T extends AbstractEntity> String getUpdateWhere(T from) {
        List<Field> fields = getCleanFields(from.getClass());
        for (Field field : fields) {
            if (isPrimaryKey(field)) {
                return getColumnName(field) + " = " + getFieldValue(from, field);

            }
        }
        return "";
    }

    private static boolean isPrimaryKey(Field field) {
        FieldName fieldName = field.getAnnotation(FieldName.class);
        return fieldName != null && fieldName.primaryKey();
    }

    public enum DB {
        CREAR_TABLA("CREATE TABLE IF NOT EXISTS "),
        BORRAR_TABLA("DROP TABLE IF EXISTS"),
        COMA(","),
        ESPACIO(" "),
        ABRIR_PARENTESIS("("),
        CERRAR_PARENTESIS(")"),
        TABLE("TABLE");
        private final String name;

        DB(String s) {
            name = s;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
