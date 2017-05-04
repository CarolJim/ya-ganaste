package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDocumentosResponse;

import java.util.List;

/**
 * Created by flima on 22/03/2017.
 */

public interface IDocumentsPresenter <T> {

    public void uploadDocuments(T documents);
}
