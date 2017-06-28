package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.google.gson.annotations.SerializedName;

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
    private List<ComercioResponse> comercios;

    public DataObtenerCatalogos() {
        comercios = new ArrayList<ComercioResponse>();
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<ComercioResponse> getComercios() {
        return comercios;
    }

    public void setComercios(List<ComercioResponse> comercios) {
        this.comercios = comercios;
    }
}
