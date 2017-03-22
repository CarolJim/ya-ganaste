package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerDocumentosResponse extends GenericResponse {

    private DataEstatusDocumentos Data;

    public ObtenerDocumentosResponse() {
        Data = new DataEstatusDocumentos();
    }

    public DataEstatusDocumentos getData() {
        return Data;
    }

    public void setData(DataEstatusDocumentos data) {
        Data = data;
    }
}
