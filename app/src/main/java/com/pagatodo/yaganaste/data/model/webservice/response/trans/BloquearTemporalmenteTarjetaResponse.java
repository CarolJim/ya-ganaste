package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class BloquearTemporalmenteTarjetaResponse extends GenericResponse {

    private DataBloquearTarjeta Data;

    public BloquearTemporalmenteTarjetaResponse() {
        Data = new DataBloquearTarjeta();
    }

    public DataBloquearTarjeta getData() {
        return Data;
    }

    public void setData(DataBloquearTarjeta data) {
        Data = data;
    }
}
