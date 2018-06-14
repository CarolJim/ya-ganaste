package com.pagatodo.yaganaste.ui_wallet.interfaces;

import android.support.v4.view.PagerAdapter;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;

import java.util.ArrayList;

/**
 * Created by icruz on 12/12/2017.
 */

public interface IWalletView extends IMainWalletView {
    void getPagerAdapter(ArrayList<ElementWallet> elementWallets);
    void getSaldo(String saldo);
    void sendCardReported();
    void sendSuccessStatusAccount(EstatusCuentaResponse response);
    //void sendSuccessInfoAgente();
    void setErrorSaldo(String saldoDefault);
    void sendError(int codeError);

    //void sendSuccess(GenericResponse response);
    //void sendSuccess();


}
