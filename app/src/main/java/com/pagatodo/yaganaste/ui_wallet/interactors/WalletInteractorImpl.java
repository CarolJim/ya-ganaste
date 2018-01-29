package com.pagatodo.yaganaste.ui_wallet.interactors;


import android.os.Handler;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ConsultaSaldoCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneDatosCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ResumenMovimientosAdqResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WlletNotifaction;
import com.pagatodo.yaganaste.utils.Recursos;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO_ADQ;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTA_MOVIMIENTOS_MES_ADQ;

/**
 * Created by icruz on 12/12/2017.
 */

public class WalletInteractorImpl implements WalletInteractor {

    private WlletNotifaction listener;

    public WalletInteractorImpl(WlletNotifaction listener){
        this.listener = listener;
    }

    @Override
    public void getWalletsCards(final boolean error, final WlletNotifaction listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(error);
            }
        },1000);

    }

    @Override
    public void getMovementsAdq(ConsultarMovimientosRequest request, final WlletNotifaction listener ) {
        try {
            ApiAdtvo.consultarMovimientosMes(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            listener.onFailed(Recursos.CODE_OFFLINE, Recursos.NO_ACTION, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void getBalance() {
        try {
            ApiAdq.consultaSaldoCupo(this);
        } catch (OfflineException e) {
            //No-op
        }
    }

    //REQUEST
    @Override
    public void onSuccess(DataSourceResult result) {
        switch (result.getWebService()) {

            case CONSULTAR_SALDO_ADQ:
                //validateBalanceResponse((ConsultaSaldoCupoResponse) dataSourceResult.getData());
                this.listener.onSuccessADQ(((ConsultaSaldoCupoResponse) result.getData()).getSaldo());
                break;


        }
    }

    @Override
    public void onFailed(DataSourceResult error) {

    }
}
