package com.pagatodo.yaganaste.ui.adquirente.presenters;

import com.pagatodo.yaganaste.interfaces.IDocumentsPresenter;
import com.pagatodo.yaganaste.ui._manager.GenericPresenterMain;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;

/**
 * Created by flima on 22/03/2017.
 */

public abstract class DocumentsPresenter extends GenericPresenterMain<IPreferUserGeneric> implements IDocumentsPresenter {

    private String TAG = DocumentsPresenter.class.getName();

    public DocumentsPresenter() {
    }

    public abstract void showGaleryError();

    @Override
    public void setIView(IPreferUserGeneric iPreferUserGeneric) {
        super.setIView(iPreferUserGeneric);
    }

}
