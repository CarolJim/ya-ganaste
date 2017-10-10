package com.pagatodo.yaganaste.ui.preferuser.interfases;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;

/**
 * Created by Armando Sandoval on 10/10/2017.
 */

public interface IMyCardViewHome extends IPreferUserGeneric {

    void showLoader(String s);

    void hideLoader();

    void sendSuccessBloquearCuentaToView(BloquearCuentaResponse response);
    void sendSuccessEstatusCuentaToView(EstatusCuentaResponse response);

    void sendErrorBloquearCuentaToView(String mensaje);
}
