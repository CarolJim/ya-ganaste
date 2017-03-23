package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class ResumenMovimientosMesRequest  extends AdqRequest implements Serializable{

    private String fecha = "";

    public ResumenMovimientosMesRequest() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
