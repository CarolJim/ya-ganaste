package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class ConsultarMovimientosMesResponse extends GenericResponse {

    private List<MovimientosResponse> Data;

    public ConsultarMovimientosMesResponse() {
        Data = new ArrayList<MovimientosResponse>();
    }

    public List<MovimientosResponse> getData() {
        return Data;
    }

    public void setData(List<MovimientosResponse> data) {
        Data = data;
    }
}
