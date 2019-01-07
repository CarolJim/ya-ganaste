package com.pagatodo.yaganaste.modules.wallet_emisor;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.BloquearCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.utils.Recursos;

public class WalletEmisorInteractor implements WalletEmisorContracts.Interactor {

    public static final int BLOQUEO = 1;
    public static final int DESBLOQUEO = 2;
    private WalletEmisorContracts.Listener listener;

    public WalletEmisorInteractor(WalletEmisorContracts.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void TemporaryBlock() {
        this.listener.showLoad();
        BloquearCuentaRequest bloquearCuentaRequest = new BloquearCuentaRequest("" + BLOQUEO);
        try {
            ApiTrans.bloquearCuenta(bloquearCuentaRequest, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            this.listener.onErrorTemporaryBlock();
        }
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        this.listener.hideLoad();
        if (result.getData() instanceof BloquearCuentaResponse) {
            BloquearCuentaResponse response = (BloquearCuentaResponse) result.getData();

            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                //Log.d("PreferUserIteractor", "BloquearCuentaResponse Sucess " + response.getMensaje());
                //preferUserPresenter.successGenericToPresenter(dataSourceResult);
                this.listener.onSuccessTemporaryBlock();
            } else {
                //Log.d("PreferUserIteractor", "BloquearCuentaResponse Sucess with Error " + response.getMensaje());
                ///preferUserPresenter.errorGenericToPresenter(dataSourceResult);
            }
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        this.listener.hideLoad();
    }
}
