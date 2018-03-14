package com.pagatodo.yaganaste.ui_wallet.interactors;


import android.os.Handler;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ConsultaSaldoCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarMovimientosMesResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarSaldoResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletNotification;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringConstants;

import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE;

/**
 * Created by icruz on 12/12/2017.
 */

public class WalletInteractorImpl implements WalletInteractor {

    private WalletNotification listener;

    public WalletInteractorImpl(WalletNotification listener) {
        this.listener = listener;
    }

    @Override
    public void getWalletsCards(final boolean error, final WalletNotification listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(error);
            }
        }, 1000);

    }

    /*
        @Override
        public void getMovementsAdq(ConsultarMovimientosRequest request, final WalletNotification listener ) {
            try {
                ApiAdtvo.consultarMovimientosMes(request, this);
            } catch (OfflineException e) {
                e.printStackTrace();
                listener.onFailed(Recursos.CODE_OFFLINE, Recursos.NO_ACTION, App.getInstance().getString(R.string.no_internet_access));
            }
        }
    */
    @Override
    public void getBalance() {
        try {
            ApiTrans.consultarSaldo(this);
            ApiAdq.consultaSaldoCupo(this);
        } catch (OfflineException e) {
            //No-op
        }
    }

    @Override
    public void getMovements(ConsultarMovimientosRequest request) {
        try {
            ApiAdtvo.consultarMovimientosMes(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            this.listener.onFailed(Recursos.CODE_OFFLINE, Recursos.NO_ACTION, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    //REQUEST
    @Override
    public void onSuccess(DataSourceResult result) {
        switch (result.getWebService()) {
            case CONSULTAR_MOVIMIENTOS_MES:
                validateResponse((ConsultarMovimientosMesResponse) result.getData());

                break;
            case CONSULTAR_SALDO:
                this.listener.onSuccessEmisor(((ConsultarSaldoResponse) result.getData()).getData().getSaldo());
                break;
            case CONSULTAR_SALDO_ADQ:
                //validateBalanceResponse((ConsultaSaldoCupoResponse) dataSourceResult.getData());
                this.listener.onSuccessADQ(((ConsultaSaldoCupoResponse) result.getData()).getSaldo());
                break;


        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        if (error.getWebService() == WebService.CONSULTAR_MOVIMIENTOS_MES) {
            this.listener.onFailed(0, Recursos.NO_ACTION, error.getData().toString());
        }
    }

    private void validateResponse(ConsultarMovimientosMesResponse response) {
        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            listener.onSuccesMovements(response);
            //onSuccesResponse(response);
        } else {
            listener.onFailed(response.getCodigoRespuesta(), response.getAccion(), response.getMensaje());
        }
    }
}
