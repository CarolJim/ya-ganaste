package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

public class IniciarSesionResponse extends GenericResponse {

    private DataIniciarSesion Data;

    public IniciarSesionResponse() {
        Data = new DataIniciarSesion();
    }

    public DataIniciarSesion getData() {
        return Data;
    }

    public void setData(DataIniciarSesion data) {
        Data = data;
    }
}
