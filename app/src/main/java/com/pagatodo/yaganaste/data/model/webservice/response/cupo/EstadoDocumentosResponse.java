package com.pagatodo.yaganaste.data.model.webservice.response.cupo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Horacio on 21/08/17.
 */

public class EstadoDocumentosResponse extends GenericResponse {

    private List<DataEstadoDocumentos> Data;

    private EstadoDocumentosResponse() {
        Data = new ArrayList<DataEstadoDocumentos>();
    }

    public List<DataEstadoDocumentos> getData() {
        return Data;
    }

    public void setData(List<DataEstadoDocumentos> data) {
        Data = data;
    }

}
