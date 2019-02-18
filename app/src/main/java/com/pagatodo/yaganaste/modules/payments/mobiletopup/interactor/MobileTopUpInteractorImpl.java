package com.pagatodo.yaganaste.modules.payments.mobiletopup.interactor;

import com.pagatodo.yaganaste.modules.payments.mobiletopup.presenter.MobileTopUpPresenter;
import com.pagatodo.yaganaste.utils.ValidateForm;

public class MobileTopUpInteractorImpl implements MobileTopUpIntercator {

    MobileTopUpPresenter presenter;

    public MobileTopUpInteractorImpl(MobileTopUpPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void validateFields(String phone, Double monto, int longitudReferencia, boolean isIAVE) {
        if (phone == null || phone.isEmpty()) {
            presenter.onNumberEmpty();
            return;
        }


        if (isIAVE && longitudReferencia != 0 && phone.length() < longitudReferencia) {
            presenter.onNumberError();
            return;
        }

        if (!isIAVE && !ValidateForm.isValidCellPhone(phone)) {
            presenter.onNumberError();
            return;
        }


        if (monto == null || monto <= 0.0) {
            presenter.onMontoError();
            return;
        }

        presenter.onSuccess(monto);
    }
}
