package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class CambiarContraseniaRequest implements Serializable{

    private String ContrasenaActual = "";
    private String ContrasenaNueva = "";

    public CambiarContraseniaRequest() {
    }

    public String getContrasenaActual() {
        return ContrasenaActual;
    }

    public void setContrasenaActual(String contrasenaActual) {
        ContrasenaActual = contrasenaActual;
    }

    public String getContrasenaNueva() {
        return ContrasenaNueva;
    }

    public void setContrasenaNueva(String contrasenaNueva) {
        ContrasenaNueva = contrasenaNueva;
    }
}
