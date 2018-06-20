package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.WebService;

/**
 * Created by asandovals on 20/06/2018.
 */

public interface IChangeStatusOperador<T> {

    void onSucces(WebService ws, T msgSuccess);

    void onError(WebService ws, T error);

    void change(String usuario, int status);
}
