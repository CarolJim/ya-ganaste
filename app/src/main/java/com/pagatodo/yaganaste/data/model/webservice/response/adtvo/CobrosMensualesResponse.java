package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by icruz on 07/11/2017.
 */

public class CobrosMensualesResponse implements Serializable {

    private int IdSppiner;
    private String Descripcion = "";
    private String IdOperacion = "";
    private String IdDestinoRecurso = "";
    private String IdOrigenRecurso = "";
    private String IdPais = "";
    private String Clave = "";

    public CobrosMensualesResponse(){

    }

    public CobrosMensualesResponse(String descripcion, String idOperacion) {
        Descripcion = descripcion;
        IdOperacion = idOperacion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getIdOperacion() {
        return IdOperacion;
    }

    public void setIdOperacion(String idOperacion) {
        IdOperacion = idOperacion;
    }

    public String getIdDestinoRecurso() {
        return IdDestinoRecurso;
    }

    public void setIdDestinoRecurso(String idDestinoRecurso) {
        IdDestinoRecurso = idDestinoRecurso;
    }

    public String getIdOrigenRecurso() {
        return IdOrigenRecurso;
    }

    public void setIdOrigenRecurso(String idOrigenRecurso) {
        IdOrigenRecurso = idOrigenRecurso;
    }

    public String getIdPais() {
        return IdPais;
    }

    public void setIdPais(String idPais) {
        IdPais = idPais;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }
}
