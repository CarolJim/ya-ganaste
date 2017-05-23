package com.pagatodo.yaganaste.ui.payments.managers;

import com.pagatodo.yaganaste.interfaces.enums.WebService;

/**
 * Created by jmhernandez on 22/05/2017.
 */

public interface PaymentsSuccessManaget<T> {

    public void goToNextStepAccount(String event, T data);
    public void onSucces(WebService ws, T msgSuccess);
    public void onError(WebService ws,T error);
    public void hideLoader();

}
