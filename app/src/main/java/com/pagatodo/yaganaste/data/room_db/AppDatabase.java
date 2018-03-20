package com.pagatodo.yaganaste.data.room_db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.pagatodo.yaganaste.data.room_db.dao.ComercioDao;
import com.pagatodo.yaganaste.data.room_db.dao.FavoritosDao;
import com.pagatodo.yaganaste.data.room_db.dao.MontoComercioDao;
import com.pagatodo.yaganaste.data.room_db.dao.PaisesDao;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.data.room_db.entities.MontoComercio;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.utils.Recursos;

/**
 * Created by ozunigag on 15/03/2018.
 */
@Database(entities = {Comercio.class, Favoritos.class, MontoComercio.class,
        Paises.class}, version = Recursos.DATABASE_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract PaisesDao paisesModel();

    public abstract ComercioDao comercioModel();

    public abstract MontoComercioDao montoComercioModel();

    public abstract FavoritosDao favoritosModel();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Recursos.DATABASE_NAME).build();
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
