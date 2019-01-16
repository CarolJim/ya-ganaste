package com.pagatodo.yaganaste.data.room_db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
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

    @SerializedName("ID_Pais")
    @ColumnInfo(name = "id_pais")
    private String idPais;

    @SerializedName("IdPaisFirebase")
    @ColumnInfo(name = "id_pais_firebase")
    private String idPaisFirebase;

    @SerializedName("Nombre")
    @ColumnInfo(name = "pais")
    private String pais;

    @SerializedName("Valido")
    @ColumnInfo(name = "valido")
    private boolean valido;

    @SerializedName("Valor")
    @ColumnInfo(name = "valor")
    private String valor;



    public Paises(int id, String pais, String idPais) {
        this.id = id;
        this.pais = pais;
        this.idPais = idPais;
    }



    @Ignore
    public Paises(Parcel parcel) {
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

    public String getIdPaisFirebase() {
        return idPaisFirebase;
    }

    public void setIdPaisFirebase(String idPaisFirebase) {
        this.idPaisFirebase = idPaisFirebase;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
