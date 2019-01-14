package com.pagatodo.yaganaste.modules.emisor;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.BloquearCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultaAsignacionTarjetaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarAsignacionTarjetaResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.utils.Recursos;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_ASIGNACION_TARJETA;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

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
            //this.listener.onErrorTemporaryBlock();
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

            } else {
                //Log.d("PreferUserIteractor", "BloquearCuentaResponse Sucess with Error " + response.getMensaje());
                ///preferUserPresenter.errorGenericToPresenter(dataSourceResult);
            }
        } else if (result.getData() instanceof ConsultarAsignacionTarjetaResponse){
            if (((ConsultarAsignacionTarjetaResponse) result.getData()).getCodigoRespuesta() == CODE_OK){
                this.listener.onSouccesValidateCard();
            } else {
                this.listener.onErrorRequest(((ConsultarAsignacionTarjetaResponse) result.getData()).getMensaje());
            }
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        this.listener.hideLoad();
        if (error != null && error.getWebService() == CONSULTAR_ASIGNACION_TARJETA) {
          this.listener.onErrorRequest(error.getData().toString());
        }
    }

    @Override
    public void validateCard(String cardNumber) {
        this.listener.showLoad();
        ConsultaAsignacionTarjetaRequest request = new ConsultaAsignacionTarjetaRequest(cardNumber);
        try {
            ApiTrans.consultaAsignacionTarjeta(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            this.listener.onErrorRequest(App.getContext().getString(R.string.no_internet_access));
            //accountManager.onError(CONSULTAR_ASIGNACION_TARJETA, App.getContext().getString(R.string.no_internet_access);
        }
    }
}
