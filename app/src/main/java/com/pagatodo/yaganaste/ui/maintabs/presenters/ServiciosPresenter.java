package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.ui.maintabs.iteractors.ServiciosInteractor;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IServiciosInteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsFormPresenter;

/**
 * Created by Jordan on 19/04/2017.
 */

public class ServiciosPresenter implements IPaymentsFormPresenter, IServiciosInteractor.OnValidationFinishListener{

    IServiciosInteractor serviciosInteractor;
    PaymentsManager paymentsManager;

    public ServiciosPresenter(PaymentsManager paymentsManager){
        serviciosInteractor = new ServiciosInteractor();
        this.paymentsManager = paymentsManager;
    }

    @Override
    public void validateFields(String number, Double importe) {

    }

    @Override
    public void onReferenciaError() {

    }

    @Override
    public void onReferenciaEmpty() {

    }

    @Override
    public void onImporteError() {

    }

    @Override
    public void onImporteEmpty() {

    }

    @Override
    public void onConceptEmpty() {

    }

    @Override
    public void onSuccess() {

    }
}
