package com.pagatodo.yaganaste.data.model.db;

import com.google.gson.annotations.SerializedName;
import com.pagatodo.yaganaste.data.local.persistence.db.AbstractEntity;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.FieldName;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.TableName;

import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Paises.ID;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Paises.ID_PAIS;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Paises.PAIS;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Paises.TABLE;

/**
 * Created by Jordan on 28/07/2017.
 */
@TableName(TABLE)
public class Countries extends AbstractEntity {

    @SerializedName("Id")
    @FieldName(value = ID, primaryKey = true)
    private int id;

    @SerializedName("IdPais")
    @FieldName(ID_PAIS)
    private String idPais;

    @SerializedName("Descripcion")
    @FieldName(PAIS)
    private String pais;

    public Countries(){}

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
