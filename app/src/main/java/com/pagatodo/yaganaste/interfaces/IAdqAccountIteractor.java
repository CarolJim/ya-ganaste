package com.pagatodo.yaganaste.interfaces;

import android.view.View;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqAccountIteractor {
    void registerAdq();

    void getNeighborhoodByZipCode(String zipCode);

    void setListDocuments(View view, List<EstatusDocumentosResponse> mListaDocumentos);

    void getEstatusDocs();

    void getClientAddress();

    void sendDocuments(ArrayList<DataDocuments> string);

    void sendDocumentsPendientes(ArrayList<DataDocuments> string);

}
