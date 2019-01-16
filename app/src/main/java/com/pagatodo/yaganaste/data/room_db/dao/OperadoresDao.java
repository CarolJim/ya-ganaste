package com.pagatodo.yaganaste.data.room_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pagatodo.yaganaste.data.room_db.entities.Operadores;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface OperadoresDao {

    @Query("SELECT * FROM Operadores WHERE numero_agente=:numeroAgente")
    List<Operadores> getOperadoresByAgente(String numeroAgente);

    @Query("SELECT * FROM Operadores WHERE numero_agente=:numeroAgente AND is_admin = 1")
    Operadores getOperadorAdminByAgente(String numeroAgente);

    @Query("SELECT id_usuario_adquiriente FROM Operadores LIMIT 1")
    int getIdUsuarioAdquirienteRolOperador();

    @Query("SELECT id_usuario_adquiriente FROM Operadores WHERE numero_agente=:numeroAgente AND is_admin = 1")
    int getIdUsuarioAdquirienteByAgente(String numeroAgente);

    @Insert(onConflict = IGNORE)
    void insertOperador(Operadores operadores);

    @Query("DELETE FROM Operadores")
    void deleteAll();

    @Query("DELETE FROM Operadores WHERE numero_agente=:numeroAgente" )
    void deleteByAgente(String numeroAgente);
}

