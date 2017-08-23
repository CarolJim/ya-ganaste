package com.pagatodo.yaganaste.ui.payments.managers;

import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui.otp.controllers.OtpView;

/**
 * Created by Jordan on 14/08/2017.
 */

public interface PaymentAuthorizeManager extends ValidationForms, OtpView{
    void showError(Object error);

    void validationPasswordSucces();

    void validationPasswordFailed(String error);

    void showLoader(String title);

    void hideLoader();
}
