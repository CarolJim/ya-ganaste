package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ConsultarMovimientosMesResponse extends GenericResponse {

    private DataConsultarMovimientos Data;

    public ConsultarMovimientosMesResponse() {
        Data = new DataConsultarMovimientos();
    }

    public DataConsultarMovimientos getData() {
        return Data;
    }

    public void setData(DataConsultarMovimientos data) {
        Data = data;
    }
}
