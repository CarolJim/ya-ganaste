package com.pagatodo.yaganaste.data.room_db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pagatodo.yaganaste.data.room_db.entities.Comercio;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by ozunigag on 15/03/2018.
 */
@Dao
public interface ComercioDao {

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN 0 ELSE 1 END AS isEmpty FROM Comercio")
    boolean isTableEmpty();

    @Query("SELECT * FROM Comercio WHERE Comercio.id_tipo_comercio = :idTypeComercio ORDER BY Comercio.nombre_comercio ASC")
    List<Comercio> selectByIdType(int idTypeComercio);

    @Query("SELECT * FROM Comercio WHERE Comercio.id_comercio = :idComercio")
    Comercio selectById(int idComercio);

    @Query("SELECT Comercio.url_logo_color FROM Comercio WHERE Comercio.nombre_comercio = :nombreComercio")
    String selectUrlComercioByNombre(String nombreComercio);

    @Insert(onConflict = IGNORE)
    void insertComercios(List<Comercio> comercios);

    @Query("DELETE FROM Comercio")
    void deleteAll();
}
