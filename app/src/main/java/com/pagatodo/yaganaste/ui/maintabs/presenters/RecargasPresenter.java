package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.ui.maintabs.iteractors.RecargasInteractor;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IRecargasInteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsFormPresenter;

/**
 * Created by Jordan on 18/04/2017.
 */

public class RecargasPresenter implements IPaymentsFormPresenter, IRecargasInteractor.OnValidationFinishListener{

    IRecargasInteractor recargasInteractor;
    PaymentsManager paymentsManager;
    boolean isIAVE;

    public RecargasPresenter(PaymentsManager paymentsManager, boolean isIAVE){
        recargasInteractor = new RecargasInteractor();
        this.paymentsManager = paymentsManager;
        this.isIAVE = isIAVE;
    }

    @Override
    public void validateFields(String number, Double importe) {
        recargasInteractor.validateForms(number, importe, this);
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
        paymentsManager.onError(isIAVE ? "Numero de IAVE incorrecto" :  "Numero de Teléfono incorrecto");
    }

    @Override
    public void onSuccess() {
        paymentsManager.onSuccess();
    }
}
