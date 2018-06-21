package com.pagatodo.yaganaste.data.room_db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pagatodo.yaganaste.data.room_db.entities.Agentes;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface AgentesDao {

    @Query("SELECT * FROM Agentes")
    List<Agentes> selectAgentes();

    @Query("SELECT DISTINCT es_comercio_uyu FROM Agentes INNER JOIN Operadores" +
            " ON Agentes.numero_agente = Operadores.numero_agente AND Operadores.id_usuario_adquiriente=:idUsuarioAdq")
    boolean esComercioUyU(String idUsuarioAdq);

    @Query("SELECT DISTINCT id_estatus FROM Agentes WHERE numero_agente=:numAgente")
    int getIdEstatusAgente(String numAgente);

    @Insert(onConflict = IGNORE)
    void insertAgentes(List<Agentes> agentes);

    @Query("DELETE FROM Agentes")
    void deleteAll();

}
