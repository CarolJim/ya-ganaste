package com.pagatodo.yaganaste.ui.adquirente;

import com.pagatodo.yaganaste.interfaces.IDocumentsPresenter;

/**
 * Created by flima on 22/03/2017.
 */

public abstract class DocumentsPresenter implements IDocumentsPresenter {
    private String TAG = DocumentsPresenter.class.getName();

    public DocumentsPresenter() {
    }

    @Override
    public abstract void uploadDocuments(Object documents);

    @Override
    public abstract void getStatusDocuments();
}
