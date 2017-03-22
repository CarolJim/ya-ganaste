package com.pagatodo.yaganaste.data.model.webservice.request.trans;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class BloquearTemporalmenteTarjetaRequest implements Serializable{
    private int TipoOperacion;

    public BloquearTemporalmenteTarjetaRequest() {
    }

    public int getTipoOperacion() {
        return TipoOperacion;
    }

    public void setTipoOperacion(int tipoOperacion) {
        TipoOperacion = tipoOperacion;
    }
}
