package com.pagatodo.yaganaste.ui.cupo.presenters.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;

import java.util.ArrayList;

/**
 * Created by Tato on 02/08/17.
 */

public interface IViewDomicilioPersonalPresenter {

        void getNeighborhoods(String zipCode);

        void getClientAddress();

        public abstract void showGaleryError();

        void getEstatusDocs();

        void sendDocumentos(ArrayList<DataDocuments> data);

        void reenviaDocumentos(ArrayList<DataDocuments> data);

        void createCupoSolicitud();

        void getEstadoSolicitudCupo();

}
