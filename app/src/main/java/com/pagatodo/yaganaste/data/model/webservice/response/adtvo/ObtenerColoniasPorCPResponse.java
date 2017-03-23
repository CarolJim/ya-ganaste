package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerColoniasPorCPResponse extends GenericResponse {

    private DataObtenerColoniasPorCP Data;

    public ObtenerColoniasPorCPResponse() {
        Data = new DataObtenerColoniasPorCP();
    }

    public DataObtenerColoniasPorCP getData() {
        return Data;
    }

    public void setData(DataObtenerColoniasPorCP data) {
        Data = data;
    }
}
