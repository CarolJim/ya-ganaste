package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneTiposReembolsoResponse;

/**
 * Created by Omar on 22/02/2018.
 */

public interface ITimeRepaymentView {

    void onSuccessGetTypes(ObtieneTiposReembolsoResponse response);
    void onSuccessUpdateType();
    void onError(String error);
    void showLoader(String messaje);
    void hideLoader();
}
