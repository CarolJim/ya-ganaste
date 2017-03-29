package com.pagatodo.yaganaste.data.model.webservice.response.manager;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class GenericResponse implements Serializable {

    private int IdOperacion;
    private int CodigoRespuesta;
    private int Accion;
    private String Mensaje = "";

    public GenericResponse() {
    }

    public GenericResponse(int idOperacion, String mensaje) {
        IdOperacion = idOperacion;
        Mensaje = mensaje;
    }

    public int getIdOperacion() {
        return IdOperacion;
    }

    public void setIdOperacion(int idOperacion) {
        IdOperacion = idOperacion;
    }

    public int getCodigoRespuesta() {
        return CodigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        CodigoRespuesta = codigoRespuesta;
    }

    public int getAccion() {
        return Accion;
    }

    public void setAccion(int accion) {
        Accion = accion;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }
}
