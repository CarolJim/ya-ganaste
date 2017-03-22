package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ConsultarSaldoResponse extends GenericResponse {

    private DataSaldo Data;

    public ConsultarSaldoResponse() {
        Data = new DataSaldo();
    }

    public DataSaldo getData() {
        return Data;
    }

    public void setData(DataSaldo data) {
        Data = data;
    }
}
