package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class CrearUsuarioClienteResponse extends GenericResponse {

    private DataUsuarioCliente Data;

    public CrearUsuarioClienteResponse() {
        Data = new DataUsuarioCliente();
    }

    public DataUsuarioCliente getData() {
        return Data;
    }

    public void setData(DataUsuarioCliente data) {
        Data = data;
    }
}
