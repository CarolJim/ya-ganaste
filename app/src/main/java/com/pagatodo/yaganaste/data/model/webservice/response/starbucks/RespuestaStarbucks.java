package com.pagatodo.yaganaste.data.model.webservice.response.starbucks;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asandovals on 20/04/2018.
 */

public class RespuestaStarbucks implements Serializable {

    private int codigoRespuesta ;
    private String mensaje ;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }


}
