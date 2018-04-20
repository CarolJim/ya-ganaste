package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.LoginStarbucksRequest;

/**
 * Created by asandovals on 19/04/2018.
 */

public interface IloginIteractorStarbucks<T>{

    void login(LoginStarbucksRequest request);
}
