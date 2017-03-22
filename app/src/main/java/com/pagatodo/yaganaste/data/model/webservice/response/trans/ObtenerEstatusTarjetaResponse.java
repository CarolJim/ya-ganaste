package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerEstatusTarjetaResponse extends GenericResponse {

    private DataEstatusTarjeta Data;

    public ObtenerEstatusTarjetaResponse() {
        Data = new DataEstatusTarjeta();
    }

    public DataEstatusTarjeta getData() {
        return Data;
    }

    public void setData(DataEstatusTarjeta data) {
        Data = data;
    }
}
