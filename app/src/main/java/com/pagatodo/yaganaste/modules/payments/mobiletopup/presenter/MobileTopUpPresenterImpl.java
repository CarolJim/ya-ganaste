package com.pagatodo.yaganaste.modules.payments.mobiletopup.presenter;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.payments.mobiletopup.interactor.MobileTopUpInteractorImpl;
import com.pagatodo.yaganaste.modules.payments.mobiletopup.interactor.MobileTopUpIntercator;
import com.pagatodo.yaganaste.modules.payments.mobiletopup.view.MobileTopUpView;

public class MobileTopUpPresenterImpl implements MobileTopUpPresenter {

    MobileTopUpIntercator mobileTopUpIntercator;
    MobileTopUpView mobileTopUpView;
    boolean isIAVE;

    public MobileTopUpPresenterImpl(MobileTopUpView paymentsManager, boolean isIAVE) {
        mobileTopUpIntercator = new MobileTopUpInteractorImpl(this);
        this.mobileTopUpView = paymentsManager;
        this.isIAVE = isIAVE;
    }

    @Override
    public void validateFields(String referencia, Double importe, int longitudReferencia, boolean isIAVE) {
        mobileTopUpIntercator.validateFields(referencia, importe, longitudReferencia, isIAVE);
    }

    @Override
    public void onMontoError() {
        mobileTopUpView.onError(App.getContext().getString(R.string.favor_selecciona_importe));
    }

    @Override
    public void onNumberEmpty() {
        mobileTopUpView.onError(isIAVE ? App.getContext().getString(R.string.numero_iave_vacio)  :
                App.getContext().getString(R.string.numero_telefono_vacio) );
    }

    @Override
    public void onNumberError() {
        mobileTopUpView.onError(isIAVE ? App.getContext().getString(R.string.new_body_IAVE_error) :
                App.getContext().getString(R.string.new_body_phone_error));
    }

    @Override
    public void onSuccess(Double monto) {
        mobileTopUpView.onSuccess(monto);
    }

}
