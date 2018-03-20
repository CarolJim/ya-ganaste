package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.google.gson.annotations.SerializedName;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class DataObtenerCatalogos implements Serializable {

    @SerializedName("Version")
    private String version = "";
    @SerializedName("ListaComercios")
    private List<Comercio> comercios;

    public DataObtenerCatalogos() {
        comercios = new ArrayList<Comercio>();
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Comercio> getComercios() {
        return comercios;
    }

    public void setComercios(List<Comercio> comercios) {
        this.comercios = comercios;
    }
}
