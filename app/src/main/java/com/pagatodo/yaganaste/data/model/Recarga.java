package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.data.room_db.entities.Comercio;

/**
 * Created by Jordan on 26/04/2017.
 */

public class Recarga extends Payments {

    public Recarga() {
        super();
    }

    public Recarga(String numero, Double importe, Comercio comercio, boolean isFavorite) {
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
