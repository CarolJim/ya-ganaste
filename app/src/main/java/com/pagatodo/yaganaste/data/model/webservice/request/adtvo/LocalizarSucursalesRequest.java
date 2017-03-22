package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class LocalizarSucursalesRequest implements Serializable{

    private Double Latitud;
    private Double Longitud;
    private String Consulta = "";

    public LocalizarSucursalesRequest() {
    }

    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double latitud) {
        Latitud = latitud;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
        Longitud = longitud;
    }

    public String getConsulta() {
        return Consulta;
    }

    public void setConsulta(String consulta) {
        Consulta = consulta;
    }
}
