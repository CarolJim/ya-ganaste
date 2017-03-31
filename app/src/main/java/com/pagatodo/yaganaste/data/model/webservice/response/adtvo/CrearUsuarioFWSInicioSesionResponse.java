package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class CrearUsuarioFWSInicioSesionResponse extends GenericResponse {

    private DataUsuarioFWSInicioSesion Data;

    public CrearUsuarioFWSInicioSesionResponse() {
        Data = new DataUsuarioFWSInicioSesion();
    }

    public DataUsuarioFWSInicioSesion getData() {
        return Data;
    }

    public void setData(DataUsuarioFWSInicioSesion data) {
        Data = data;
    }
}
