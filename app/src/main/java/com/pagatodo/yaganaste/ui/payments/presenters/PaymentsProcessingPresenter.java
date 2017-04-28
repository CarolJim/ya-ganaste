package com.pagatodo.yaganaste.ui.payments.presenters;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui.payments.interactors.PaymentsProcessingInteractor;
import com.pagatodo.yaganaste.ui.payments.interactors.interfaces.IPaymentsProcessingInteractor;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentsProcessingManager;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsProcessingPresenter;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB1;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB2;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsTab.TAB3;

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
        if(tab == TAB1){
            interactor.sendRecarga((Recarga)obj);
        }else if(tab == TAB2){
            interactor.sendPagoServicio((Servicios) obj);
        }else if (tab == TAB3){
            interactor.sendEnvio((Envios)obj);
        }
    }

    @Override
    public void onSuccessPayment(DataSourceResult result) {
        manager.hideLoader();
    }

    @Override
    public void onFailPayment(DataSourceResult error) {
        manager.hideLoader();
        manager.onFailPaimentResponse(error);
    }
}
