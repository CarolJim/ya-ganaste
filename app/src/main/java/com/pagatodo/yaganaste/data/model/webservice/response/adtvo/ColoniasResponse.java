package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class ColoniasResponse implements Serializable{

    private String ColoniaId;
    private String Colonia = "";
    private String IdMunicipio = "";
    private String Municipio = "";
    private String IdEstado = "";
    private String Estado = "";



    public ColoniasResponse() {

    }

    public ColoniasResponse(String coloniaId, String colonia, String idMunicipio, String municipio, String idEstado, String estado) {
        ColoniaId = coloniaId;
        Colonia = colonia;
        IdMunicipio = idMunicipio;
        Municipio = municipio;
        IdEstado = idEstado;
        Estado = estado;
    }

    public String getColoniaId() {
        return ColoniaId;
    }

    public void setColoniaId(String coloniaId) {
        ColoniaId = coloniaId;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getIdMunicipio() {
        return IdMunicipio;
    }

    public void setIdMunicipio(String idMunicipio) {
        IdMunicipio = idMunicipio;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }

    public String getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(String idEstado) {
        IdEstado = idEstado;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
