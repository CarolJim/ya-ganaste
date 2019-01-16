package com.pagatodo.yaganaste.data.room_db.entities;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asandovals on 20/04/2018.
 */

public class Rewards implements Serializable {

    @SerializedName("descripcion")
    @ColumnInfo(name = "descripcion")
    private String descripcion;
    @SerializedName("idBeneficio")
    @ColumnInfo(name = "idBeneficio")
    private int idBeneficio;
    public Rewards() {

    }
    public Rewards(String descripcion, int idBeneficio) {
        this.descripcion = descripcion;
        this.idBeneficio = idBeneficio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdBeneficio() {
        return idBeneficio;
    }

    public void setIdBeneficio(int idBeneficio) {
        this.idBeneficio = idBeneficio;
    }
}
