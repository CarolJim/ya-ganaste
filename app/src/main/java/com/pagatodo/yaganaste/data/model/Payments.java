package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.data.room_db.entities.Comercio;

import java.io.Serializable;

/**
 * Created by Jordan on 26/04/2017.
 */

public class Payments implements Serializable {

    protected String referencia;
    protected Double monto;
    protected String concepto;
    protected Comercio comercio;
    protected boolean isFavorite;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }
}
