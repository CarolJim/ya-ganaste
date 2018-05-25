package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ActualizarInformacionSesionResponse extends GenericResponse {

    private DataIniciarSesion Data;

    public ActualizarInformacionSesionResponse() {
        Data = new DataIniciarSesion();
    }

    public DataIniciarSesion getData() {
        return Data;
    }

    public void setData(DataIniciarSesion data) {
        Data = data;
    }
}
