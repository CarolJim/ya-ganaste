package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DocumentosFragment;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_RECHAZADO;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerDocumentosResponse extends GenericResponse {

    private ResponseDocumentos Data;

    public ObtenerDocumentosResponse() {
        Data = new ResponseDocumentos();
    }

    public ResponseDocumentos getData() {
        return Data;
    }

    public List<EstatusDocumentosResponse> getDocumentos() {
        return Data.Documentos;
    }

    public String getEstatus() {
        return Data.Estatus;
    }

    public String getFolio() {
        return Data.Folio;
    }

    public String getMotivo() {
        return Data.Motivo;
    }

    public int getIdEstatus() {
        return Data.IdEstatus;
    }

    public boolean hasErrorInDocs(){
        return Data.docsWithError();
    }

    public void setData(ResponseDocumentos data) {
        Data = data;
    }

    class ResponseDocumentos {
        List<EstatusDocumentosResponse> Documentos;
        String Estatus, Folio, Motivo;
        int IdEstatus;

        public boolean docsWithError(){
            for (EstatusDocumentosResponse docs: Documentos){
                if(docs.getIdEstatus()==STATUS_DOCTO_RECHAZADO){
                    return true;
                }
            }
            return false;
        }
    }
}
