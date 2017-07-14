package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
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

    public EnviosPresenter(PaymentsManager paymentsManager) {
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
        paymentsManager.onError(App.getContext().getString(R.string.txt_tipo_envio_error));
    }

    @Override
    public void onNumberEmpty() {
        paymentsManager.onError(App.getContext().getString(R.string.txt_referencia_envio_empty));
    }

    @Override
    public void onNumberError() {
        paymentsManager.onError(App.getContext().getString(R.string.txt_referencia_envio_error));
    }

    @Override
    public void onImporteEmpty() {
        paymentsManager.onError(App.getContext().getString(R.string.txt_importe_empty));
    }

    @Override
    public void onImporteError() {
        paymentsManager.onError(App.getContext().getString(R.string.txt_importe_error));
    }

    @Override
    public void onNameEmpty() {
        paymentsManager.onError(App.getContext().getString(R.string.txt_name_empty));
    }

    @Override
    public void onNameError() {
        paymentsManager.onError(App.getContext().getString(R.string.txt_name_error));
    }

    @Override
    public void onConceptEmpty() {
        paymentsManager.onError(App.getContext().getString(R.string.txt_concept_empty));
    }

    @Override
    public void onConceptError() {
        paymentsManager.onError(App.getContext().getString(R.string.txt_concept_error));
    }

    @Override
    public void onReferenceEmpty() {
        paymentsManager.onError(App.getContext().getString(R.string.txt_referencia_number_empty));
    }

    @Override
    public void onReferenceError() {
        paymentsManager.onError(App.getContext().getString(R.string.txt_referencia_number_error));
    }

    @Override
    public void onSuccess(Double monto) {
        paymentsManager.onSuccess(monto);
    }
}
