package com.pagatodo.yaganaste.ui.maintabs.managers;

import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataTitular;
import com.pagatodo.yaganaste.interfaces.ITextChangeListener;

/**
 * Created by Jordan on 17/08/2017.
 */

public interface EnviosManager extends PaymentsManager, ITextChangeListener {
    void showError(String text);

    void setTitularName(DataTitular dataTitular);

    void onFailGetTitulaName();

    //void clearTitularName();

    void showLoader(String text);

    void hideLoader();
}
