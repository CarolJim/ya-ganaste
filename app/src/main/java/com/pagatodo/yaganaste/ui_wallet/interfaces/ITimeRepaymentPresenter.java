package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneTiposReembolsoResponse;

/**
 * Created by Omar on 22/02/2018.
 */

public interface ITimeRepaymentPresenter {
    void getTypePayments();

    void updateTypeRepayment(int idTypeRepayment);

    void onSuccessGetTypesRepayment(ObtieneTiposReembolsoResponse response);

    void onSuccessUpdateTypeRepayment();

    void onFailedGetTypesRepayment(String error);

    void onFailedUpdateTypeRepayment(String error);

    void toPresenterErrorServer(String message);
}
