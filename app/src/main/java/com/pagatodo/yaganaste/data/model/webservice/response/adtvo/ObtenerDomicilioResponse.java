package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerDomicilioResponse extends GenericResponse {

    private DataObtenerDomicilio Data;

    public ObtenerDomicilioResponse() {
        Data = new DataObtenerDomicilio();
    }

    public DataObtenerDomicilio getData() {
        return Data;
    }

    public void setData(DataObtenerDomicilio data) {
        Data = data;
    }
}
