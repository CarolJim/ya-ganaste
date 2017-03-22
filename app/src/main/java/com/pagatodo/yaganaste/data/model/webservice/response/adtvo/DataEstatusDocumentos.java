package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class DataEstatusDocumentos implements Serializable {

    private List<EstatusDocumentosResponse> ListaDocumentos;

    public DataEstatusDocumentos() {
        ListaDocumentos = new ArrayList<EstatusDocumentosResponse>();
    }

    public List<EstatusDocumentosResponse> getListaDocumentos() {
        return ListaDocumentos;
    }

    public void setListaDocumentos(List<EstatusDocumentosResponse> listaDocumentos) {
        ListaDocumentos = listaDocumentos;
    }
}
