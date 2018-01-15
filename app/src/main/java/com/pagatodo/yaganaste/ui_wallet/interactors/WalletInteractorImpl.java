package com.pagatodo.yaganaste.ui_wallet.interactors;


import android.os.Handler;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WlletNotifaction;
import com.pagatodo.yaganaste.utils.Recursos;

/**
 * Created by icruz on 12/12/2017.
 */

public class WalletInteractorImpl implements WalletInteractor {


    public WalletInteractorImpl(){
    }

    @Override
    public void getWalletsCards(final WlletNotifaction listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess();
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

    //REQUEST
    @Override
    public void onSuccess(DataSourceResult result) {

    }

    @Override
    public void onFailed(DataSourceResult error) {

    }
}
