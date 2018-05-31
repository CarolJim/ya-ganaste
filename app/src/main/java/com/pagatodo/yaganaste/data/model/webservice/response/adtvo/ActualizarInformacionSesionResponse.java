package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ActualizarInformacionSesionResponse extends GenericResponse {

    private DataIniciarSesionUYU Data;

    public ActualizarInformacionSesionResponse() {
        Data = new DataIniciarSesionUYU();
    }

    public DataIniciarSesionUYU getData() {
        return Data;
    }

    public void setData(DataIniciarSesionUYU data) {
        Data = data;
    }
}
