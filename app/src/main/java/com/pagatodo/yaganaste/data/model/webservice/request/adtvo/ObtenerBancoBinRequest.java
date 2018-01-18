package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by Armando Sandoval on 17/01/2018.
 */

public class ObtenerBancoBinRequest implements Serializable {

    private String pbusqueda;

    private String tipoConsulta;


    public String getPbusqueda() {
        return pbusqueda;
    }

    public void setPbusqueda(String pbusqueda) {
        this.pbusqueda = pbusqueda;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }
}
