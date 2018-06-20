package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.interfaces.IRequestResult;

/**
 * Created by icruz on 12/12/2017.
 */

public interface WalletInteractor extends IRequestResult<DataSourceResult> {

    void getWalletsCards(boolean error, WalletNotification listener);
    void getBalance(int typeWallet, Agentes agentes);
    void getStatusAccount(EstatusCuentaRequest request);
    //void getInfoAgente();
}
