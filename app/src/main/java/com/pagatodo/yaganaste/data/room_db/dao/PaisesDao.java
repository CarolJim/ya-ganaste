package com.pagatodo.yaganaste.data.room_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pagatodo.yaganaste.data.room_db.entities.Paises;

import java.util.ArrayList;
import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

/**
 * Created by ozunigag on 15/03/2018.
 */
@Dao
public interface PaisesDao {

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN 0 ELSE 1 END AS isEmpty FROM Paises")
    boolean isTableEmpty();

    @Insert(onConflict = IGNORE)
    void insertPaises(List<Paises> paises);

    @Query("SELECT * FROM Paises ORDER BY Paises.pais ASC")
    List<Paises> getListPaisesOrdered();
}
