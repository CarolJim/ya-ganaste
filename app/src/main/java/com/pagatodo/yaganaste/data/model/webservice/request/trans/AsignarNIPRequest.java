package com.pagatodo.yaganaste.data.model.webservice.request.trans;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class AsignarNIPRequest implements Serializable {

    private String NIPActual = "";
    private String NIPNuevo = "";

    public AsignarNIPRequest() {
    }

    public AsignarNIPRequest(String NIPNuevo) {
        this.NIPNuevo = NIPNuevo;
    }

    public String getNIPActual() {
        return NIPActual;
    }

    public void setNIPActual(String NIPActual) {
        this.NIPActual = NIPActual;
    }

    public String getNIPNuevo() {
        return NIPNuevo;
    }

    public void setNIPNuevo(String NIPNuevo) {
        this.NIPNuevo = NIPNuevo;
    }
}
