package com.pagatodo.yaganaste.ui.payments.managers;

import com.pagatodo.yaganaste.interfaces.enums.WebService;

/**
 * Created by jmhernandez on 22/05/2017.
 */

public interface PaymentsSuccessManaget<T> {

    void goToNextStepAccount(String event, T data);

    void onSucces(WebService ws, T msgSuccess);

    void onError(WebService ws, T error);

    void hideLoader();

}
