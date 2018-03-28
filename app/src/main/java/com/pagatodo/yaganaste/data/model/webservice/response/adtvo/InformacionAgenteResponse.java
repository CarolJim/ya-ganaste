package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

public class InformacionAgenteResponse extends GenericResponse {

    DataInfoAgente Data;

    public InformacionAgenteResponse() {
        Data = new DataInfoAgente();
    }

    public DataInfoAgente getData() {
        return Data;
    }

    public void setData(DataInfoAgente data) {
        Data = data;
    }
}
