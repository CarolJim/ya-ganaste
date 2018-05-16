package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.WebService;

/**
 * Created by asandovals on 19/04/2018.
 */

public interface IloginStarbucksss<T> {

    void onSucces(WebService ws, T msgSuccess);

    void onError(WebService ws, T error);

    void hideLoader();

    void login(String usuario, String password);

    void forgotpassword(String usuario);


}
