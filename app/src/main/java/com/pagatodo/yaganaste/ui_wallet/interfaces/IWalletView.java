package com.pagatodo.yaganaste.ui_wallet.interfaces;

import android.support.v4.view.PagerAdapter;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by icruz on 12/12/2017.
 */

public interface IWalletView extends IMainWalletView {
    void getPagerAdapter(PagerAdapter pagerAdapter);
    void getSaldo(String saldo);
    void sendCardReported();
    void sendSuccessStatusAccount(EstatusCuentaResponse response);
    void sendSuccessInfoAgente();
    void setErrorSaldo(String saldoDefault);
    void sendError(int codeError);

    //void sendSuccess(GenericResponse response);
    //void sendSuccess();


}
