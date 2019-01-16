package com.pagatodo.yaganaste.data.room_db;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import android.content.Context;

import com.pagatodo.yaganaste.data.room_db.dao.AgentesDao;
import com.pagatodo.yaganaste.data.room_db.dao.ComercioDao;
import com.pagatodo.yaganaste.data.room_db.dao.FavoritosDao;
import com.pagatodo.yaganaste.data.room_db.dao.MontoComercioDao;
import com.pagatodo.yaganaste.data.room_db.dao.OperadoresDao;
import com.pagatodo.yaganaste.data.room_db.dao.PaisesDao;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.data.room_db.entities.MontoComercio;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.utils.Recursos;

/**
 * Created by ozunigag on 15/03/2018.
 */
@Database(entities = {Comercio.class, Favoritos.class, MontoComercio.class,
        Paises.class, Agentes.class, Operadores.class}, version = Recursos.DATABASE_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract PaisesDao paisesModel();

    public abstract ComercioDao comercioModel();

    public abstract MontoComercioDao montoComercioModel();

    public abstract FavoritosDao favoritosModel();

    public abstract AgentesDao agentesModel();

    public abstract OperadoresDao operadoresModel();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Recursos.DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build();
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `Agentes` (`numero_agente` TEXT NOT NULL, `es_comercio_uyu` INTEGER NOT NULL, `folio` TEXT, `id_comercio` INTEGER NOT NULL, `id_estatus` INTEGER NOT NULL, `nombre_negocio` TEXT, PRIMARY KEY(`numero_agente`))");
            database.execSQL("CREATE TABLE `Operadores` (`id_usuario` INTEGER NOT NULL, `numero_agente` INTEGER NOT NULL, `id_usuario_adquiriente` TEXT, `id_operador` INTEGER NOT NULL, `is_admin` INTEGER NOT NULL," +
                    "`nombre_usuario` TEXT, `petro_numero` TEXT, `estatus_usuario` TEXT, `id_estatus_usuario` INTEGER NOT NULL, PRIMARY KEY(`id_usuario`))");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS `Operadores`");
            database.execSQL("CREATE TABLE `Operadores` (`id_usuario` INTEGER NOT NULL, `numero_agente` INTEGER NOT NULL, `id_usuario_adquiriente` TEXT, `id_operador` INTEGER NOT NULL, `is_admin` INTEGER NOT NULL," +
                    "`nombre_usuario` TEXT, `petro_numero` TEXT NOT NULL, `estatus_usuario` TEXT, `id_estatus_usuario` INTEGER NOT NULL, PRIMARY KEY(`petro_numero`))");
        }
    };
}
