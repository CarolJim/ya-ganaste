package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.LoginStarbucksRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.RegisterStarbucksRequest;

/**
 * Created by asandovals on 25/04/2018.
 */

public interface IregisterIteractorStarbucks <T> {

    void register(RegisterStarbucksRequest request);
}
