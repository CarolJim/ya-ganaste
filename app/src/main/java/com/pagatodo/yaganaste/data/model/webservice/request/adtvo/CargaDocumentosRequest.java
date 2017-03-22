package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class CargaDocumentosRequest implements Serializable{

    private List<DataDocuments> Documentos;

    public CargaDocumentosRequest() {

        Documentos = new ArrayList<DataDocuments>();
    }

    public List<DataDocuments> getDocumentos() {
        return Documentos;
    }

    public void setDocumentos(List<DataDocuments> documentos) {
        Documentos = documentos;
    }
}
