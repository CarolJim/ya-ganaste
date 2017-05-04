package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerColoniasPorCPResponse extends GenericResponse {

    private List<ColoniasResponse> Data;

    public ObtenerColoniasPorCPResponse() {
        Data = new ArrayList<>();
    }

    public List<ColoniasResponse> getData() {
        return Data;
    }

    public void setData(List<ColoniasResponse> data) {
        Data = data;
    }
}
