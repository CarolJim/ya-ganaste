package com.pagatodo.yaganaste.ui.payments.managers;

import com.pagatodo.yaganaste.interfaces.IProgressView;

/**
 * Created by jmhernandez on 22/05/2017.
 */

public interface PaymentSuccessManager extends IProgressView {
    void validateMail();

    void onSuccessSendMail(String successMessage);

    void onErrorSendMail(String errorMessage);

    void finalizePayment();
}
