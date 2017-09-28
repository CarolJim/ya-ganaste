package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;

/**
 * Created by Jordan on 26/04/2017.
 */

public class Servicios extends Payments {

    public Servicios() {
        super();
    }

    public Servicios(String referencia, Double monto, String concepto, ComercioResponse comercio, boolean isFavorite) {
        this.referencia = referencia;
        this.monto = monto;
        this.concepto = concepto;
        this.comercio = comercio;
        this.isFavorite = isFavorite;
    }
}
