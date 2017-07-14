package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;

import java.util.List;

/**
 * Created by flima on 22/03/2017.
 */

public interface IUploadDocumentsView extends INavigationView {
    void documentsUploaded(String message);

    void setDocumentosStatus(List<EstatusDocumentosResponse> data);

    void documentosActualizados(String s);
}
