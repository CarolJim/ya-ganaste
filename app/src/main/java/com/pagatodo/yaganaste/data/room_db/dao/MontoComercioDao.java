package com.pagatodo.yaganaste.data.room_db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pagatodo.yaganaste.data.room_db.entities.MontoComercio;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by ozunigag on 15/03/2018.
 */
@Dao
public interface MontoComercioDao {

    @Insert(onConflict = IGNORE)
    void insertMonto(MontoComercio montoComercio);

    @Query("SELECT * FROM MontoComercio WHERE MontoComercio.id_comercio=:idComercio")
    List<MontoComercio> selectMontosByIdComercio(int idComercio);

    @Query("DELETE FROM MontoComercio")
    void deleteAll();
}
