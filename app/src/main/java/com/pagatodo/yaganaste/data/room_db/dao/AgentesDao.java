package com.pagatodo.yaganaste.data.room_db.dao;

import androidx.room.Dao;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;

import com.pagatodo.yaganaste.data.room_db.entities.Agentes;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface AgentesDao {

    @Query("SELECT * FROM Agentes")
    List<Agentes> selectAgentes();

    @Query("SELECT * FROM Agentes WHERE id_comercio=:idComercio")
    Agentes getAgenteByComercio(int idComercio);

    @Query("SELECT DISTINCT es_comercio_uyu FROM Agentes INNER JOIN Operadores" +
            " ON Agentes.numero_agente = Operadores.numero_agente AND Operadores.id_usuario_adquiriente=:idUsuarioAdq")
    boolean esComercioUyU(String idUsuarioAdq);

    @Query("SELECT DISTINCT es_agregador FROM Agentes INNER JOIN Operadores" +
            " ON Agentes.numero_agente = Operadores.numero_agente AND Operadores.id_usuario_adquiriente=:idUsuarioAdq")
    boolean esAgregador(String idUsuarioAdq);

    @Query("SELECT DISTINCT id_estatus FROM Agentes WHERE numero_agente=:numAgente")
    int getIdEstatusAgente(String numAgente);

    @Query("SELECT DISTINCT folio FROM Agentes WHERE id_comercio=:idComercio")
    String getFolioAgente(String idComercio);

    @Insert(onConflict = IGNORE)
    void insertAgentes(List<Agentes> agentes);

    @Query("DELETE FROM Agentes")
    void deleteAll();

    @Query("UPDATE Agentes SET id_estatus =:status WHERE folio=:folioadq")
    void updateStatus(int status,String folioadq );
}
