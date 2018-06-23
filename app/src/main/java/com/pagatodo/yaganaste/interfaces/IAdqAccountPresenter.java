package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;

import java.util.ArrayList;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqAccountPresenter {

    void updateAdq(String folio);

    void getNeighborhoods(String zipCode);

    void getEstatusDocs();

    void getClientAddress();

    void sendDocumentos(ArrayList<DataDocuments> data);

    void sendDocumentosPendientes(ArrayList<DataDocuments> data);
}
