package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

class RolesResponse implements Serializable {
    private int IdRol;
    private String NombreRol = "";

    public int getIdRol() {
        return IdRol;
    }

    public void setIdRol(int idRol) {
        IdRol = idRol;
    }

    public String getNombreRol() {
        return NombreRol;
    }

    public void setNombreRol(String nombreRol) {
        NombreRol = nombreRol;
    }
}
