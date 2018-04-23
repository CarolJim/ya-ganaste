package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

public class ResponseGenericSb implements Serializable {

    private int codigoRespuesta;
    private String mensaje = "";

    public ResponseGenericSb(){

    }

    public ResponseGenericSb(int codigoRespuesta, String mensaje) {
        this.codigoRespuesta = codigoRespuesta;
        this.mensaje = mensaje;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
