package com.pagatodo.yaganaste.data.room_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

/**
 * Created by ozunigag on 16/03/2018.
 */
@Dao
public interface FavoritosDao {

    @Insert(onConflict = IGNORE)
    void insertListFavorites(List<Favoritos> listFavoritos);

    @Insert(onConflict = IGNORE)
    void insertFavorite(Favoritos favorito);

    @Query("SELECT * FROM Favoritos WHERE Favoritos.id_tipo_comercio = :idTypeComercio ORDER BY Favoritos.id_favorito ASC")
    List<Favoritos> selectFavoritesByTypeComercio(int idTypeComercio);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS isEmpty FROM Favoritos WHERE " +
            "Favoritos.referencia = :referencia AND Favoritos.id_comercio = :idComercio")
    boolean favoriteExists(String referencia, int idComercio);

    @Query("DELETE FROM Favoritos")
    void deleteAll();

    @Query("DELETE FROM Favoritos WHERE Favoritos.id_favorito = :idFavorito")
    void deleteById(int idFavorito);
}
