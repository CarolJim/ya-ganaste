package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ValidarEstatusUsuarioResponse extends GenericResponse {

    private DataEstatusUsuario Data;

    public ValidarEstatusUsuarioResponse() {
        Data = new DataEstatusUsuario();
    }

    public DataEstatusUsuario getData() {
        return Data;
    }

    public void setData(DataEstatusUsuario data) {
        Data = data;
    }
}
