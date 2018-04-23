package com.pagatodo.yaganaste.data.model.webservice.response.manager;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerCatalogos;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.RespuestaStarbucks;

import java.io.Serializable;

/**
 * Created by asandovals on 20/04/2018.
 */

public class GenericResponseStarbucks implements Serializable {

    private RespuestaStarbucks respuestaStarbucks;
    public RespuestaStarbucks getData() {
        return respuestaStarbucks;
    }
    public void setData(RespuestaStarbucks data) {
        respuestaStarbucks = data;
    }
}
