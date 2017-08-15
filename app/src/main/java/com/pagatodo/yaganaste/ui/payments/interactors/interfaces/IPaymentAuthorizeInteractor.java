package com.pagatodo.yaganaste.ui.payments.interactors.interfaces;

import com.pagatodo.yaganaste.net.IRequestResult;

/**
 * Created by Jordan on 14/08/2017.
 */

public interface IPaymentAuthorizeInteractor extends IRequestResult {
    void validatePasswordFormat(String password);
}
