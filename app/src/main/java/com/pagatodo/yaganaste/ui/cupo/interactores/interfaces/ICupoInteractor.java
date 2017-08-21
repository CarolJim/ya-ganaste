package com.pagatodo.yaganaste.ui.cupo.interactores.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;

import java.util.ArrayList;

/**
 * Created by Tato on 03/08/17.
 */

public interface ICupoInteractor {

    void getNeighborhoodByZipCode(String zipCode);

    void getClientAddress();

    void getEstatusDocs();

    void sendDocuments(ArrayList<DataDocuments> string);

    void reenviaDocumentos(ArrayList<DataDocuments> string);

    void createSolicitudCupo();

    void getEstadoSolicitudCupo();




}
