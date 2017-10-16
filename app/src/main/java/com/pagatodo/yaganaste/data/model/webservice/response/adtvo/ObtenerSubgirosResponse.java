package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerSubgirosResponse extends GenericResponse {

    private List<Giros> Data;

    public ObtenerSubgirosResponse() {
        Data = new ArrayList<Giros>();
    }

    public List<Giros> getData() {
        return Data;
    }

    public void setData(List<Giros> data) {
        Data = data;
    }
}
