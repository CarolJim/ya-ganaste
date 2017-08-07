package com.pagatodo.yaganaste.data.model.db;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.pagatodo.yaganaste.data.local.persistence.db.AbstractEntity;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.FieldName;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.TableName;

import java.io.Serializable;

import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Paises.ID;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Paises.ID_PAIS;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Paises.PAIS;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Paises.TABLE;

/**
 * Created by Jordan on 28/07/2017.
 */
@TableName(TABLE)
public class Countries extends AbstractEntity implements Serializable {

    @SerializedName("Id")
    @FieldName(value = ID, primaryKey = true)
    private int id;

    @SerializedName("IdPais")
    @FieldName(ID_PAIS)
    private String idPais;

    @SerializedName("Descripcion")
    @FieldName(PAIS)
    private String pais;

    public Countries() {
    }

    public Countries(int id, String pais, String idPais) {
        this.id = id;
        this.pais = pais;
        this.idPais = idPais;
    }

    private Countries(Parcel parcel) {
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

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(idPais);
        parcel.writeString(pais);
        parcel.writeInt(id);
    }

    public static final Parcelable.Creator<Countries> CREATOR = new Creator<Countries>() {
        @Override
        public Countries createFromParcel(Parcel source) {
            return new Countries(source);
        }

        @Override
        public Countries[] newArray(int size) {
            return new Countries[size];
        }
    }*/
}
