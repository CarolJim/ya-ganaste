package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Armando Sandoval on 18/01/2018.
 */

public class DataObtenerDataBin implements Serializable {
    @SerializedName("IdComercioAfectado")
    private String IdComercioAfectado;
    @SerializedName("Nombre")
    private String Nombre;

    public String getIdComercioAfectado() {
        return IdComercioAfectado;
    }

    public void setIdComercioAfectado(String idComercioAfectado) {
        IdComercioAfectado = idComercioAfectado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
