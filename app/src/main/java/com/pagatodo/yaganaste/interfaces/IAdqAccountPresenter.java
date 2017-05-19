package com.pagatodo.yaganaste.interfaces;

import android.view.View;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;

import java.util.ArrayList;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqAccountPresenter {

    public void createAdq();
    public void getNeighborhoods(String zipCode);
    public void getEstatusDocs();
    public void getClientAddress();
    public void sendDocumentos(ArrayList<DataDocuments> data);
    public void sendDocumentosPendientes(ArrayList<DataDocuments> data);
}
