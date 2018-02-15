package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WlletNotifaction;

/**
 * Created by icruz on 12/12/2017.
 */

public interface WalletInteractor extends IRequestResult<DataSourceResult> {

    void getWalletsCards(boolean error, WlletNotifaction listener);
    void getBalance();
    //void getMovementsAdq(ConsultarMovimientosRequest request, WlletNotifaction listener);
    void getMovements(ConsultarMovimientosRequest request);

}
