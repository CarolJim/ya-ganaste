package com.pagatodo.yaganaste.ui.payments.presenters.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.WebService;

/**
 * Created by Jordan on 14/08/2017.
 */

public interface IPaymentAuthorizePresenter {
    void validatePasswordFormat(String password);

    void onSuccess(WebService ws, Object success);

    void onError(WebService ws, Object error);
}
