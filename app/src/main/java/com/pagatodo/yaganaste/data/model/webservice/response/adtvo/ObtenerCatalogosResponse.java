package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerCatalogosResponse extends GenericResponse {

    private DataObtenerCatalogos Data;

    public ObtenerCatalogosResponse() {
        Data = new DataObtenerCatalogos();
    }

    public DataObtenerCatalogos getData() {
        return Data;
    }

    public void setData(DataObtenerCatalogos data) {
        Data = data;
    }
}
