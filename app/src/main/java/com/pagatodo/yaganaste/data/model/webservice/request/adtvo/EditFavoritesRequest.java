package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by Francisco Manzo on 25/09/2017.
 */

public class EditFavoritesRequest implements Serializable {

    private int IdTipoComercio = 0;
    private int idTipoEnvio = 0;
    private int IdComercio = 0;
    private String Nombre = "";
    private String Referencia = "";
    private String FotoEncode = "";

    public EditFavoritesRequest(int idTipoComercio, int idTipoEnvio, int idComercio, String mAlias,
                                String mReferencia, String FotoEncode) {
        this.IdTipoComercio = idTipoComercio;
        this.idTipoEnvio = idTipoEnvio;
        this.IdComercio = idComercio;
        this.Nombre = mAlias;
        this.Referencia = mReferencia;
        this.FotoEncode = FotoEncode;
    }

    public int getIdTipoComercio() {
        return IdTipoComercio;
    }

    public void setIdTipoComercio(int idTipoComercio) {
        IdTipoComercio = idTipoComercio;
    }

    public int getIdComercio() {
        return IdComercio;
    }

    public void setIdComercio(int idComercio) {
        IdComercio = idComercio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    public int getIdTipoEnvio() {
        return idTipoEnvio;
    }

    public void setIdTipoEnvio(int idTipoEnvio) {
        this.idTipoEnvio = idTipoEnvio;
    }

    public String getFotoEncode() {
        return FotoEncode;
    }

    public void setFotoEncode(String fotoEncode) {
        FotoEncode = fotoEncode;
    }
}