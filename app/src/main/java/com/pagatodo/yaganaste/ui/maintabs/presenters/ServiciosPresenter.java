package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
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
    public void validateFields(String referencia, String importe, String concepto, int longitudReferencia) {
        serviciosInteractor.validateForms(referencia, importe, concepto, longitudReferencia, this);
    }

    @Override
    public void onReferenciaError() {
        paymentsManager.onError(App.getContext().getResources().getString(R.string.txt_referencia_error));
    }

    @Override
    public void onReferenciaEmpty() {
        paymentsManager.onError(App.getContext().getResources().getString(R.string.txt_referencia_empty));
    }

    @Override
    public void onImporteError() {
        paymentsManager.onError(App.getContext().getResources().getString(R.string.txt_importe_error));
    }

    @Override
    public void onImporteEmpty() {
        paymentsManager.onError(App.getContext().getResources().getString(R.string.txt_importe_empty));
    }

    @Override
    public void onConceptEmpty() {
        paymentsManager.onError(App.getContext().getResources().getString(R.string.txt_concept_empty));
    }

    @Override
    public void onSuccess(Double i) {
        paymentsManager.onSuccess(i);
    }
}
