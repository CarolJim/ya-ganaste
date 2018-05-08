package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.interfaces.IRequestResult;

/**
 * Created by icruz on 12/12/2017.
 */

public interface WalletInteractor extends IRequestResult<DataSourceResult> {

    void getWalletsCards(boolean error, WalletNotification listener);
    void getBalance(int typeWallet);
    void getStatusAccount(EstatusCuentaRequest request);
    void getInfoAgente();
}
