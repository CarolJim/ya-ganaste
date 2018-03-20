package com.pagatodo.yaganaste.data.room_db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ozuniga on 15/03/2017.
 */
@Entity
public class Paises implements Serializable {

    @SerializedName("Id")
    @PrimaryKey
    private int id;

    @SerializedName("IdPais")
    @ColumnInfo(name = "id_pais")
    private String idPais;

    @SerializedName("Descripcion")
    @ColumnInfo(name = "pais")
    private String pais;

    public Paises(int id, String pais, String idPais) {
        this.id = id;
        this.pais = pais;
        this.idPais = idPais;
    }

    @Ignore
    private Paises(Parcel parcel) {
        id = parcel.readInt();
        pais = parcel.readString();
        idPais = parcel.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdPais() {
        return idPais;
    }

    public void setIdPais(String idPais) {
        this.idPais = idPais;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
