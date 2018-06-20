package com.pagatodo.yaganaste.data.room_db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.pagatodo.yaganaste.data.room_db.entities.Operadores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

@Entity
public class Agentes implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "numero_agente")
    @NonNull
    private String NumeroAgente;

    @ColumnInfo(name = "es_comercio_uyu")
    private boolean EsComercioUYU;

    @ColumnInfo(name = "id_estatus")
    private int IdEstatus;

    @ColumnInfo(name = "nombre_negocio")
    private String NombreNegocio;

    @Ignore
    private List<Operadores> Operadores = new ArrayList<>();

    public int getIdEstatus() {
        return IdEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        IdEstatus = idEstatus;
    }

    public boolean isEsComercioUYU() {
        return EsComercioUYU;
    }

    public void setEsComercioUYU(boolean esComercioUYU) {
        EsComercioUYU = esComercioUYU;
    }

    public String getNombreNegocio() {
        return NombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        NombreNegocio = nombreNegocio;
    }

    public String getNumeroAgente() {
        return NumeroAgente;
    }

    public void setNumeroAgente(String numeroAgente) {
        NumeroAgente = numeroAgente;
    }

    public List<Operadores> getOperadores() {
        return Operadores;
    }

    public void setOperadores(List<Operadores> operadores) {
        Operadores = operadores;
    }
}
