package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by Armando Sandoval on 22/08/2017.
 */

public class EnviarCorreoContactanosRequest implements Serializable {

    private String MensajeCorreo = "";

    public EnviarCorreoContactanosRequest(String mensaje) {
        this.MensajeCorreo = mensaje;
    }

    public String getMensaje() {
        return MensajeCorreo;
    }

    public void setMensaje(String mensaje) {
        MensajeCorreo = mensaje;
    }
}
