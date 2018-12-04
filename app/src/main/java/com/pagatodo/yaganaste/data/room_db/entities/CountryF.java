package com.pagatodo.yaganaste.data.room_db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CountryF  implements Serializable {

    @SerializedName("ID_Pais")
    @PrimaryKey
    private String ID_Pais;

    @SerializedName("IdPaisFirebase")
    @ColumnInfo(name = "IdPaisFirebase")
    private String IdPaisFirebase;

    @SerializedName("Nombre")
    @ColumnInfo(name = "Nombre")
    private String Nombre;

    @SerializedName("Valido")
    @ColumnInfo(name = "Valido")
    private boolean Valido;

    @SerializedName("Valor")
    @ColumnInfo(name = "Valor")
    private boolean Valor;
}
