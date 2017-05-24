package com.pagatodo.yaganaste.ui.adquirente;

import android.view.View;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.interfaces.IDocumentsPresenter;

import java.util.List;

/**
 * Created by flima on 22/03/2017.
 */

public abstract class DocumentsPresenter implements IDocumentsPresenter {

    private String TAG = DocumentsPresenter.class.getName();

    public DocumentsPresenter() {
    }

    public abstract void showGaleryError();



}
