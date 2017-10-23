package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
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
        paymentsManager.onError(App.getContext().getString(R.string.favor_selecciona_importe));
    }

    @Override
    public void onNumberEmpty() {
        paymentsManager.onError(isIAVE ? App.getContext().getString(R.string.numero_iave_vacio)  :
        App.getContext().getString(R.string.numero_telefono_vacio) );
    }

    @Override
    public void onNumberError() {
        paymentsManager.onError(isIAVE ? App.getContext().getString(R.string.new_body_IAVE_error) :
        App.getContext().getString(R.string.new_body_phone_error) );
    }

    @Override
    public void onSuccess(Double monto) {
        paymentsManager.onSuccess(monto);
    }
}
