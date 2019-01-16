package com.pagatodo.yaganaste.data.room_db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;

@Entity
public class Operadores implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "petro_numero")
    @NonNull
    private String PetroNumero;

    @ColumnInfo(name = "id_usuario")
    private int IdUsuario;

    @ColumnInfo(name = "numero_agente")
    private int NumeroAgente;

    @ColumnInfo(name = "id_usuario_adquiriente")
    private String IdUsuarioAdquirente;

    @ColumnInfo(name = "id_operador")
    private int IdOperador;

    @ColumnInfo(name = "is_admin")
    private boolean IsAdmin;

    @ColumnInfo(name = "nombre_usuario")
    private String NombreUsuario;

    @ColumnInfo(name = "estatus_usuario")
    private String EstatusUsuario;

    @ColumnInfo(name = "id_estatus_usuario")
    private int IdEstatusUsuario;

    public int getIdEstatusUsuario() {
        return IdEstatusUsuario;
    }

    public void setIdEstatusUsuario(int idEstatusUsuario) {
        IdEstatusUsuario = idEstatusUsuario;
    }

    public String getEstatusUsuario() {
        return EstatusUsuario;
    }

    public void setEstatusUsuario(String estatusUsuario) {
        EstatusUsuario = estatusUsuario;
    }

    public boolean getIsAdmin() {
        return IsAdmin;
    }

    public void setIsAdmin(boolean admin) {
        IsAdmin = admin;
    }

    public int getNumeroAgente() {
        return NumeroAgente;
    }

    public void setNumeroAgente(int numeroAgente) {
        NumeroAgente = numeroAgente;
    }

    public int getIdOperador() {
        return IdOperador;
    }

    public void setIdOperador(int idOperador) {
        IdOperador = idOperador;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getIdUsuarioAdquirente() {
        return IdUsuarioAdquirente;
    }

    public void setIdUsuarioAdquirente(String idUsuarioAdquirente) {
        IdUsuarioAdquirente = idUsuarioAdquirente;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getPetroNumero() {
        return PetroNumero;
    }

    public void setPetroNumero(String petroNumero) {
        PetroNumero = petroNumero;
    }

    /*
    * "IdOperador": 101,
                            "IdUsuario": 4451,
                            "IdUsuarioAdquirente": "15141",
                            "IsAdmin": true,
                            "NombreUsuario": "back103@gmail.com",
                            "PetroNumero": "26745001"*/
}
