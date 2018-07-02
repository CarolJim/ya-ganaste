package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.WebService;

public interface IVentasDiarias <T> {

    void onSucces(WebService ws, T msgSuccess);

    void onError(WebService ws, T error);

    void obtenerSaldo();

    void obtenerResumendia(String fecha);


}
