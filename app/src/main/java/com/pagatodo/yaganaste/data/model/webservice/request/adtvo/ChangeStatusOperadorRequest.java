package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChangeStatusOperadorRequest implements Serializable {

    @SerializedName("NombreUsuario")
    private String NombreUsuario;

    @SerializedName("IdEstatus")
    private int IdEstatus;


    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public int getIdEstatus() {
        return IdEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        IdEstatus = idEstatus;
    }
}
