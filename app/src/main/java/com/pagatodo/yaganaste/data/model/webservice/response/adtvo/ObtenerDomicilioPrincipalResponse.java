package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerDomicilioPrincipalResponse extends GenericResponse {

    private DataDireccion Data;

    public ObtenerDomicilioPrincipalResponse() {
        Data = new DataDireccion();
    }

    public DataDireccion getData() {
        return Data;
    }

    public void setData(DataDireccion data) {
        Data = data;
    }
}
