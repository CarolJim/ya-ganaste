package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.ui.maintabs.iteractors.RecargasInteractor;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IRecargasInteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IRecargasPresenter;

/**
 * Created by Jordan on 18/04/2017.
 */

public class RecargasPresenter implements IRecargasPresenter, IRecargasInteractor.OnValidationFinishListener {

    IRecargasInteractor recargasInteractor;
    PaymentsManager paymentsManager;
    boolean isIAVE;

    public RecargasPresenter(PaymentsManager paymentsManager, boolean isIAVE) {
        recargasInteractor = new RecargasInteractor();
        this.paymentsManager = paymentsManager;
        this.isIAVE = isIAVE;
    }

    @Override
    public void validateFields(String referencia, Double importe, int longitudReferencia, boolean isIAVE) {
        recargasInteractor.validateForms(referencia, importe, longitudReferencia, isIAVE, this);
    }

    @Override
    public void onMontoError() {
        paymentsManager.onError("Por favor selecióna un Importe");
    }

    @Override
    public void onNumberEmpty() {
        paymentsManager.onError(isIAVE ? "Numero de IAVE vacío" : "Numero de Teléfono vacío");
    }

    @Override
    public void onNumberError() {
        paymentsManager.onError(isIAVE ? "Numero de IAVE incorrecto" : "Numero de Teléfono incorrecto");
    }

    @Override
    public void onSuccess(Double monto) {
        paymentsManager.onSuccess(monto);
    }
}
