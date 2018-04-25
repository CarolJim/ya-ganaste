package com.pagatodo.yaganaste.data.model.webservice.response.starbucks;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asandovals on 25/04/2018.
 */

public class PreRegisterStarbucksRespónse {

    @SerializedName("codigo")
    private int codigo;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("idPreregistro")
    private String idPreregistro;


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdPreregistro() {
        return idPreregistro;
    }

    public void setIdPreregistro(String idPreregistro) {
        this.idPreregistro = idPreregistro;
    }
}
