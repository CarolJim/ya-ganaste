package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;

/**
 * Created by Jordan on 26/04/2017.
 */

public class Recarga extends Payments {

    public Recarga() {
        super();
    }

    public Recarga(String numero, Double importe, ComercioResponse comercio, boolean isFavorite) {
        this.referencia = numero;
        this.monto = importe;
        this.comercio = comercio;
        this.isFavorite = isFavorite;
    }

    @Override
    public String getConcepto() {
        return null;
    }

    @Override
    public void setConcepto(String concepto) {
    }
}
