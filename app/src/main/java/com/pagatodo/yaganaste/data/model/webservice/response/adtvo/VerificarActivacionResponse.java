package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class VerificarActivacionResponse extends GenericResponse {

    private DataActivacion Data;

    public VerificarActivacionResponse() {
        Data = new DataActivacion();
    }

    public DataActivacion getData() {
        return Data;
    }

    public void setData(DataActivacion data) {
        Data = data;
    }
}
