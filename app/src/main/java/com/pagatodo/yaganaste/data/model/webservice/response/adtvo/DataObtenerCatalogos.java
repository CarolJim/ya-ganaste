package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class DataObtenerCatalogos implements Serializable {

    private String Version ="";
    private List<ComercioResponse> Comercios;

    public DataObtenerCatalogos() {
        Comercios = new ArrayList<ComercioResponse>();
    }


    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public List<ComercioResponse> getComercios() {
        return Comercios;
    }

    public void setComercios(List<ComercioResponse> comercios) {
        Comercios = comercios;
    }
}
