package com.pagatodo.yaganaste.ui.payments.presenters;

import android.os.Handler;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.DataTransaccion;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui.payments.interactors.PaymentsProcessingInteractor;
import com.pagatodo.yaganaste.ui.payments.interactors.interfaces.IPaymentsProcessingInteractor;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentsProcessingManager;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsProcessingPresenter;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB1;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB2;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB3;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by Jordan on 27/04/2017.
 */

public class PaymentsProcessingPresenter implements IPaymentsProcessingPresenter{
    IPaymentsProcessingInteractor interactor;
    PaymentsProcessingManager manager;

    public PaymentsProcessingPresenter(PaymentsProcessingManager manager){
        this.manager = manager;
        interactor = new PaymentsProcessingInteractor(this);
    }

    @Override
    public void sendPayment(MovementsTab tab, Object obj) throws OfflineException {
        /*if(tab == TAB1){
            interactor.sendRecarga((Recarga)obj);
        }else if(tab == TAB2){
            interactor.sendPagoServicio((Servicios) obj);
        }else if (tab == TAB3){
            interactor.sendEnvio((Envios)obj);
        }*/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                EjecutarTransaccionResponse data = new EjecutarTransaccionResponse();
                data.setCodigoRespuesta(CODE_OK);
                DataSourceResult sourceResult = new DataSourceResult(null, null, data);
                onSuccessPayment(sourceResult);
            }
        }, 2300);
    }

    @Override
    public void onSuccessPayment(DataSourceResult result) {
        EjecutarTransaccionResponse data = (EjecutarTransaccionResponse)result.getData();
        if(data.getCodigoRespuesta() == CODE_OK){
            manager.onSuccessPaymentRespone(result);
        }else{
            manager.hideLoader();
            manager.onFailPaimentResponse(result);
        }

    }

    @Override
    public void onFailPayment(DataSourceResult error) {
        manager.hideLoader();
        manager.onFailPaimentResponse(error);
    }
}