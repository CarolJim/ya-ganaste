package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

public class OperadoresResponse implements Serializable {

    private int IdOperador;
    private int IdUsuario;
    private String IdUsuarioAdquirente = "";
    private Boolean IsAdmin;
    private String NombreUsuario;
    private String PetroNumero;

    public OperadoresResponse() {
    }

    public int getIdOperador() {
        return IdOperador;
    }

    public void setIdOperador(int idOperador) {
        IdOperador = idOperador;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getIdUsuarioAdquirente() {
        return IdUsuarioAdquirente;
    }

    public void setIdUsuarioAdquirente(String idUsuarioAdquirente) {
        IdUsuarioAdquirente = idUsuarioAdquirente;
    }

    public Boolean getAdmin() {
        return IsAdmin;
    }

    public void setAdmin(Boolean admin) {
        IsAdmin = admin;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getPetroNumero() {
        return PetroNumero;
    }

    public void setPetroNumero(String petroNumero) {
        PetroNumero = petroNumero;
    }

    /*
    * "IdOperador": 101,
                            "IdUsuario": 4451,
                            "IdUsuarioAdquirente": "15141",
                            "IsAdmin": true,
                            "NombreUsuario": "back103@gmail.com",
                            "PetroNumero": "26745001"*/
}
