package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerSubgirosResponse extends GenericResponse {

    private List<SubGiro> Data;

    public ObtenerSubgirosResponse() {
        Data = new ArrayList<SubGiro>();
    }

    public List<SubGiro> getData() {
        return Data;
    }

    public void setData(List<SubGiro> data) {
        Data = data;
    }
}
