package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.RegisterUserStarbucks;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

/**
 * Created by asandovals on 26/04/2018.
 */

public interface IregisterCompleteStarbuckss<T> {

    void onSucces(WebService ws, T msgSuccess);

    void onError(WebService ws, T error);

    void hideLoader();

    void registerStarbucks(RegisterUserStarbucks registerUserStarbucks);

    void getNeighborhoods(String zipCode);

    void datosregisterStarbucks();

    void login(String usuario, String password);


}
