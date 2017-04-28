package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.EnviosInteractor;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IEnviosInteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IEnviosPresenter;

/**
 * Created by Jordan on 20/04/2017.
 */

public class EnviosPresenter implements IEnviosPresenter, IEnviosInteractor.OnValidationFinishListener {

    IEnviosInteractor enviosInteractor;
    PaymentsManager paymentsManager;

    public EnviosPresenter(PaymentsManager paymentsManager){
        this.paymentsManager = paymentsManager;
        enviosInteractor = new EnviosInteractor();
    }


    @Override
    public void validateForms(TransferType type, String number, String importe, String name,
                              String concept, String reference) {
        enviosInteractor.validateForms(type, number, importe, name, concept, reference, this);
    }

    @Override
    public void onTypeError() {
        paymentsManager.onError("onTypeError");
    }

    @Override
    public void onNumberEmpty() {
        paymentsManager.onError("onNumberEmpty");
    }

    @Override
    public void onNumberError() {
        paymentsManager.onError("onNumberError");
    }

    @Override
    public void onImporteEmpty() {
        paymentsManager.onError("onImporteEmpty");
    }

    @Override
    public void onImporteError() {
        paymentsManager.onError("onImporteError");
    }

    @Override
    public void onNameEmpty() {
        paymentsManager.onError("onNameEmpty");
    }

    @Override
    public void onNameError() {
        paymentsManager.onError("onNameError");
    }

    @Override
    public void onConceptEmpty() {
        paymentsManager.onError("onConceptEmpty");
    }

    @Override
    public void onConceptError() {
        paymentsManager.onError("onConceptError");
    }

    @Override
    public void onReferenceEmpty() {
        paymentsManager.onError("onReferenceEmpty");
    }

    @Override
    public void onReferenceError() {
        paymentsManager.onError("onReferenceError");
    }

    @Override
    public void onSuccess(Double monto) {
        paymentsManager.onSuccess(monto);
    }
}
