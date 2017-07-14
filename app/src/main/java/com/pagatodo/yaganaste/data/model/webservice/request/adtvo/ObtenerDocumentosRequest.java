package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerDocumentosRequest extends GenericResponse {

    private List<EstatusDocumentosResponse> Data;

    public ObtenerDocumentosRequest() {
        Data = new ArrayList<>();
    }

    public List<EstatusDocumentosResponse> getData() {
        return Data;
    }

    public void setData(List<EstatusDocumentosResponse> data) {
        Data = data;
    }
}
