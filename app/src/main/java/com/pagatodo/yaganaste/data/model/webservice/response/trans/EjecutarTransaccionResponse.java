package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class EjecutarTransaccionResponse extends GenericResponse {

    private DataTransaccion Data;

    public EjecutarTransaccionResponse() {
        Data = new DataTransaccion();
    }

    public DataTransaccion getData() {
        return Data;
    }

    public void setData(DataTransaccion data) {
        Data = data;
    }
}
