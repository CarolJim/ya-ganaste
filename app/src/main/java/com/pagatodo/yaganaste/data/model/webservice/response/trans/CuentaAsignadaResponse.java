package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataActivacion;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class CuentaAsignadaResponse extends GenericResponse {

    private DataActivacion Data;

    public CuentaAsignadaResponse() {
        Data = new DataActivacion();
    }

    public DataActivacion getData() {
        return Data;
    }

    public void setData(DataActivacion data) {
        Data = data;
    }
}
