package com.pagatodo.yaganaste.data.local.persistence.db.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.pagatodo.yaganaste.data.local.persistence.db.AbstractEntity;
import com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract;
import com.pagatodo.yaganaste.data.local.persistence.db.exceptions.NoMappedClassException;
import com.pagatodo.yaganaste.data.local.persistence.db.logging.Logger;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.FieldName;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.MappedFrom;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.ReflectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

import static com.pagatodo.yaganaste.data.local.persistence.db.utils.ReflectionUtils.DB.ABRIR_PARENTESIS;
import static com.pagatodo.yaganaste.data.local.persistence.db.utils.ReflectionUtils.DB.BORRAR_TABLA;
import static com.pagatodo.yaganaste.data.local.persistence.db.utils.ReflectionUtils.DB.CERRAR_PARENTESIS;
import static com.pagatodo.yaganaste.data.local.persistence.db.utils.ReflectionUtils.DB.COMA;
import static com.pagatodo.yaganaste.data.local.persistence.db.utils.ReflectionUtils.DB.CREAR_TABLA;
import static com.pagatodo.yaganaste.data.local.persistence.db.utils.ReflectionUtils.DB.ESPACIO;

/**
 * Created by Jordan on 21/04/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "yaganaste.db";

    private Context context;


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Log.d("SQLiteHelper", "Creando Helper");
    }

    public void createTables() {
        createTables(SQLiteManager.getInstance().openDataBase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    public void createTables(SQLiteDatabase sqLiteDatabase) {
        Class currentClass = DBContract.class;
        Class[] classes = currentClass.getClasses();
        Class<? extends AbstractEntity> mapped;
        MappedFrom annotation;
        for (Class<?> c : classes) {
            if (!c.getSimpleName().equals(DBContract.DBTable.class.getSimpleName())) {
                annotation = c.getAnnotation(MappedFrom.class);
                if (annotation == null) {
                    Logger.throwException(new NoMappedClassException(c.getSimpleName()));
                }
                mapped = annotation.value();
                sqLiteDatabase.execSQL(getQuery(mapped));
            }
        }
    }

    private String getQuery(Class<? extends AbstractEntity> currentClass) {
        StringBuilder query = new StringBuilder();
        query.append(CREAR_TABLA.toString()).append(ESPACIO.toString());
        List<Field> classFields = ReflectionUtils.getCleanFields(currentClass);
        query.append(ReflectionUtils.getTableName(currentClass)).append(ESPACIO.toString())
                .append(ABRIR_PARENTESIS.toString());

        boolean isPrimaryKey;
        boolean isAutoIncrement;
        FieldName fieldName;
        for (Field f : classFields) {
            fieldName = f.getAnnotation(FieldName.class);
            isPrimaryKey = fieldName != null && fieldName.primaryKey();
            isAutoIncrement = fieldName != null && fieldName.autoIncrement();
            query.append(ESPACIO.toString()).append(ReflectionUtils.getColumnName(f)).
                    append(ESPACIO.toString())
                    .append(ReflectionUtils.getSqliteType(f))
                    .append(isPrimaryKey ? " PRIMARY KEY" : "")
                    .append(isAutoIncrement ? " AUTOINCREMENT" : "")
                    .append(COMA.toString());
        }

        int n = query.lastIndexOf(COMA.toString());
        if (query.lastIndexOf(COMA.toString()) == query.length() - 1) {
            query.deleteCharAt(n);
        }
        query.append(ESPACIO.toString()).append(CERRAR_PARENTESIS.toString());
        System.out.println("-" + query.toString());
        System.out.println("******************************************");
        return query.toString();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Class currentClass = DBContract.class;
        Class[] classes = currentClass.getClasses();
        StringBuilder query;
        Class<? extends AbstractEntity> mapped;
        MappedFrom annotation;
        for (Class<?> c : classes) {
            if (!c.getSimpleName().equals(DBContract.DBTable.class.getSimpleName())) {
                annotation = c.getAnnotation(MappedFrom.class);
                if (annotation == null) {
                    Logger.throwException(new NoMappedClassException(c.getSimpleName()));
                }
                mapped = annotation.value();
                query = new StringBuilder();
                query.append(BORRAR_TABLA.toString()).append(ESPACIO.toString())
                        .append(ReflectionUtils.getTableName(mapped));
                db.execSQL(query.toString());
            }
        }
        onCreate(db);
    }


    public void copyDataBaseDebug() throws IOException {
        Log.i("copiado", "inicia..");
        // abre la BD local
        InputStream myInput = new FileInputStream(new File(
                context.getDatabasePath(DATABASE_NAME).toURI())); // context.getAssets().open("db/"+DB_NAME);

        // Direccion de la
        String outFileName = Environment.getExternalStorageDirectory()
                + "/.YaGanaste/" + DATABASE_NAME; // DataBaseUtilsB.DB_PATH +
        // DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.i("copiado", "termina..");
    }
}
