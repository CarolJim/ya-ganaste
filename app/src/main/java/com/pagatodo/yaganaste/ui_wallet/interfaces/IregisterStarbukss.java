package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.WebService;

/**
 * Created by asandovals on 25/04/2018.
 */

public interface IregisterStarbukss<T> {

    void onSucces(WebService ws, T msgSuccess);

    void onError(WebService ws, T error);

    void hideLoader();

    void registerStarbucks(String tarjeta, String pin);


}
