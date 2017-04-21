package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.ui.maintabs.iteractors.ServiciosInteractor;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IServiciosInteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IServiciosPresenter;

/**
 * Created by Jordan on 19/04/2017.
 */

public class ServiciosPresenter implements IServiciosPresenter, IServiciosInteractor.OnValidationFinishListener {

    IServiciosInteractor serviciosInteractor;
    PaymentsManager paymentsManager;

    public ServiciosPresenter(PaymentsManager paymentsManager) {
        serviciosInteractor = new ServiciosInteractor();
        this.paymentsManager = paymentsManager;
    }

    @Override
    public void validateFields(String referencia, String importe, String concepto) {
        serviciosInteractor.validateForms(referencia, importe, concepto, this);
    }

    @Override
    public void onReferenciaError() {
        paymentsManager.onError("onReferenciaError");
    }

    @Override
    public void onReferenciaEmpty() {
        paymentsManager.onError("onReferenciaEmpty");
    }

    @Override
    public void onImporteError() {
        paymentsManager.onError("onImporteError");
    }

    @Override
    public void onImporteEmpty() {
        paymentsManager.onError("onImporteEmpty");
    }

    @Override
    public void onConceptEmpty() {
        paymentsManager.onError("onConceptEmpty");
    }

    @Override
    public void onSuccess() {
        paymentsManager.onSuccess();
    }
}
