package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ConsultarAsignacionTarjetaResponse extends GenericResponse {

    private DataConsultarAsignacion Data;

    public ConsultarAsignacionTarjetaResponse() {
        Data = new DataConsultarAsignacion();
    }

    public DataConsultarAsignacion getData() {
        return Data;
    }

    public void setData(DataConsultarAsignacion data) {
        Data = data;
    }
}
