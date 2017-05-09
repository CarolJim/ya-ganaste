package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.WebService;

/**
 * Created by flima on 20/02/2017.
 */

public interface IAccountManager<T> {

    public void goToNextStepAccount(String event, T data);
    public void onSucces(WebService ws,T msgSuccess);
    public void onError(WebService ws,T error);
    void hideLoader();

}
