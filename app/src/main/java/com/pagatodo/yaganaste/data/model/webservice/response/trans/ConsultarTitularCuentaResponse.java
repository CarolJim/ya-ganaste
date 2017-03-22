package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ConsultarTitularCuentaResponse extends GenericResponse {

    private DataTitular Data;

    public ConsultarTitularCuentaResponse() {
        Data = new DataTitular();
    }

    public DataTitular getData() {
        return Data;
    }

    public void setData(DataTitular data) {
        Data = data;
    }
}
