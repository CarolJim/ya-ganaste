package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

public class IniciarSesionUYUResponse extends GenericResponse {

    private DataIniciarSesionUYU Data;

    public IniciarSesionUYUResponse() {
        Data = new DataIniciarSesionUYU();
    }

    public DataIniciarSesionUYU getData() {
        return Data;
    }

    public void setData(DataIniciarSesionUYU data) {
        Data = data;
    }
}
