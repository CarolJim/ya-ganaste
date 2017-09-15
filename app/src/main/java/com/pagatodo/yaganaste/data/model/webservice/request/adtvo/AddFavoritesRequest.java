package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class AddFavoritesRequest implements Serializable {

    private int IdTipoComercio = 0;
    private int IdComercio = 0;
    private String Nombre = "";
    private String Referencia = "";

    public AddFavoritesRequest() {
    }

    public AddFavoritesRequest(int mIdTipoComercio, int mIdComercio, String mNombre, String mReferencia) {
        this.IdTipoComercio = mIdTipoComercio;
        this.IdComercio = mIdComercio;
        this.Nombre = mNombre;
        this.Referencia = mReferencia;
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
}
