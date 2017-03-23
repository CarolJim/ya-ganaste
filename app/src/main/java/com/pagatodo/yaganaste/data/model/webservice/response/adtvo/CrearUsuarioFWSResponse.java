package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class CrearUsuarioFWSResponse extends GenericResponse {

    private DataUsuarioFWS Data;

    public CrearUsuarioFWSResponse() {
        Data = new DataUsuarioFWS();
    }

    public DataUsuarioFWS getData() {
        return Data;
    }

    public void setData(DataUsuarioFWS data) {
        Data = data;
    }
}
