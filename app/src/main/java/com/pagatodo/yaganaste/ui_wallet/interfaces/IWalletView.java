package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by icruz on 12/12/2017.
 */

public interface IWalletView extends IMainWalletView {


    void completed(boolean error);
    void getSaldo();
    void sendSuccessStatusAccount(EstatusCuentaResponse response);
}
