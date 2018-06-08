package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class ResumenMovimientosMesRequest extends AdqRequest implements Serializable {

    private String FechaInicial, FechaFinal;

    public ResumenMovimientosMesRequest() {
    }

    public String getFechaInicial() {
        return FechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        FechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return FechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        FechaFinal = fechaFinal;
    }
}
